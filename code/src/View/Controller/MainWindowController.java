package View.Controller;

import Model.FileInformationOne;
import Model.FileInformationTwo;
import OperationExecutors.OperationController;
import View.GUICreator;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainWindowController {
	@FXML
	private TableView<FileInformationOne> fileTableOne;
	@FXML
	private TableColumn<FileInformationOne, String> fileNameColumnOne;
	@FXML
	private TableColumn<FileInformationOne, String> fileSizeColumnOne;
	@FXML
	private TableColumn<FileInformationOne, ImageView> fileTypeOne;

	@FXML
	private TableView<FileInformationTwo> fileTableTwo;
	@FXML
	private TableColumn<FileInformationTwo, String> fileNameColumnTwo;
	@FXML
	private TableColumn<FileInformationTwo, String> fileSizeColumnTwo;
	@FXML
	private TableColumn<FileInformationTwo, ImageView> fileTypeTwo;

	@FXML
	private ComboBox<String> listOfRootsForTableOne;
	@FXML
	private ComboBox<String> listOfRootsForTableTwo;

	private StringBuffer previousDirectoryForFirstTable = new StringBuffer();
	private StringBuffer previousDirectoryForSecondTable = new StringBuffer();
	private static GUICreator guiCreator;

	private void goNextDirectoryFirstTable(FileInformationOne file) throws IOException {
		StringBuffer currentDirectory = guiCreator.getCurrentDirectoryForFirst();
		previousDirectoryForFirstTable.replace(0, currentDirectory.length() - 1, currentDirectory.toString());
		currentDirectory.replace(0, currentDirectory.length() - 1,
				currentDirectory.toString() + file.getFileNameFirst().getValue());
		guiCreator.setDirectoryForFirst(currentDirectory.toString());
		guiCreator.createNewListOfFilesForTableOne();
	}

	@FXML
	private void createNewFileForTableOne() throws IOException {
		String fileName = checkNewFile(OperationController.enterNewFileName(
				"Name of new file(and extension through point) or directory", "Please, enter the name"));
		File file = new File(guiCreator.getCurrentDirectoryForFirst().toString(), fileName);
		if (fileName.contains("."))
			file.createNewFile();
		else
			file.mkdir();
		guiCreator.createNewListOfFilesForTableOne();
		guiCreator.createNewListOfFilesForTableTwo();
	}

	private String checkNewFile(String fileName) {
		String name = fileName;
		File fileList[] = new File(guiCreator.getCurrentDirectoryForFirst().toString()).listFiles();
		for (int i = 0; i < fileList.length; i++) {
			while (fileList[i].getName().equals(name) || name.isEmpty())
				name = OperationController.enterNewFileName("This name is already used or empty", "Please, enter new name");
		}
		return name;
	}

	@FXML
	private void createNewFileForTableTwo() throws IOException {
		String fileName = OperationController.enterNewFileName("Name of new file(and extension through point) or directory",
				"Please, enter the name");
		File file = new File(guiCreator.getCurrentDirectoryForSecond().toString(), fileName);
		if (fileName.contains("."))
			file.createNewFile();
		else
			file.mkdir();
		guiCreator.createNewListOfFilesForTableOne();
		guiCreator.createNewListOfFilesForTableTwo();
	}

	private void goNextDirectorySecondTable(FileInformationTwo file) throws IOException {
		StringBuffer currentDirectory = guiCreator.getCurrentDirectoryForSecond();
		previousDirectoryForSecondTable.replace(0, currentDirectory.length() - 1, currentDirectory.toString());
		currentDirectory.replace(0, currentDirectory.length() - 1,
				currentDirectory.toString() + file.getFileNameForSecondTable().getValue());
		guiCreator.setDirectoryForSecond(currentDirectory.toString());
		guiCreator.createNewListOfFilesForTableTwo();
	}

	private void showFileInFirstTable(FileInformationOne file) {
		if (file != null) {
			fileTableOne.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2) {
					if (file.getFile().isDirectory())
						try {
							goNextDirectoryFirstTable(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					else
						try {
							OperationController.openFileByProgram(file.getFile());
						} catch (Exception e) {
							e.printStackTrace();
						}
				} else {
					if(event.getButton() == MouseButton.PRIMARY) {
						return;
					} else
						try {
							guiCreator.showFileInfoDialog(file.getFile());
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			});
		}
	}

	private void showFileInSecondTable(FileInformationTwo file) {
		if (file != null) {
			fileTableTwo.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2) {
					if (file.getFile().isDirectory()) {
						try {
							goNextDirectorySecondTable(file);
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
					if(event.getButton() == MouseButton.PRIMARY)
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
		Path path;
		int selectedIndex = fileTableOne.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			path = Paths.get(fileTableOne.getItems().get(selectedIndex).getFile().getAbsolutePath());
			guiCreator.showProgressOfOperation(path, null, "Delete");
		} else
			OperationController.viewError(guiCreator);
	}

	@FXML
	private void deleteFileForTableTwo() throws IOException {
		Path path;
		int selectedIndex = fileTableTwo.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			path = Paths.get(fileTableTwo.getItems().get(selectedIndex).getFile().getAbsolutePath());
			guiCreator.showProgressOfOperation(path, null, "Delete");
		} else
			OperationController.viewError(guiCreator);
	}

	@FXML
	private void moveFileForTableOne() throws IOException {
		Path path;
		int selectedIndex = fileTableOne.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			path = Paths.get(fileTableOne.getItems().get(selectedIndex).getFile().getAbsolutePath());
			Path destinition = Paths.get(guiCreator.getCurrentDirectoryForSecond() + "\\"
					+ fileTableOne.getItems().get(selectedIndex).getFile().getName());
			guiCreator.showProgressOfOperation(path, destinition, "Move");
		} else
			OperationController.viewError(guiCreator);
	}

	@FXML
	private void moveFileForTableTwo() throws IOException {
		Path path;
		int selectedIndex = fileTableTwo.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			path = Paths.get(fileTableTwo.getItems().get(selectedIndex).getFile().getAbsolutePath());
			Path destinition = Paths.get(guiCreator.getCurrentDirectoryForFirst() + "\\"
					+ fileTableTwo.getItems().get(selectedIndex).getFile().getName());
			guiCreator.showProgressOfOperation(path, destinition, "Move");
		} else
			OperationController.viewError(guiCreator);
	}

	@FXML
	private void copyFileToTableTwo() throws IOException, InterruptedException {
		int selectedIndex = fileTableOne.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Path path = Paths.get(fileTableOne.getItems().get(selectedIndex).getFile().getAbsolutePath());
			Path destinition = Paths.get(guiCreator.getCurrentDirectoryForSecond() + "\\"
					+ fileTableOne.getItems().get(selectedIndex).getFile().getName());
			guiCreator.showProgressOfOperation(path, destinition, "Copy");
		} else
			OperationController.viewError(guiCreator);
	}

	@FXML
	private void copyFileToTableOne() throws IOException {
		int selectedIndex = fileTableTwo.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Path path = Paths.get(fileTableTwo.getItems().get(selectedIndex).getFile().getAbsolutePath());
			Path destinition = Paths.get(guiCreator.getCurrentDirectoryForFirst() + "\\"
					+ fileTableTwo.getItems().get(selectedIndex).getFile().getName());
			guiCreator.showProgressOfOperation(path, destinition, "Copy");
		} else
			OperationController.viewError(guiCreator);
	}

	@FXML
	private void renameFileForTableOne() throws IOException {
		int selectedIndex = fileTableOne.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			File newFile = OperationController.getNewNameOfFile(fileTableOne.getItems().get(selectedIndex).getFile());
			if (newFile != null) {
				fileTableOne.getItems().get(selectedIndex).getFile().renameTo(newFile);
				guiCreator.createNewListOfFilesForTableOne();
				guiCreator.createNewListOfFilesForTableTwo();
			} else
				return;
		} else
			OperationController.viewError(guiCreator);
	}

	public static GUICreator getGuiCreator() {
		return guiCreator;
	}

	@FXML
	private void renameFileForTableTwo() throws IOException {
		int selectedIndex = fileTableTwo.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			File newFile = OperationController.getNewNameOfFile(fileTableTwo.getItems().get(selectedIndex).getFile());
			if (newFile != null) {
				fileTableTwo.getItems().get(selectedIndex).getFile().renameTo(newFile);
				guiCreator.createNewListOfFilesForTableOne();
				guiCreator.createNewListOfFilesForTableTwo();
			} else
				return;
		} else
			OperationController.viewError(guiCreator);
	}

	@FXML
	private void initialize() {
		fileNameColumnOne.setCellValueFactory(cellData -> cellData.getValue().fileNameForFirstTable);
		fileSizeColumnOne.setCellValueFactory(cellData -> cellData.getValue().fileSizeForFirstTable);
		fileTypeOne.setCellValueFactory(cellData -> cellData.getValue().imageViewFirst);

		fileNameColumnTwo.setCellValueFactory(cellData -> cellData.getValue().fileNameForSecondTable);
		fileSizeColumnTwo.setCellValueFactory(cellData -> cellData.getValue().fileSizeForSecondTable);
		fileTypeTwo.setCellValueFactory(cellData -> cellData.getValue().imageViewSecond);

		fileTableOne.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showFileInFirstTable(newValue));
		fileTableTwo.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showFileInSecondTable(newValue));
	}

	public void setMainApp(GUICreator mainApp) {
		MainWindowController.guiCreator = mainApp;
		fileTableOne.setItems(mainApp.getFileDataFirst());
		fileTableTwo.setItems(mainApp.getFileDataSecond());
		listOfRootsForTableOne.setItems(mainApp.getListOfRootsForTableOne());
		listOfRootsForTableTwo.setItems(mainApp.getListOfRootsForTableTwo());
	}

	@FXML
	private void goToPreviousDirectoryForTableOne() throws IOException {
		try {
			if (fileTableOne.getItems().size() == 0) {
				guiCreator.setDirectoryForFirst(previousDirectoryForFirstTable.toString());
			} else {
				previousDirectoryForFirstTable = new StringBuffer(
						fileTableOne.getItems().get(0).getFile().getParentFile().getParentFile().getPath());
				guiCreator.setDirectoryForFirst(
						fileTableOne.getItems().get(0).getFile().getParentFile().getParentFile().getPath());
			}
			guiCreator.createNewListOfFilesForTableOne();
		} catch (NullPointerException e) {
		}
	}

	@FXML
	private void goToPreviousDirectoryForTableTwo() throws IOException {
		try {
			if (fileTableTwo.getItems().size() == 0) {
				guiCreator.setDirectoryForSecond(previousDirectoryForSecondTable.toString());
			} else {
				previousDirectoryForSecondTable = new StringBuffer(
						fileTableTwo.getItems().get(0).getFile().getParentFile().getParentFile().getPath());
				guiCreator.setDirectoryForSecond(
						fileTableTwo.getItems().get(0).getFile().getParentFile().getParentFile().getPath());
			}
			guiCreator.createNewListOfFilesForTableTwo();
		} catch (NullPointerException e) {
		}
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
