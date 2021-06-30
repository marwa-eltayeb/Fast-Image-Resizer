package com.marwaeltayeb.fir.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.*;

import static com.marwaeltayeb.fir.utils.ResizeManager.getExtension;
import static com.marwaeltayeb.fir.utils.ResizeManager.resizeImages;
import static com.marwaeltayeb.fir.utils.ImageSizeUtils.getSize;

public class MainController implements Initializable {

    @FXML private AnchorPane anchorId;

    @FXML private Label lblOutputPath;
    @FXML private ListView<String> lstImagesList;

    @FXML private ImageView imgPreview;

    @FXML private Label lbType;
    @FXML private Label lbSize;
    @FXML private Label lbDimen;

    @FXML private CheckComboBox<String> comboBoxSizes;
    @FXML private ComboBox<String> comboDir;

    @FXML private TextField editHeight;
    @FXML private TextField editWidth;

    private final ObservableList<String> listOfDirs = FXCollections.observableArrayList("drawable", "mipmap");
    private final ObservableList<String> defaultSizes = FXCollections.observableArrayList("ldpi 36x36", "mdpi 48x48", "tvdpi 64x64", "hdpi 72x72", "xhdpi 96x96", "xxhdpi 144x144", "xxxhdpi 192x192");
    private final Set<String> acceptedExtensions = new HashSet<>(Arrays.asList("png", "jpg", "jpeg", "gif"));

    private String defaultImagePath;
    private String selectedItem;
    private int selectedIndex;

    private final List<File> originalImages = new ArrayList<>();
    private File selectedFolder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstImagesList.setPlaceholder(new Label("Drag & drop your photos here"));

        defaultImagePath = "com/marwaeltayeb/fir/assets/preview.png";
        showImageDetails(defaultImagePath);

        comboDir.setItems(listOfDirs);
        comboBoxSizes.setTitle("Default Sizes");
        comboBoxSizes.getItems().addAll(defaultSizes);
    }

    @FXML
    private void handleDragOver(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        for (File file : files) {
            final String extension = getExtension(file.getName());
            if (acceptedExtensions.contains(extension)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        }
    }

    @FXML
    private void handleDrop(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();

        if (files == null) {
            System.out.println("File is not valid");
            return;
        }

        for (File file : files) {
            final String extension = getExtension(file.getName());
            ObservableList<String> imageList = lstImagesList.getItems();
            if (acceptedExtensions.contains(extension)) {
                // If list of images does not contain the path, add it
                if (!imageList.contains(file.getAbsolutePath())) {
                    imageList.add(file.getAbsolutePath());
                    originalImages.add(file);
                }
            }
        }

        event.setDropCompleted(true);
        event.consume();
    }

    @FXML private void handleOnMouseClicked() {
        selectedItem = lstImagesList.getSelectionModel().getSelectedItem();
        selectedIndex = lstImagesList.getSelectionModel().getSelectedIndex();
        showImageDetails(selectedItem);
    }

    @FXML private void deleteImage() {
        if(selectedItem != null) {
            String deletedItem = lstImagesList.getItems().remove(selectedIndex);
            originalImages.remove(selectedIndex);
            // No item is selected after deletion
            selectedItem = null;
            if (!lstImagesList.getItems().contains(deletedItem)) {
                showImageDetails(defaultImagePath);
            }
        }
    }

    @FXML private void deleteAllImages() {
        lstImagesList.getItems().removeAll(lstImagesList.getItems());
        showImageDetails(defaultImagePath);
    }

    @FXML private void browse() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) anchorId.getScene().getWindow();
        // Set Initial Directory as Documents
        directoryChooser.setInitialDirectory(new JFileChooser().getFileSystemView().getDefaultDirectory());
        selectedFolder = directoryChooser.showDialog(stage);
        if (selectedFolder == null) {
            // Get Documents path
            selectedFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsoluteFile();
        }
        lblOutputPath.setText(selectedFolder.getPath());
    }


    private void showImageDetails(String path) {
        if (path != null) {
            File file;
            Image img;
            if(path.equals(defaultImagePath)){
                file = new File("../src/" + path);
                img = new Image(path);
            }else {
                file =  new File(path);
                img = new Image(file.toURI().toString());
            }

            imgPreview.setImage(img);
            imgPreview.setPreserveRatio(true);

            String imageDimensions = (int) img.getHeight() + "x" + (int) img.getWidth();
            lbDimen.setText(imageDimensions);

            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
            lbType.setText(fileExtension);

            double bytes = file.length();
            String fileSize = String.format("%.2f", bytes / 1024) + " kb";
            lbSize.setText(fileSize);
        }
    }

    @FXML private void resize() {
        String selectedDir = comboDir.getValue();
        ObservableList<String> defaultSizes = comboBoxSizes.getCheckModel().getCheckedItems();

        String widthStr = editWidth.getText();
        String heightStr = editHeight.getText();

        if (!widthStr.isEmpty() && !heightStr.isEmpty()) {

            if(!widthStr.matches("\\d*") || !heightStr.matches("\\d*")){
                showError();
                return;
            }

            int width = Integer.parseInt(widthStr);
            int height = Integer.parseInt(heightStr);

            if (selectedFolder == null) {
                showWarning("Please Select a destination folder first!");
                return;
            }

            resizeImages(originalImages, selectedFolder.getPath(), width, height, selectedDir);
            editHeight.setText("");
            editWidth.setText("");
        } else if (defaultSizes.size() != 0) {

            if (selectedFolder == null) {
                showWarning("Please Select a destination folder first!");
                return;
            }

            for (String size : defaultSizes) {
                int imageSize = getSize(size);
                resizeImages(originalImages, selectedFolder.getPath(),imageSize, imageSize, selectedDir);
            }

        }else {
            showWarning("You must choose a certain size or make a custom size");
        }
    }

    private void showWarning(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING, text, ButtonType.OK);
        alert.initOwner(anchorId.getScene().getWindow());
        alert.showAndWait();
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Dimensions must be positive integers", ButtonType.OK);
        alert.initOwner(anchorId.getScene().getWindow());
        alert.showAndWait();
    }

}

