package com.commentremover.app;

import com.commentremover.exception.CommentRemoverException;
import com.commentremover.handling.UserInputHandler;
import com.commentremover.utility.CommentUtility;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;


public class CommentProcessor {

    private final CommentRemover commentRemover;
    private final UserInputHandler userInputHandler;

    private final FileProcessRouter fileProcessRouter;
    private final List<String> supportedExtensions;

    private volatile boolean isFinished = false;

    public CommentProcessor(CommentRemover commentRemover) {
        this.commentRemover = commentRemover;
        this.userInputHandler = new UserInputHandler(commentRemover);
        this.fileProcessRouter = new FileProcessRouter(commentRemover);
        this.supportedExtensions = Arrays.asList("java", "js", "jsp", "html", "css", "xml", "properties");
    }

    public void start() throws CommentRemoverException, StackOverflowError {
        checkAllStates();
        displayProcessStarted();
        displayProcessProgressByDots();
        doProcess();
        displayProcessSuccessfullyDone();
    }

    private void displayProcessStarted() {
        System.out.println("PROCESS STARTED...It may take a while(based on your project size).Please be patient :)\n");
    }

    private void checkAllStates() throws CommentRemoverException {
        userInputHandler.checkAllStates();
    }

    private void displayProcessProgressByDots() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                int starCount = 0;
                while (!isFinished) {

                    if (starCount >= 10) {
                        System.out.println("*\t");
                        starCount = 0;
                    } else {
                        System.out.print("*\t");
                        starCount++;
                    }

                    try {
                        Thread.sleep(350);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void doProcess() {

        final Path startingPath = Paths.get(getSelectedStartingPath());
        final String[] excludePackagesPaths = getExcludePackagesPathsInValidForm(commentRemover.getExcludePackages(), startingPath.toString());

        try {
            Files.walkFileTree(startingPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                    Path parentPath = dir.getParent();
                    if (parentPath == null) {
                        return FileVisitResult.CONTINUE;
                    }

                    if (excludePackagesPaths != null) {

                        for (String excludePackagePath : excludePackagesPaths) {

                            String currentDirectoryPath = dir.toString();
                            if (currentDirectoryPath.equals(excludePackagePath)) {
                                return FileVisitResult.SKIP_SUBTREE;
                            }
                        }
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException, StackOverflowError {

                    String fileName = file.getFileName().toString();
                    String fileExtension = CommentUtility.getExtension(fileName);
                    if (!isSupportedFileExtension(fileExtension)) {
                        return FileVisitResult.CONTINUE;
                    }

                    try {
                        String filePath = file.toString();
                        fileProcessRouter.setCurrentFilePath(filePath);
                        fileProcessRouter.removeComments();
                    } catch (CommentRemoverException e) {
                        System.err.println(e.getMessage());
                    } catch (StackOverflowError e) {
                        System.err.println("StackOverflowError:Please increase your stack size! VM option command is: -Xss40m if you need to increase more -Xss{size}m");
                        throw e;
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {

                    System.err.println(exc.getMessage());
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

    private String[] getExcludePackagesPathsInValidForm(String[] excludePackagesPaths, String startingPath) {

        if (excludePackagesPaths == null) {
            return null;
        }

        return isStartInternalPathSelected() ?
                CommentUtility.getExcludePackagesInValidFormForInternalStarting(excludePackagesPaths) :
                CommentUtility.getExcludePackagesInValidFormForExternalStarting(startingPath, excludePackagesPaths);
    }

    private String getSelectedStartingPath() {

        String startInternalPath = commentRemover.getStartInternalPath();
        String startExternalPath = commentRemover.getStartExternalPath();

        if (startInternalPath != null) {
            return CommentUtility.getStartInternalPathInValidForm(startInternalPath);
        } else {
            return CommentUtility.getStartExternalPathInValidForm(startExternalPath);
        }
    }

    private boolean isSupportedFileExtension(String fileExtension) {
        return supportedExtensions.contains(fileExtension);
    }

    private boolean isStartInternalPathSelected() {
        return commentRemover.getStartInternalPath() != null;
    }

    private void displayProcessSuccessfullyDone() {
        isFinished = true;
        System.out.println("\n\nPROCESS SUCCESSFULLY DONE!");
    }
}
