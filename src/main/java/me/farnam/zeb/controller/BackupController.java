package me.farnam.zeb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class BackupController {
    @FXML
    private RadioButton localBackupRadioBtn;
    @FXML
    private RadioButton googleDriveBackupRadioBtn;
    @FXML
    private GridPane localBackupGridPane;
    @FXML
    private GridPane googleDriveBackupGridPane;
    @FXML
    private TextField backupFileNameTF;

    @FXML
    private void onLocalRadioSelect(ActionEvent event) {
        googleDriveBackupGridPane.setVisible(false);
        localBackupGridPane.setVisible(true);
    }

    @FXML
    private void onGoogleDriveRadioSelect(ActionEvent event) {
        localBackupGridPane.setVisible(false);
        googleDriveBackupGridPane.setVisible(true);
    }
}
