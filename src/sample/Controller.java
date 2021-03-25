package sample;

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

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstImagesList.setPlaceholder(new Label("Drag & drop your photos here"));
    }

    @FXML
    private void handleDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void handleDrop(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        if(files != null){
            for (File file : files) {
                lstImagesList.getItems().add(file.getAbsolutePath());
            }
        } else{
            System.out.println("File is not valid");
        }
    }

}

