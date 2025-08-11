package me.farnam.zeb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
        scene = new Scene(fxmlLoader.load(), 600, 475);
        stage.setScene(scene);
        stage.setTitle("Zip / Encrypt / Backup");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}