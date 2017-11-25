package Model;

import java.io.File;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

public class FileInformationTwo {
	public final ObjectProperty<ImageView> imageViewSecond;
	private final File fileSecond;
	public final StringProperty fileSizeForSecondTable;
	public final StringProperty fileNameForSecondTable;

	public FileInformationTwo() {
		fileSizeForSecondTable = null;
		imageViewSecond = null;
		fileSecond = null;
		fileNameForSecondTable = null;
	}

	public FileInformationTwo(String fileNameTwo, String fileSizeTwo, ImageView imageViewTwo, File fileSecond) {
		this.fileNameForSecondTable = new SimpleStringProperty(fileNameTwo);
		this.fileSizeForSecondTable = new SimpleStringProperty(fileSizeTwo);
		this.imageViewSecond = new SimpleObjectProperty<ImageView>(imageViewTwo);
		this.fileSecond = fileSecond;
	}

	public StringProperty getFileSizeForSecondTable() {
		return fileSizeForSecondTable;
	}

	public StringProperty getFileNameForSecondTable() {
		return fileNameForSecondTable;
	}

	public File getFile() {
		return fileSecond;
	}
}
