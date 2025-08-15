package me.farnam.zeb.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import me.farnam.zeb.App;
import me.farnam.zeb.Submit;

import java.io.IOException;

public class MainController {
    private Submit submit;

    @FXML
    private TitledPane chooseDirPassTP;
    @FXML
    private TitledPane gitTP;
    @FXML
    private TitledPane backupTP;
    @FXML
    private TextArea logConsoleTextArea;
    @FXML
    private Accordion accordion;

    /**
     * Sets some of the initial settings of the UI. <br/>
     * Loads UI for each pane and sets them up. <br/>
     * Creates and holds a <code>Submit</code> object
     * to hold reference to other controllers and access their information later.
     */
    public void initialize() {
        logConsoleTextArea.appendText("Welcome to ZEB \uD83D\uDD10!\nLog:\n");
        accordion.setExpandedPane(chooseDirPassTP);

        try {
            FXMLLoader chooseDirPassLoader = loadFXML("tabs/dir-pass-choose.fxml");
            chooseDirPassTP.setContent(chooseDirPassLoader.load());
            FXMLLoader gitLoader = loadFXML("tabs/git.fxml");
            gitTP.setContent(gitLoader.load());
            FXMLLoader backupLoader = loadFXML("tabs/backup.fxml");
            backupTP.setContent(backupLoader.load());

            submit = new Submit(
                    chooseDirPassLoader.getController(),
                    gitLoader.getController(),
                    backupLoader.getController()
            );
        } catch (IOException ioException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Some assets of the program is missing!");
            Platform.exit();
        }
    }

    private FXMLLoader loadFXML(String fxml) {
        return new FXMLLoader(App.class.getResource(fxml));
    }

    @FXML
    private void onSubmitBtn(ActionEvent event) {
        submit.submit();
    }
}
