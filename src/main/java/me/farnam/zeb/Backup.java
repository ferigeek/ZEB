package me.farnam.zeb;

import java.io.File;
import java.io.IOException;

public class Backup {
    private File backupDirectory;
    private File backupOutputDirectory;
    private String password;
    private boolean hasGit;

    public Backup(File backupDirectory) throws IOException {
        String appDir = System.getProperty("user.dir");
        File appCacheDir = new File(appDir + File.separator + ".zeb");
        if (!appCacheDir.exists()) {
            boolean created = appCacheDir.mkdir();
            if (!created) {
                throw new IOException("Failed to create application cache directory!");
            }
        }
        this.backupOutputDirectory = appCacheDir;
    }

    public Backup(File backupDirectory, File backupOutputDirectory) throws IOException {
        this(backupDirectory);

        if (backupDirectory.exists()) {
            this.backupOutputDirectory = backupOutputDirectory;
        } else {
            throw new IllegalArgumentException("Backup output directory doesn't exist!");
        }
    }

    public Backup(File backupDirectory, String password) throws IOException{
        this(backupDirectory);
        this.password = password;
    }

    public Backup(File backupDirectory, File backupOutputDirectory, String password) throws IOException {
        this(backupDirectory, backupOutputDirectory);
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHasGit(boolean tf) {
        this.hasGit = tf;
    }

    public void compress() {

    }

    private void commitChanges() {

    }
}
