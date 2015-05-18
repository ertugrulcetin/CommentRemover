package handling;

import app.CommentRemover;
import exception.CommentRemoverException;
import utility.CommentUtility;

import java.io.File;

public class CommentHandler {

    private final CommentRemover commentRemover;

    public CommentHandler(CommentRemover commentRemover) {
        this.commentRemover = commentRemover;
    }

    public void checkAllStates() throws CommentRemoverException {
        checkAtLeastOneFileTypeAssigned(commentRemover);
        checkAnyCommentTypeAssigned(commentRemover);
        checkStartPathType(commentRemover);
        checkExcludePackagesPaths(commentRemover);
    }

    private void checkAtLeastOneFileTypeAssigned(CommentRemover commentRemover) throws CommentRemoverException {

        boolean isAtLeastOneFileTypeEntered = commentRemover.isRemoveCSS() ||
                commentRemover.isRemoveJSP() ||
                commentRemover.isRemoveXML() ||
                commentRemover.isRemoveHTML() ||
                commentRemover.isRemoveJava() ||
                commentRemover.isRemoveJavaScript() ||
                commentRemover.isRemoveProperties();

        if (!isAtLeastOneFileTypeEntered) {
            throw new CommentRemoverException("Please select at least One file type to remove comments");
        }
    }

    private void checkAnyCommentTypeAssigned(CommentRemover commentRemover) throws CommentRemoverException {

        boolean isAnyCommentTypeAssigned = commentRemover.isRemoveSingleLines() || commentRemover.isRemoveMultiLines();

        if (!isAnyCommentTypeAssigned) {
            throw new CommentRemoverException("Please select at least one comment type!");
        }
    }

    private void checkStartPathType(CommentRemover commentRemover) throws CommentRemoverException {

        String startPath = commentRemover.getStartPath();
        String startExternalPath = commentRemover.getStartExternalPath();

        if (!isSelectedOneTypeStartingPath(startPath, startExternalPath)) {
            throw new CommentRemoverException("Please select one start path type (startPath or excludeStartPath)!");

        }

        if (startPath != null) {
            checkStartPath(commentRemover);
        } else {
            checkExternalStartPath(commentRemover);
        }
    }

    private boolean isSelectedOneTypeStartingPath(String startPath, String startExternalPath) {
        return startPath != null ^ startExternalPath != null;
    }

    private void checkStartPath(CommentRemover commentRemover) throws CommentRemoverException {

        String startingDirectoryPath = CommentUtility.getPath(commentRemover.getStartPath());
        File file = new File(startingDirectoryPath);

        if (!(file.exists() && file.isDirectory())) {
            throw new CommentRemoverException("Please specify valid directory path! " + file.getAbsolutePath() + " is not a valid.");
        }
    }

    private void checkExternalStartPath(CommentRemover commentRemover) throws CommentRemoverException {

        String startingExternalDirectoryPath = CommentUtility.getExternalPath(commentRemover.getStartExternalPath());
        File file = new File(startingExternalDirectoryPath);

        if (!(file.exists() && file.isDirectory())) {
            throw new CommentRemoverException("Please specify valid directory path! " + file.getAbsolutePath() + " is not a valid.");
        }
    }

    private void checkExcludePackagesPaths(CommentRemover commentRemover) throws CommentRemoverException {

        String[] excludePackagesPaths = commentRemover.getExcludePackagesPaths();
        if (excludePackagesPaths == null) {
            return;
        }

        for (String path : excludePackagesPaths) {
            File file = new File(path);
            if (!(file.exists() && file.isDirectory())) {
                throw new CommentRemoverException(file.getAbsolutePath() + " is not valid directory path for exclude packages!");
            }
        }
    }
}