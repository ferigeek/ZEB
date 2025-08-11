package me.farnam.zeb.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import me.farnam.zeb.App;

import java.io.IOException;

public class MainController {
    @FXML
    private Tab dirPassChooseTab;
    @FXML
    private Tab gitTab;
    @FXML
    private Tab backupTab;

    public void initialize() throws IOException {
        dirPassChooseTab.setContent(loadFXML("tabs/dir-pass-choose.fxml"));
    }

    private Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(App.class.getResource(fxml));
    }
}
