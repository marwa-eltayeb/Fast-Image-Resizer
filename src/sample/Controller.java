package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static sample.Resizer.resizeImages;

public class Controller implements Initializable {

    @FXML
    Button btnBrowse;

    @FXML
    Label lblOutputPath;

    @FXML
    ListView<String> lstImagesList;

    @FXML
    Button btnDelete;

    @FXML
    Button btnDeleteAll;

    @FXML
    Button btnResize;

    @FXML
    ImageView imgPreview;

    @FXML
    Label lbType;

    @FXML
    Label lbSize;

    @FXML
    Label lbDimen;

    @FXML
    ComboBox<String> comboSizes;

    @FXML
    ComboBox<String> comboDir;

    @FXML
    TextField editHeight;

    @FXML
    TextField editWidth;

    @FXML
    CheckBox cbRatio;

    ObservableList<String> listOfDirs = FXCollections.observableArrayList("drawable", "mipmap");
    ObservableList<String> defaultSizes = FXCollections.observableArrayList("ldpi 36x36", "mdpi 48x48", "tvdpi 64x64", "hdpi 72x72", "xhdpi 96x96", "xxhdpi 144x144", "xxxhdpi 192x192" );


    private String defaultImagePath;
    private int selectedIndex;

    private List<File> originalImages = new ArrayList<File>();
    private File selectedFolder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstImagesList.setPlaceholder(new Label("Drag & drop your photos here"));

        defaultImagePath = "src/sample/assets/preview.png";
        showImageDetails(defaultImagePath);

        comboDir.setItems(listOfDirs);
        comboSizes.setItems(defaultSizes);

        boolean ifRatioSelected = cbRatio.isSelected();
    }

    @FXML
    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void handleDrop(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        if(files != null){
            for (File file : files) {
                // If list of images does not contain the path, add it
                if (!lstImagesList.getItems().contains(file.getAbsolutePath())) {
                    lstImagesList.getItems().add(file.getAbsolutePath());
                    originalImages.add(file);
                }
            }
        } else {
            System.out.println("File is not valid");
        }
    }

    public void handleOnMouseClicked(MouseEvent mouseEvent) {
        String selectedItem = lstImagesList.getSelectionModel().getSelectedItem();
        selectedIndex = lstImagesList.getSelectionModel().getSelectedIndex();
        showImageDetails(selectedItem);
    }

    public void deleteImage(ActionEvent actionEvent) {
        String deletedItem = lstImagesList.getItems().remove(selectedIndex);
        if (!lstImagesList.getItems().contains(deletedItem)) {
            showImageDetails(defaultImagePath);
        }
    }

    public void deleteAllImages(ActionEvent actionEvent) {
        lstImagesList.getItems().removeAll(lstImagesList.getItems());
        showImageDetails(defaultImagePath);
    }

    public void browse(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        // Set Initial Directory as Documents
        directoryChooser.setInitialDirectory(new JFileChooser().getFileSystemView().getDefaultDirectory());
        selectedFolder = directoryChooser.showDialog(null);
        if (selectedFolder == null) {
            // Get Documents path
            selectedFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsoluteFile();
        }
        lblOutputPath.setText(selectedFolder.getPath());
    }

    private void showImageDetails(String path) {
        if (path != null) {
            Image img = new Image("file:" + path);
            imgPreview.setImage(img);

            String imageDimensions = (int) img.getHeight() + "x" + (int) img.getWidth();
            lbDimen.setText(imageDimensions);

            File file = new File(path);
            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
            lbType.setText(fileExtension);

            double bytes = file.length();
            String fileSize = String.format("%.2f", bytes / 1024) + " kb";
            lbSize.setText(fileSize);
        }
    }

    public void resize(ActionEvent actionEvent) {
        String selectedDir = comboDir.getValue();
        int width = Integer.parseInt(editWidth.getText().trim());
        int height = Integer.parseInt(editHeight.getText().trim());

        resizeImages(originalImages, selectedFolder.getPath(), width, height, "png",selectedDir);
    }
}

