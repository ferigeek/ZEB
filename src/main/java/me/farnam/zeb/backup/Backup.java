package me.farnam.zeb.backup;

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
    private File srcDirectory;
    private File outputDirectory;
    private String password;
    private String outputFileName;
    private String commitMessage;
    private boolean hasGit;

    public Backup(File srcDirectory) throws IOException {
        if (srcDirectory.exists()) {
            this.srcDirectory = srcDirectory;
        } else {
            throw new IllegalArgumentException("Backup source directory doesn't exist!");
        }
    }

    public void setOutputDirectory(File outputDirectory) {
        if (outputDirectory.exists()) {
            this.outputDirectory = outputDirectory;
        } else {
            throw new IllegalArgumentException("Backup output directory doesn't exist!");
        }
    }

    public void setPassword(String password) {
        if (password != null && !password.isBlank()) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Enter a valid password!");
        }
    }

    public  void setCommitMessage(String message) {
        if (message != null && !message.isBlank()) {
            this.commitMessage = message;
        } else {
            throw new IllegalArgumentException("Enter a valid commit message!");
        }
    }

    public void setHasGit(boolean hasGit) {
        this.hasGit = hasGit;
    }

    /**
     * File name for the backup output without file extension.
     * @param fileName
     */
    public void setOutputFileName(String fileName) {
        if (fileName != null && !fileName.isBlank()) {
            this.outputFileName = fileName;
        } else {
            throw new IllegalArgumentException("Enter a valid file name!");
        }
    }

    /**
     * Before calling this method, make sure you have correct values:
     * <p>
     *     If there is a git repository in source directory, set <code>hastGit</code> to true.
     *     <br>
     *     Setting commit message is optional.
     * </p>
     * <p>
     *     You can set backup output directory and output file name.
     * </p>
     * @throws IOException
     * @throws GitAPIException
     */
    public void backup() throws IOException, GitAPIException {
        commitChanges();
        compress();
    }

    private void compress() throws IOException, GitAPIException {
        String fileName;
        if (outputFileName != null) {
            fileName = outputFileName;
        } else {
            fileName = "ZEB-Backup";
        }

        if (outputDirectory == null) {
            String appDir = System.getProperty("user.dir");
            File appCacheDir = new File(appDir + File.separator + ".zeb");
            if (!appCacheDir.exists()) {
                boolean created = appCacheDir.mkdir();
                if (!created) {
                    throw new IOException("Failed to create application cache directory!");
                }
            }
            outputDirectory = appCacheDir;
        }

        if (password != null) {
            try (ZipFile backup = new ZipFile(
                    outputDirectory + File.separator + String.format("%s.zip", fileName),
                    password.toCharArray())) {
                backup.addFolder(srcDirectory);
            }
        } else {
            try (ZipFile backup = new ZipFile(
                    outputDirectory + File.separator + String.format("%s.zip", fileName))) {
                backup.addFolder(srcDirectory);
            }
        }

    }

    private void commitChanges() throws IOException, GitAPIException {
        if (!hasGit) return;

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try (Repository gitRepo = builder.setGitDir(srcDirectory)
                .readEnvironment()
                .findGitDir()
                .build()) {
            Git git = new Git(gitRepo);
            CommitCommand commit = git.commit();
            if (commitMessage != null){
                commit.setMessage(commitMessage).call();
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String now = LocalDateTime.now().format(formatter);
                commit.setMessage(now);
            }
        }
    }
}
