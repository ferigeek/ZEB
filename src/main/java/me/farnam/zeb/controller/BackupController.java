package me.farnam.zeb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import me.farnam.zeb.backup.BackupMethod;

import java.io.File;

public class BackupController {
    private File backupOutputDirectory;

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
    private Button backupOutputChooseBtn;
    @FXML
    private TextField backupPathTF;

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

    @FXML
    private void onChooseBtn(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.setTitle("Choose backup directory ...");
        File selectedPath = chooser.showDialog(backupOutputChooseBtn.getScene().getWindow());

        if (selectedPath == null || !selectedPath.exists()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please choose a valid directory!");
            alert.show();
            return;
        }

        this.backupOutputDirectory = selectedPath;
        backupPathTF.setText(selectedPath.getAbsolutePath());
    }

    public BackupMethod getBackupMethod() {
        if (localBackupRadioBtn.isSelected()) {
            return BackupMethod.Local;
        } else {
            return BackupMethod.GoogleDrive;
        }
    }

    public File getLocalBackupPath() { return this.backupOutputDirectory; }

    public String getLocalBackupFileName() {
        return backupFileNameTF.getText();
    }
}
