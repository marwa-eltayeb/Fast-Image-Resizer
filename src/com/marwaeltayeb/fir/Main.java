package com.marwaeltayeb.fir;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
        primaryStage.setTitle("Fast Image ResizeManager");
        primaryStage.setScene(new Scene(root, 650, 400));
        primaryStage.getIcons().add(new Image("/com/marwaeltayeb/fir/assets/logo.png"));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("stylesheet/application.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
