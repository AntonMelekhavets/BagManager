package View.Controller;

import Model.DirectoryMemento;
import Model.FileInformation;
import OperationExecutors.OperationController;
import View.GUICreator;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainWindowController {
    private static GUICreator guiCreator;
    @FXML
    private TableView<FileInformation> fileTableOne;
    @FXML
    private TableColumn<FileInformation, String> fileNameColumnOne;
    @FXML
    private TableColumn<FileInformation, String> fileSizeColumnOne;
    @FXML
    private TableColumn<FileInformation, ImageView> fileTypeOne;
    private DirectoryMemento directoryMementoFirst;
    @FXML
    private TableView<FileInformation> fileTableTwo;
    @FXML
    private TableColumn<FileInformation, String> fileNameColumnTwo;
    @FXML
    private TableColumn<FileInformation, String> fileSizeColumnTwo;
    @FXML
    private TableColumn<FileInformation, ImageView> fileTypeTwo;
    private DirectoryMemento directoryMementoSecond;
    @FXML
    private ComboBox<String> listOfRootsForTableOne;
    @FXML
    private ComboBox<String> listOfRootsForTableTwo;

    public static GUICreator getMainApp() {
        return guiCreator;
    }

    public void setMainApp(GUICreator mainApp) {
        MainWindowController.guiCreator = mainApp;
        fileTableOne.setItems(mainApp.getFileDataFirst());
        fileTableTwo.setItems(mainApp.getFileDataSecond());
        listOfRootsForTableOne.setItems(mainApp.getListOfRootsForTableOne());
        listOfRootsForTableTwo.setItems(mainApp.getListOfRootsForTableTwo());
    }

    public static GUICreator getGuiCreator() {
        return guiCreator;
    }

    public void savePrevDirectory(String currentDirectory, int number) {
        switch (number) {
            case 1:
                directoryMementoFirst = new DirectoryMemento(currentDirectory);
                break;
            case 2:
                directoryMementoSecond = new DirectoryMemento(currentDirectory);
                break;
        }
    }

    public String getPrevDirectory(int number) {
        switch (number) {
            case 1:
                return directoryMementoFirst.getLastDirectory();
            case 2:
                return directoryMementoSecond.getLastDirectory();
            default:
                return null;
        }
    }

    private String checkNewFile(String fileName) {
        String name = fileName;
        File fileList[] = new File(guiCreator.getCurrentDirectoryForFirst().toString()).listFiles();
        for (int i = 0; i < fileList.length; i++) {
            while (fileList[i].getName().equals(name) || name.isEmpty()) name = OperationController.enterNewFileName(
                    "This name is already used or empty", "Please, enter new name");
        }
        return name;
    }

    @FXML
    private void createNewFileForTableOne() throws IOException {
        createFile(guiCreator.getCurrentDirectoryForSecond().toString());
    }

    @FXML
    private void createNewFileForTableTwo() throws IOException {
        createFile(guiCreator.getCurrentDirectoryForSecond().toString());
    }

    private void createFile(String currentDirectory) throws IOException {
        String fileName = checkNewFile(OperationController.enterNewFileName(
                "Name of new file(and extension through point) or directory", "Please, enter the name"));
        File file = new File(currentDirectory, fileName);
        if (fileName.contains("."))
            file.createNewFile();
        else
            file.mkdir();
        guiCreator.createNewListOfFilesForTableOne();
        guiCreator.createNewListOfFilesForTableTwo();
    }

    private void goNextDirectory(FileInformation file, TableView<FileInformation> tableView) throws IOException {
        StringBuffer currentDirectory;
        if (fileTableTwo.equals(tableView)) {
            currentDirectory = guiCreator.getCurrentDirectoryForSecond();
            savePrevDirectory(currentDirectory.toString(), 2);
            currentDirectory.replace(0, currentDirectory.length() - 1,
                    currentDirectory.toString() + file.getFileName().getValue());
            guiCreator.setDirectoryForSecond(currentDirectory.toString());
            guiCreator.createNewListOfFilesForTableTwo();
        }
        if (fileTableOne.equals(tableView)) {
            currentDirectory = guiCreator.getCurrentDirectoryForFirst();
            savePrevDirectory(currentDirectory.toString(), 1);
            currentDirectory.replace(0, currentDirectory.length() - 1,
                    currentDirectory.toString() + file.getFileName().getValue());
            guiCreator.setDirectoryForFirst(currentDirectory.toString());
            guiCreator.createNewListOfFilesForTableOne();
        }
    }

    private void showFileInFirstTable(FileInformation file) {
        showFile(file, fileTableOne);
    }

    private void showFileInSecondTable(FileInformation file) {
        showFile(file, fileTableTwo);
    }

    private void showFile(FileInformation file, TableView<FileInformation> tableView) {
        if (file != null) {
            tableView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    if (file.getFile().isDirectory()) {
                        try {
                            goNextDirectory(file, tableView);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else
                        try {
                            OperationController.openFileByProgram(file.getFile());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                } else {
                    if (event.getButton() == MouseButton.PRIMARY)
                        return;
                    else
                        try {
                            guiCreator.showFileInfoDialog(file.getFile());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            });
        }
    }

    @FXML
    private void deleteFileForTableOne() throws IOException {
        deleteOperation(fileTableTwo);
    }

    @FXML
    private void deleteFileForTableTwo() throws IOException {
        deleteOperation(fileTableTwo);
    }

    private void deleteOperation(TableView<FileInformation> tableView) {
        Path path;
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            path = Paths.get(tableView.getItems().get(selectedIndex).getFile().getAbsolutePath());
            guiCreator.showProgressOfOperation(path, null, "Delete");
        } else
            OperationController.viewError(guiCreator);
    }

    @FXML
    private void moveFileForTableOne() throws IOException {
        moveOperation(fileTableOne);
    }

    @FXML
    private void moveFileForTableTwo() throws IOException {
        moveOperation(fileTableTwo);
    }

    private void moveOperation(TableView<FileInformation> tableView) {
        Path path;
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            path = Paths.get(tableView.getItems().get(selectedIndex).getFile().getAbsolutePath());
            Path destinition = Paths.get(guiCreator.getCurrentDirectoryForFirst() + "\\"
                    + tableView.getItems().get(selectedIndex).getFile().getName());
            guiCreator.showProgressOfOperation(path, destinition, "Move");
        } else
            OperationController.viewError(guiCreator);
    }

    @FXML
    private void copyFileToTableTwo() throws IOException, InterruptedException {
        copyOperation(fileTableTwo);
    }

    @FXML
    private void copyFileToTableOne() throws IOException {
        copyOperation(fileTableOne);
    }

    private void copyOperation(TableView<FileInformation> tableView) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Path path = Paths.get(tableView.getItems().get(selectedIndex).getFile().getAbsolutePath());
            Path destinition = Paths.get(guiCreator.getCurrentDirectoryForFirst() + "\\"
                    + tableView.getItems().get(selectedIndex).getFile().getName());
            guiCreator.showProgressOfOperation(path, destinition, "Copy");
        } else
            OperationController.viewError(guiCreator);
    }

    @FXML
    private void renameFileForTableOne() throws IOException {
        renameOperation(fileTableOne);
    }

    @FXML
    private void renameFileForTableTwo() throws IOException {
        renameOperation(fileTableTwo);
    }

    private void renameOperation(TableView<FileInformation> tableView) throws IOException {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            File newFile = OperationController.getNewNameOfFile(tableView.getItems().get(selectedIndex).getFile());
            if (newFile != null) {
                tableView.getItems().get(selectedIndex).getFile().renameTo(newFile);
                guiCreator.createNewListOfFilesForTableOne();
                guiCreator.createNewListOfFilesForTableTwo();
            } else
                return;
        } else
            OperationController.viewError(guiCreator);
    }

    @FXML
    private void initialize() {
        fileNameColumnOne.setCellValueFactory(cellData -> cellData.getValue().fileName);
        fileSizeColumnOne.setCellValueFactory(cellData -> cellData.getValue().fileSize);
        fileTypeOne.setCellValueFactory(cellData -> cellData.getValue().image);

        fileNameColumnTwo.setCellValueFactory(cellData -> cellData.getValue().fileName);
        fileSizeColumnTwo.setCellValueFactory(cellData -> cellData.getValue().fileSize);
        fileTypeTwo.setCellValueFactory(cellData -> cellData.getValue().image);

        fileTableOne.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showFileInFirstTable(newValue));
        fileTableTwo.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showFileInSecondTable(newValue));
    }

    @FXML
    private void goToPreviousDirectoryForTableOne() throws IOException {
        guiCreator.setDirectoryForFirst(getPrevDirectory(1));
        guiCreator.createNewListOfFilesForTableOne();
    }

    @FXML
    private void goToPreviousDirectoryForTableTwo() throws IOException {
        guiCreator.setDirectoryForSecond(getPrevDirectory(2));
        guiCreator.createNewListOfFilesForTableTwo();
    }

    @FXML
    private void getStartDirectoryForTableOne() throws IOException {
        guiCreator.setDirectoryForFirst(listOfRootsForTableOne.getValue());
        guiCreator.createNewListOfFilesForTableOne();
    }

    @FXML
    private void getStartDirectoryForTableTwo() throws IOException {
        guiCreator.setDirectoryForSecond(listOfRootsForTableTwo.getValue());
        guiCreator.createNewListOfFilesForTableTwo();
    }

}
