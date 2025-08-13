package me.farnam.zeb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import me.farnam.zeb.App;

import java.io.File;
import java.io.IOException;

public class MainController {
    @FXML
    private Tab dirPassChooseTab;
    @FXML
    private Tab gitTab;
    @FXML
    private Tab backupTab;
    @FXML
    private Button submitBtn;
    @FXML
    private TextArea logConsoleTextArea;

    public void initialize() throws IOException {
        logConsoleTextArea.appendText("Welcome to ZEB \uD83D\uDD10!\nLog:\n");
        dirPassChooseTab.setContent(loadFXML("tabs/dir-pass-choose.fxml"));
        gitTab.setContent(loadFXML("tabs/git.fxml"));
        backupTab.setContent(loadFXML("tabs/backup.fxml"));
    }

    private Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(App.class.getResource(fxml));
    }

    @FXML
    private void onSubmitBtn(ActionEvent event) {

    }
}
