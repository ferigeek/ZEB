package me.farnam.zeb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class GitController {
    @FXML
    private CheckBox gitCheck;
    @FXML
    private GridPane gitCommitMessageGridPain;
    @FXML
    private RadioButton timestampRadioButton;
    @FXML
    private RadioButton customMessageRadioButton;
    @FXML
    private TextArea customCommitMessageTA;

    @FXML
    private void onGitCheckSelect(ActionEvent event) {
        gitCommitMessageGridPain.setDisable(!gitCheck.isSelected());
    }

    @FXML
    private void onCustomRadioSelect(ActionEvent event) {
        if (customMessageRadioButton.isSelected()) {
            customCommitMessageTA.setVisible(true);
        }
    }

    @FXML
    private void onTimestampSelect(ActionEvent event) {
        if (timestampRadioButton.isSelected()) {
            customCommitMessageTA.setVisible(false);
        }
    }
}
