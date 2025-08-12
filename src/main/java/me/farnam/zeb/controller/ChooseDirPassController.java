package me.farnam.zeb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class ChooseDirPassController {
    @FXML
    private TextField dirPathTF;
    @FXML
    private Button chooseBtn;
    @FXML
    private CheckBox passwordCheck;
    @FXML
    private Label setPassLabel;
    @FXML
    private PasswordField setPassPF;
    @FXML
    private Label repeatPassLabel;
    @FXML
    private PasswordField repeatPassPF;
    @FXML
    private ImageView passwordCheckIcon;

    /**
     * If "Use password" checkbox is selected, related password fields are enabled.
     * <br/>
     * Otherwise, on deselecting the checkbox, related password fields are disabled.
     * @param event
     * ActionEvent
     */
    @FXML
    private void onPassCheck(ActionEvent event) {
        if (passwordCheck.isSelected()) {
            disablePasswordFields(false);
        } else {
            disablePasswordFields(true);
        }
    }

    /**
     * Directory choosing dialog is shown to user after hitting "choose" button.
     * The Chosen directory will be shown in the directory path text field.
     * @param event
     * ActionEvent
     */
    @FXML
    private void onDirChooseBtn(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        directoryChooser.setTitle("Choose directory...");
        File chosenPath = directoryChooser.showDialog(chooseBtn.getScene().getWindow());

        if (chosenPath == null || !chosenPath.exists()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please choose a valid directory!");
            alert.show();
            return;
        }

        dirPathTF.setText(chosenPath.getAbsolutePath());
    }

    @FXML
    private void onRepeatPasswordChange(KeyEvent event) {
        String initPass = setPassPF.getText();
        String repeatPass = repeatPassPF.getText();
        if (initPass.equals(repeatPass)) {
            Image checkIcon = new Image(getClass().getResourceAsStream("/icons/check.png"));
            passwordCheckIcon.setImage(checkIcon);
        } else {
            Image crossIcon = new Image(getClass().getResourceAsStream("/icons/close.png"));
            passwordCheckIcon.setImage(crossIcon);
        }
    }

    private void disablePasswordFields(boolean tf) {
        setPassLabel.setDisable(tf);
        setPassPF.setDisable(tf);
        repeatPassLabel.setDisable(tf);
        repeatPassPF.setDisable(tf);
    }
}
