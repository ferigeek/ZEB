package me.farnam.zeb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private final String version = "1.0.0";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 475);
        stage.setScene(scene);
        stage.setTitle(String.format("Zip / Encrypt / Backup - %s", version));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}