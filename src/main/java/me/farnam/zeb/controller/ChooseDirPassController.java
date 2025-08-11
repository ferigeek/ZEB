package me.farnam.zeb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private void onPassCheck(ActionEvent event) {
        if (passwordCheck.isSelected()) {
            disablePasswordFields(false);
        } else {
            disablePasswordFields(true);
        }
    }

    private void disablePasswordFields(boolean tf) {
        setPassLabel.setDisable(tf);
        setPassPF.setDisable(tf);
        repeatPassLabel.setDisable(tf);
        repeatPassPF.setDisable(tf);
    }
}
