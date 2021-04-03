package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Fast Image Resizer");
        primaryStage.setScene(new Scene(root, 650, 400));
        primaryStage.getIcons().add(new Image("/sample/assets/logo.png"));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("stylesheet/application.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
