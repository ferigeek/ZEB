package me.farnam.zeb;

import javafx.fxml.FXMLLoader;
import me.farnam.zeb.backup.BackupMethod;
import me.farnam.zeb.controller.BackupController;
import me.farnam.zeb.controller.ChooseDirPassController;
import me.farnam.zeb.controller.GitController;

import java.io.File;

public class Submit {
    private File directory;
    private File outputDirectory;
    private String password;
    private boolean hasGit;
    private String commitMessage;
    private BackupMethod backupMethod;
    private String backupFileName;


    private void loadDirPass() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("tabs/dir-pass-choose.fxml"));
        ChooseDirPassController cdpController = fxmlLoader.getController();

        directory = cdpController.getDirectory();
        password = cdpController.getPassword();
    }

    private void loadGit() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("tabs/git.fxml"));
        GitController gitController = fxmlLoader.getController();

        hasGit = gitController.getHasGit();
        if (!hasGit) return;

        String cm = gitController.getCommitMessage();
        if (cm != null && !cm.isBlank()) { commitMessage = cm; }
    }

    private void loadBackup() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("tabs/backup.fxml"));
        BackupController backupController = fxmlLoader.getController();

        backupMethod = backupController.getBackupMethod();

        if (backupMethod.equals(BackupMethod.Local)) {
            backupFileName = backupController.getLocalBackupFileName();
            outputDirectory = backupController.getLocalBackupPath();
        }
    }

    public void submit() {

    }
}
