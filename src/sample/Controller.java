package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
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
        Image img =new Image("file:src/sample/assets/preview.png");
        imgPreview.setImage(img);

        lstImagesList.setPlaceholder(new Label("Drag & drop your photos here\n      or click to add photos"));
    }
}

