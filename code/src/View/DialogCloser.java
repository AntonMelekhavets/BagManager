package View;

import java.io.IOException;

import javafx.stage.Stage;

public class DialogCloser implements Runnable {
    private Stage stage;
    private GUICreator mainApp;

    public DialogCloser(Stage stage, GUICreator mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
    }

    public void run() {
        stage.close();
        try {
            mainApp.createNewListOfFilesForTableOne();
            mainApp.createNewListOfFilesForTableTwo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
