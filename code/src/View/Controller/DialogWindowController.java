package View.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DialogWindowController {

    @FXML
    private Label fileNameLabel;
    @FXML
    private Label fileSizeLabel;
    @FXML
    private Label fileCreationLabel;
    @FXML
    private Label fileLastModifiedLabel;
    @FXML
    private Label fileAccessLabel;
    @FXML
    private Label fileDirectoryLabel;

    private Stage stage;

    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }

    public void setFileInfo(File file) throws IOException {
        OperationExecutors.SizeVisitor size = new OperationExecutors.SizeVisitor();
        try {
            Files.walkFileTree(Paths.get(file.getPath()), size);
        } catch (IOException e) {
            e.getMessage();
        }

        Path path = Paths.get(file.getPath());

        fileSizeLabel.setText(size.getSizeOfFile());
        fileNameLabel.setText(file.getName());
        fileCreationLabel.setText(Files.getAttribute(path, "creationTime").toString().substring(0, 10) + " "
                + Files.getAttribute(path, "creationTime").toString().substring(11, 19));
        fileLastModifiedLabel.setText(Files.getAttribute(path, "lastModifiedTime").toString().substring(0, 10) + " "
                + Files.getAttribute(path, "lastModifiedTime").toString().substring(11, 19));
        fileDirectoryLabel.setText(file.getPath());
        if (Files.isReadable(path))
            fileAccessLabel.setText("Read");
        if (Files.isWritable(path))
            fileAccessLabel.setText(fileAccessLabel.getText() + " Write");
        if (Files.isHidden(path))
            fileAccessLabel.setText(fileAccessLabel.getText() + " Hidden");
    }

    @FXML
    public void okClicked() {
        stage.close();
    }
}
