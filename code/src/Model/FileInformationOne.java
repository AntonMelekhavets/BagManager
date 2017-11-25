package Model;

import java.io.File;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

public class FileInformationOne {
	public final StringProperty fileNameForFirstTable;
	public final StringProperty fileSizeForFirstTable;
	public final ObjectProperty<ImageView> imageViewFirst;
	private final File fileFirst;

	public FileInformationOne() {
		fileNameForFirstTable = null;
		fileSizeForFirstTable = null;
		imageViewFirst = null;
		fileFirst = null;
	}

	public FileInformationOne(String fileNameOne, String fileSizeOne, ImageView imageViewOne, File fileFirst) {
		this.fileNameForFirstTable = new SimpleStringProperty(fileNameOne);
		this.fileSizeForFirstTable = new SimpleStringProperty(fileSizeOne);
		this.imageViewFirst = new SimpleObjectProperty<ImageView>(imageViewOne);
		this.fileFirst = fileFirst;
	}

	public StringProperty getFileNameFirst() {
		return fileNameForFirstTable;
	}

	public StringProperty getFileSizeFirst() {
		return fileSizeForFirstTable;
	}

	public File getFile() {
		return fileFirst;
	}

}
