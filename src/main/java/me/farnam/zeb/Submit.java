package me.farnam.zeb;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import me.farnam.zeb.backup.Backup;
import me.farnam.zeb.backup.BackupMethod;
import me.farnam.zeb.controller.BackupController;
import me.farnam.zeb.controller.ChooseDirPassController;
import me.farnam.zeb.controller.GitController;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class Submit {
    private File sourceDirectory;
    private File outputDirectory;
    private String password;
    private boolean hasGit;
    private String commitMessage;
    private BackupMethod backupMethod;
    private String backupFileName;


    private void loadDirPass() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("tabs/dir-pass-choose.fxml"));
        Parent root = fxmlLoader.load();
        ChooseDirPassController cdpController = fxmlLoader.getController();

        sourceDirectory = cdpController.getDirectory();
        password = cdpController.getPassword();
    }

    private void loadGit() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("tabs/git.fxml"));
        Parent root = fxmlLoader.load();
        GitController gitController = fxmlLoader.getController();

        hasGit = gitController.getHasGit();
        if (!hasGit) return;

        String cm = gitController.getCommitMessage();
        if (cm != null && !cm.isBlank()) { commitMessage = cm; }
    }

    private void loadBackup() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("tabs/backup.fxml"));
        Parent root = fxmlLoader.load();
        BackupController backupController = fxmlLoader.getController();

        backupMethod = backupController.getBackupMethod();

        if (backupMethod.equals(BackupMethod.Local)) {
            backupFileName = backupController.getLocalBackupFileName();
            outputDirectory = backupController.getLocalBackupPath();
        }
    }

    public void submit() {
        loadInformation();

        if (backupMethod.equals(BackupMethod.Local)) {
            try {
                Backup localBackup = getLocalBackup(sourceDirectory);
                localBackup.backup();
            } catch (IOException | GitAPIException ioException) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText(ioException.getMessage());
                errorAlert.show();
            } catch (IllegalArgumentException illegalArgumentException) {
                Alert errorAlert = new Alert(Alert.AlertType.WARNING);
                errorAlert.setContentText(illegalArgumentException.getMessage());
                errorAlert.show();
            } catch (Exception exception) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Unknown Error!");
                errorAlert.setContentText(exception.getMessage());
                errorAlert.show();
            }
        }
    }

    private void loadInformation() {
        try {
            loadDirPass();
            loadGit();
            loadBackup();
        } catch (IOException ioException) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText(ioException.getMessage());
            errorAlert.show();
        }
    }

    private Backup getLocalBackup(File sourceDirectory) throws IOException {
        Backup localBackup = new Backup(sourceDirectory);
        //Setting loaded variables.
        if (password != null) { localBackup.setPassword(password); }
        if (outputDirectory != null) { localBackup.setOutputDirectory(outputDirectory); }
        localBackup.setHasGit(hasGit);
        if (commitMessage != null) { localBackup.setCommitMessage(commitMessage); }
        if (backupFileName != null) { localBackup.setOutputFileName(backupFileName); }
        return localBackup;
    }
}
