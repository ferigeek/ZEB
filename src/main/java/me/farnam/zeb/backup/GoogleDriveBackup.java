package me.farnam.zeb.backup;

import java.io.File;
import java.io.IOException;

public class GoogleDriveBackup extends Backup {
    public GoogleDriveBackup(File backupDirectory) throws IOException {
        super(backupDirectory);
    }

    public GoogleDriveBackup(File backupDirectory, File backupOutputDirectory) throws IOException {
        super(backupDirectory, backupOutputDirectory);
    }

    public void sync() {

    }
}
