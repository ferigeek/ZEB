package me.farnam.zeb;

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

    public Submit(ChooseDirPassController con1, GitController con2, BackupController con3) {
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
                localBackup.backup();
                Alert completeAlert = new Alert(Alert.AlertType.INFORMATION);
                completeAlert.setContentText("Backup was generated successfully!");
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
