package me.farnam.zeb;

import net.lingala.zip4j.ZipFile;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Backup {
    private File backupDirectory;
    private File backupOutputDirectory;
    private String password;
    private boolean hasGit;
    private String commitMessage;

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

    public  void setCommitMessage(String message) { this.commitMessage = message; }

    public void setHasGit(boolean tf) {
        this.hasGit = tf;
    }

    public void compress() throws IOException, GitAPIException {
        if (hasGit) {
            commitChanges();
        }

        if (password != null && !password.isEmpty() && !password.isBlank()) {
            try (ZipFile backup = new ZipFile(
                    backupOutputDirectory + File.separator + "ZEB-Backup.zip",
                    password.toCharArray())) {
                backup.addFolder(backupDirectory);
            }
        } else {
            try (ZipFile backup = new ZipFile(backupOutputDirectory + File.separator + "ZEB-Backup.zip")) {
                backup.addFolder(backupDirectory);
            }
        }

    }

    /**
     * If there is no git repository(hasGit is set to <code>false</code>), it will do nothing.
     * Use <code>setHasGit</code> to <code>true</code> if the directory has git repository.
     * Finds the git repository in the directory and commits with the given message.
     * @throws IOException
     * @throws GitAPIException
     */
    private void commitChanges() throws IOException, GitAPIException {
        if (!hasGit) return;

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository gitRepo = builder.setGitDir(backupDirectory)
                .readEnvironment()
                .findGitDir()
                .build();

        Git git = new Git(gitRepo);
        CommitCommand commit = git.commit();
        if (commitMessage != null && !commitMessage.isEmpty() && !commitMessage.isBlank()){
            commit.setMessage(commitMessage).call();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now = LocalDateTime.now().format(formatter);
            commit.setMessage(now);
        }
    }
}
