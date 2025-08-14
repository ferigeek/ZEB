package me.farnam.zeb.backup;

import java.io.File;
import java.io.IOException;

public class GoogleDriveBackup extends Backup {
    public GoogleDriveBackup(File backupDirectory) throws IOException {
        super(backupDirectory);
    }

    public void sync() {

    }
}
