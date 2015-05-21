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
import java.util.Arrays;
import java.util.List;


public class CommentProcessor {

    private final CommentRemover commentRemover;
    private final UserInputHandler userInputHandler;

    private final FileProcessor fileProcessor;
    private final List<String> supportedExtensions;

    public CommentProcessor(CommentRemover commentRemover) {
        this.commentRemover = commentRemover;
        this.userInputHandler = new UserInputHandler(commentRemover);
        this.fileProcessor = FileProcessor.getInstance();
        this.supportedExtensions = Arrays.asList("java", "js", "jsp", "html", "css", "xml", "properties");
    }

    public void start() {
        checkAllStates();
        displayProcessStarted();
        displayProcessProgressByDots();
        setFileProcessorCommentRemover();
        doProcess();
        displayProcessSuccessfullyDone();
    }

    private void displayProcessStarted() {
        System.out.println("PROCESS STARTED...It may take a while(based on your project size).Please be patient :)\n");
    }

    private void checkAllStates() {
        try {
            userInputHandler.checkAllStates();
        } catch (CommentRemoverException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void displayProcessProgressByDots() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                int starCount = 0;
                while (true) {

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

    private void setFileProcessorCommentRemover() {
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


                    String[] excludePackagePaths = commentRemover.getExcludePackages();
                    if (excludePackagePaths != null) {

                        if (isStartInternalPathSelected()) {
                            excludePackagePaths = CommentUtility.getExcludePackagesInValidFormForInternalStarting(excludePackagePaths);
                        } else {
                            excludePackagePaths = CommentUtility.getExcludePackagesInValidFormForExternalStarting(startingPath.toString(), excludePackagePaths);
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
        System.out.println("\n\nPROCESS SUCCESSFULLY DONE!");
//        System.exit(0);
    }
}
