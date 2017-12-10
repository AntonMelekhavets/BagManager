package View;

import Model.FileInformation;
import OperationExecutors.OperationThread;
import View.Controller.DialogWindowController;
import View.Controller.MainWindowController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GUICreator extends Application {
    private StringBuffer baseDirectoryForTableOne;
    private StringBuffer baseDirectoryForTableTwo;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<FileInformation> fileDataFirst = FXCollections.observableArrayList();
    private ObservableList<FileInformation> fileDataSecond = FXCollections.observableArrayList();
    private ObservableList<String> listOfRootsForTableOne = FXCollections.observableArrayList();
    private ObservableList<String> listOfRootsForTableTwo = FXCollections.observableArrayList();

    public GUICreator() throws IOException {
        File fileRoot[] = File.listRoots();
        for (int i = 0; i < fileRoot.length; i++) {
            listOfRootsForTableTwo.add(fileRoot[i].toString());
            baseDirectoryForTableTwo = new StringBuffer(fileRoot[0].toString());
            listOfRootsForTableOne.add(fileRoot[i].toString());
            baseDirectoryForTableOne = new StringBuffer(fileRoot[0].toString());
        }
        ImageView imageViewFirst, imageViewSecond;
        File fileList[] = new File(baseDirectoryForTableOne.toString()).listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                imageViewFirst = new ImageView(new Image("file:Source\\File.png"));
                imageViewSecond = new ImageView(new Image("file:Source\\File.png"));
            } else {
                imageViewFirst = new ImageView(new Image("file:Source\\Directory.png"));
                imageViewSecond = new ImageView(new Image("file:Source\\Directory.png"));
            }
            fileDataFirst.add(new FileInformation(fileList[i].getName(), Files.getAttribute
                    (Paths.get(fileList[i].getPath()), "creationTime").toString().substring(0, 10), imageViewFirst, fileList[i]));
            fileDataSecond.add(new FileInformation(fileList[i].getName(), Files.getAttribute
                    (Paths.get(fileList[i].getPath()), "creationTime").toString().substring(0, 10), imageViewSecond, fileList[i]));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setDirectoryForFirst(String directory) {
        baseDirectoryForTableOne.replace(0, baseDirectoryForTableOne.length(), directory);
    }

    public void setDirectoryForSecond(String directory) {
        baseDirectoryForTableTwo.replace(0, baseDirectoryForTableTwo.length(), directory);
    }

    public StringBuffer getCurrentDirectoryForFirst() {
        return baseDirectoryForTableOne;
    }

    public StringBuffer getCurrentDirectoryForSecond() {
        return baseDirectoryForTableTwo;
    }

    public void showFileInfoDialog(File file) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUICreator.class.getResource("DialogWindow.fxml"));
            AnchorPane anchor = (AnchorPane) loader.load();

            Stage stage = new Stage();
            stage.setTitle("FileInformation");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(anchor);
            stage.setScene(scene);

            DialogWindowController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setFileInfo(file);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProgressOfOperation(Path path, Path destinition, String typeOfOperation) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUICreator.class.getResource("DialogWindow.fxml"));
            AnchorPane anchor = (AnchorPane) loader.load();

            Stage stage = new Stage();
            stage.setTitle("ProgressOfOperation");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(null);
            Scene scene = new Scene(anchor);
            stage.setScene(scene);

            DialogWindowController controller = loader.getController();
            controller.setDialogStage(stage);
            stage.show();
            OperationThread thread = new OperationThread(path, destinition, typeOfOperation,
                    MainWindowController.getMainApp(), stage);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNewListOfFilesForTableOne() throws IOException {
        fileDataFirst.clear();
        ImageView imageViewFirst;
        File fileList[] = new File(baseDirectoryForTableOne.toString()).listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile())
                imageViewFirst = new ImageView(new Image("file:Source\\File.png"));
            else
                imageViewFirst = new ImageView(new Image("file:Source\\Directory.png"));
            fileDataFirst.add(new FileInformation(fileList[i].getName(), Files.getAttribute
                    (Paths.get(fileList[i].getPath()), "creationTime").toString().substring(0, 10), imageViewFirst, fileList[i]));
        }
    }

    public void createNewListOfFilesForTableTwo() throws IOException {
        fileDataSecond.clear();
        ImageView imageViewSecond;
        File fileList[] = new File(baseDirectoryForTableTwo.toString()).listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile())
                imageViewSecond = new ImageView(new Image("file:Source\\File.png"));
            else
                imageViewSecond = new ImageView(new Image("file:Source\\Directory.png"));
            fileDataSecond.add(new FileInformation(fileList[i].getName(), Files.getAttribute
                    (Paths.get(fileList[i].getPath()), "creationTime").toString().substring(0, 10), imageViewSecond, fileList[i]));
        }
    }

    public ObservableList<FileInformation> getFileDataFirst() {
        return fileDataFirst;
    }

    public ObservableList<FileInformation> getFileDataSecond() {
        return fileDataSecond;
    }

    public ObservableList<String> getListOfRootsForTableOne() {
        return listOfRootsForTableOne;
    }

    public ObservableList<String> getListOfRootsForTableTwo() {
        return listOfRootsForTableTwo;
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("BagManager");
        this.primaryStage.getIcons().add(new Image("file:Source\\Main_icon.png"));
        initRootLayout();
        showFileOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUICreator.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showFileOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUICreator.class.getResource("MainWindow.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            rootLayout.setCenter(anchorPane);
            View.Controller.MainWindowController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
