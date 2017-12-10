package Model;

import java.io.File;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

public class FileInformation {
    public final StringProperty fileName;
    public final StringProperty fileSize;
    public final ObjectProperty<ImageView> image;
    private final File file;

    public FileInformation() {
        fileName = null;
        fileSize = null;
        image = null;
        file = null;
    }

    public FileInformation(String fileName, String fileSize, ImageView imageView, File file) {
        this.fileName = new SimpleStringProperty(fileName);
        this.fileSize = new SimpleStringProperty(fileSize);
        this.image = new SimpleObjectProperty<ImageView>(imageView);
        this.file = file;
    }

    public StringProperty getFileName() {
        return fileName;
    }

    public StringProperty getFileSize() {
        return fileSize;
    }

    public File getFile() {
        return file;
    }
}
