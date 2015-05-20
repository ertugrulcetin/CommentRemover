package app;

import exception.CommentRemoverException;
import handling.UserInputHandler;
import utility.CommentUtility;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;


public class CommentProcessor {

    private final CommentRemover commentRemover;
    private final UserInputHandler userInputHandler;

    private final FileProcessor fileProcessor = FileProcessor.getInstance();
    private final List<String> supportedExtensions = CommentUtility.getSupportedExtensions();

    public CommentProcessor(CommentRemover commentRemover) {
        this.commentRemover = commentRemover;
        this.userInputHandler = new UserInputHandler(commentRemover);
    }

    public void start() {
        checkAllStates();
        setFileProcessorContext();
        doProcess();
        displayProcessSuccessfullyDone();
    }

    private void checkAllStates() {
        try {
            userInputHandler.checkAllStates();
        } catch (CommentRemoverException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void setFileProcessorContext() {
        fileProcessor.setCommentRemover(commentRemover);
    }

    private void doProcess() {

        final Path startingPath = Paths.get(getSelectedStartingPath());
        try {
            Files.walkFileTree(startingPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                    Path parentPath = dir.getParent();
                    if (parentPath == null) {
                        return FileVisitResult.CONTINUE;
                    }


                    String[] excludePackagePaths = commentRemover.getExcludePackagesPaths();
                    if (excludePackagePaths != null) {

                        if (isStartPathSelected()) {
                            excludePackagePaths = CommentUtility.getExcludePackagesPathsInValidForm(excludePackagePaths);
                        } else {
                            excludePackagePaths = CommentUtility.getExcludePackagesPathsInValidFormForExternalPath(startingPath.toString(), excludePackagePaths);
                        }

                        for (String excludePackagePath : excludePackagePaths) {

                            String currentDirectoryPath = dir.toString();
                            if (currentDirectoryPath.equals(excludePackagePath)) {
                                return FileVisitResult.SKIP_SUBTREE;
                            }
                        }
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    String fileName = file.getFileName().toString();
                    String fileExtension = CommentUtility.getExtension(fileName);
                    if (!isSupportedFileExtension(fileExtension)) {
                        return FileVisitResult.CONTINUE;
                    }

                    try {
                        String filePath = file.toString();
                        fileProcessor.setCurrentFilePath(filePath);
                        fileProcessor.removeComments();
                    } catch (CommentRemoverException e) {
                        e.printStackTrace();
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {

                    exc.printStackTrace();

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return super.postVisitDirectory(dir, exc);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSelectedStartingPath() {

        String startPath = commentRemover.getStartPath();
        String startExternalPath = commentRemover.getStartExternalPath();

        if (startPath != null) {
            return CommentUtility.getStartPathInValidForm(startPath);
        } else {
            return CommentUtility.getStartExternalPath(startExternalPath);
        }
    }

    private boolean isSupportedFileExtension(String fileExtension) {
        return supportedExtensions.contains(fileExtension);
    }

    private boolean isStartPathSelected() {
        return commentRemover.getStartPath() != null;
    }

    private void displayProcessSuccessfullyDone() {
        System.out.println("PROCESS SUCCESSFULLY DONE!");
    }
}