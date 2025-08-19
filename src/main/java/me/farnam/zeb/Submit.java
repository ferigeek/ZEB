package me.farnam.zeb;

import javafx.scene.control.Alert;
import me.farnam.zeb.backup.Backup;
import me.farnam.zeb.backup.BackupMethod;
import me.farnam.zeb.controller.BackupController;
import me.farnam.zeb.controller.ChooseDirPassController;
import me.farnam.zeb.controller.GitController;
import me.farnam.zeb.controller.MainController;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class Submit {
    private final MainController mainController;
    private final ChooseDirPassController chooseDirPassController;
    private final GitController gitController;
    private final BackupController backupController;

    private File sourceDirectory;
    private File outputDirectory;
    private String password;
    private boolean hasGit;
    private String commitMessage;
    private BackupMethod backupMethod;
    private String backupFileName;

    public Submit(
            MainController mainController,
            ChooseDirPassController con1,
            GitController con2,
            BackupController con3) {
        this.mainController = mainController;
        this.chooseDirPassController = con1;
        this.gitController = con2;
        this.backupController = con3;
    }

    private void loadDirPass() throws IOException {
        sourceDirectory = chooseDirPassController.getDirectory();
        password = chooseDirPassController.getPassword();
    }

    private void loadGit() throws IOException {
        hasGit = gitController.getHasGit();
        if (!hasGit) return;

        String cm = gitController.getCommitMessage();
        if (cm != null && !cm.isBlank()) { commitMessage = cm; }
    }

    private void loadBackup() throws IOException {
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
                mainController.consoleLog("Creating local backup ...");
                localBackup.backup();
                mainController.consoleLog("Local backup was created successfully!");
                Alert completeAlert = new Alert(Alert.AlertType.INFORMATION);
                completeAlert.setContentText("Backup was generated successfully!");
                completeAlert.show();
            } catch (IOException | GitAPIException ioException) {
                mainController.consoleLog(ioException.getMessage());
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText(ioException.getMessage());
                errorAlert.show();
            } catch (IllegalArgumentException illegalArgumentException) {
                mainController.consoleLog(illegalArgumentException.getMessage());
                Alert errorAlert = new Alert(Alert.AlertType.WARNING);
                errorAlert.setContentText(illegalArgumentException.getMessage());
                errorAlert.show();
            } catch (Exception exception) {
                mainController.consoleLog(exception.getMessage());
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
            mainController.consoleLog("Loaded form information successfully!");
        } catch (IOException ioException) {
            mainController.consoleLog(ioException.getMessage());
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText(ioException.getMessage());
            errorAlert.show();
        }
    }

    private Backup getLocalBackup(File sourceDirectory) throws IOException {
        Backup localBackup = new Backup(sourceDirectory);
        //Setting loaded variables.
        if (password != null && !password.isBlank()) { localBackup.setPassword(password); }
        if (outputDirectory != null) { localBackup.setOutputDirectory(outputDirectory); }
        localBackup.setHasGit(hasGit);
        if (commitMessage != null && !commitMessage.isBlank()) { localBackup.setCommitMessage(commitMessage); }
        if (backupFileName != null) { localBackup.setOutputFileName(backupFileName); }
        mainController.consoleLog("Initialized local backup.");
        return localBackup;
    }
}
