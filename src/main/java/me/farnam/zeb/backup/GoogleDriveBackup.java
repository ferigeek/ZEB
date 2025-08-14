package me.farnam.zeb.backup;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class GoogleDriveBackup extends Backup {
    public GoogleDriveBackup(File backupDirectory) throws IOException {
        super(backupDirectory);
    }

    private void sync() {

    }

    @Override
    public void backup() throws GitAPIException, IOException {
        super.backup();
        sync();
    }
}
