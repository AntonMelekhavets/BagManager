package OperationExecutors;

import View.GUICreator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class OperationController {
    public static void openFileByProgram(File file) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) {
            OperationController.viewWarning("Open File Error", "This file can not open", "Please, select other file");
        }
    }

    public static File getNewNameOfFile(File file) {
        String fileName = checkNameOfFile(enterNewFileName("New name of file or directory", "Please, enter new name"), file);
        if (file != null) {
            File newFile;
            if (file.isDirectory()) {
                newFile = new File(file.getParentFile().getPath() + "\\" + fileName);
            } else {
                String extance = file.getName().substring(file.getName().indexOf("."));
                newFile = new File(file.getParentFile().getPath() + "\\" + fileName + extance);
            }
            return newFile;
        } else
            return null;
    }

    private static String checkNameOfFile(String nameOfFile, File file) {
        String name = nameOfFile;
        File fileList[] = new File(file.getParentFile().getPath().toString()).listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (file.isDirectory()) {
                if (fileList[i].getName().equals(name) || name.isEmpty())
                    name = enterNewFileName("This name is already used or empty", "Please, enter another name");
            } else {
                if (fileList[i].getName().equals(name + file.getName().substring(file.getName().indexOf("."))) || name.isEmpty())
                    name = enterNewFileName("This name is already used or empty", "Please, enter another name");
            }

        }
        return name;
    }

    public static String enterNewFileName(String headerTest, String contentText) {
        TextInputDialog dialog = new TextInputDialog("Name");
        dialog.setTitle("");
        dialog.setHeaderText(headerTest);
        dialog.setContentText(contentText);
        Optional<String> fileName = dialog.showAndWait();
        if (fileName.isPresent())
            return fileName.get();
        else
            return null;
    }

    public static void viewError(GUICreator guiCreator) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(guiCreator.getPrimaryStage());
        alert.setTitle("No selection");
        alert.setHeaderText("No file selected");
        alert.setContentText("Please select file");
        alert.showAndWait();
    }

    public static void viewWarning(String title, String header, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
