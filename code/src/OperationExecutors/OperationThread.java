package OperationExecutors;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import View.DialogCloser;
import View.GUICreator;
import javafx.application.Platform;
import javafx.stage.Stage;

public class OperationThread extends Thread {
    private Path path;
    private Path destinition;
    private String typeOfOperation;
    private GUICreator mainApp;
    private Stage stage;

    public OperationThread(Path path, Path destinition, String typeOfOperation, GUICreator mainApp, Stage stage) {
        this.path = path;
        this.destinition = destinition;
        this.typeOfOperation = typeOfOperation;
        this.mainApp = mainApp;
        this.stage = stage;
    }

    public void run() {
        try {
            switch (typeOfOperation) {
                case "Copy":
                    copyOperation();
                    break;
                case "Move":
                    moveOperation();
                    break;
                case "Delete":
                    deleteOperation();
            }
            Platform.runLater(new DialogCloser(stage, mainApp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyOperation() throws IOException {
        Files.walkFileTree(path, new CopyVisitor(path, destinition));
    }

    private void moveOperation() throws IOException {
        try {
            Files.move(path, destinition, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException e) {
            Files.walkFileTree(path, new CopyVisitor(path, destinition));
            Files.walkFileTree(path, new DeleteVisitor());
        }
    }

    private void deleteOperation() throws IOException {
        try {
            Files.walkFileTree(path, new DeleteVisitor());
        } catch (AccessDeniedException e) {
            e.getMessage();
        }
    }
}
