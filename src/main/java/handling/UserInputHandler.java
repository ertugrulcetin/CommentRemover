package handling;

import app.CommentRemover;
import exception.CommentRemoverException;
import utility.CommentUtility;

import java.io.File;

public class UserInputHandler {

    private final CommentRemover commentRemover;

    public UserInputHandler(CommentRemover commentRemover) {
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
            throw new CommentRemoverException("Please select at least ONE file type to remove comments.(Java,HTML,Properties etc.)");
        }
    }

    private void checkAnyCommentTypeAssigned(CommentRemover commentRemover) throws CommentRemoverException {

        boolean isAnyCommentTypeAssigned = commentRemover.isRemoveSingleLines() || commentRemover.isRemoveMultiLines();

        if (!isAnyCommentTypeAssigned) {
            throw new CommentRemoverException("Please select at least ONE comment type!(singleLine or/and multipleLine)");
        }
    }

    private void checkStartPathType(CommentRemover commentRemover) throws CommentRemoverException {

        String startPath = commentRemover.getStartPath();
        String startExternalPath = commentRemover.getStartExternalPath();

        if (!isSelectedOneTypeStartingPath(startPath, startExternalPath)) {
            throw new CommentRemoverException("Please select ONLY ONE start path type (startPath or excludeStartPath)!");
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

        String startingDirectoryPath = CommentUtility.getStartPathInValidForm(commentRemover.getStartPath());
        File file = new File(startingDirectoryPath);

        if (!(file.exists() && file.isDirectory())) {
            throw new CommentRemoverException("Please specify valid directory path! " + file.getAbsolutePath() + " is not a valid directory.");
        }
    }

    private void checkExternalStartPath(CommentRemover commentRemover) throws CommentRemoverException {

        String startingExternalDirectoryPath = CommentUtility.getStartExternalPath(commentRemover.getStartExternalPath());
        File file = new File(startingExternalDirectoryPath);

        if (!(file.exists() && file.isDirectory())) {
            throw new CommentRemoverException("Please specify valid directory path! " + file.getAbsolutePath() + " is not a valid directory.");
        }
    }

    private void checkExcludePackagesPaths(CommentRemover commentRemover) throws CommentRemoverException {

        String[] excludePackagesPaths = commentRemover.getExcludePackagesPaths();
        if (excludePackagesPaths == null) {
            return;
        } else {
            if (commentRemover.getStartPath() != null) {
                excludePackagesPaths = CommentUtility.getExcludePackagesPathsInValidForm(excludePackagesPaths);
            } else {
                excludePackagesPaths = CommentUtility.getExcludePackagesPathsInValidFormForExternalPath(commentRemover.getStartExternalPath(), excludePackagesPaths);
            }
        }

        for (String path : excludePackagesPaths) {
            File file = new File(path);
            if (!(file.exists() && file.isDirectory())) {
                throw new CommentRemoverException(file.getAbsolutePath() + " is not valid directory path for exclude packages!");
            }
        }
    }
}
