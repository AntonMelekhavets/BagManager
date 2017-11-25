package View.Controller;

import Model.FileInformationOne;
import Model.FileInformationTwo;
import View.GUICreator;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.io.IOException;

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
	}

	private String checkNewFile(String fileName) {
		return null;
	}

	@FXML
	private void createNewFileForTableTwo() throws IOException {
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
	}

	private void showFileInSecondTable(FileInformationTwo file) {
	}

	@FXML
	private void deleteFileForTableOne() throws IOException {
	}

	@FXML
	private void deleteFileForTableTwo() throws IOException {
	}

	@FXML
	private void moveFileForTableOne() throws IOException {
	}

	@FXML
	private void moveFileForTableTwo() throws IOException {
	}

	@FXML
	private void copyFileToTableTwo() throws IOException, InterruptedException {
	}

	@FXML
	private void copyFileToTableOne() throws IOException {
	}

	@FXML
	private void renameFileForTableOne() throws IOException {
	}
	
	public static GUICreator getGuiCreator() {
		return guiCreator;
	}

	@FXML
	private void renameFileForTableTwo() throws IOException {
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
	}

	@FXML
	private void goToPreviousDirectoryForTableTwo() throws IOException {
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
