package com.commentremover.handling;

import com.commentremover.app.CommentRemover;
import com.commentremover.exception.CommentRemoverException;
import com.commentremover.utility.CommentUtility;

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

        String startInternalPath = commentRemover.getStartInternalPath();
        String startExternalPath = commentRemover.getStartExternalPath();

        if (!isSelectedOneTypeStartingPath(startInternalPath, startExternalPath)) {
            throw new CommentRemoverException("Please select ONLY ONE starting path type (startInternalPath or startExternalPath)!");
        }

        if (startInternalPath != null) {
            checkInternalStartPath(commentRemover);
        } else {
            checkExternalStartPath(commentRemover);
        }
    }

    private boolean isSelectedOneTypeStartingPath(String startPath, String startExternalPath) {
        return startPath != null ^ startExternalPath != null;
    }

    private void checkInternalStartPath(CommentRemover commentRemover) throws CommentRemoverException {

        String startingInternalPath = CommentUtility.getStartInternalPathInValidForm(commentRemover.getStartInternalPath());
        File file = new File(startingInternalPath);

        if (!(file.exists() && file.isDirectory())) {
            throw new CommentRemoverException("Please specify valid directory path! " + file.getAbsolutePath() + " is not a valid directory.");
        }
    }

    private void checkExternalStartPath(CommentRemover commentRemover) throws CommentRemoverException {

        String startingExternalPath = CommentUtility.getStartExternalPathInValidForm(commentRemover.getStartExternalPath());
        File file = new File(startingExternalPath);


        if (!(file.exists() && (file.isDirectory() || file.isFile()))) {
            throw new CommentRemoverException("Please specify valid directory or file path! " + file.getAbsolutePath() + " is not a valid directory or file for external path.");
        }
    }

    private void checkExcludePackagesPaths(CommentRemover commentRemover) throws CommentRemoverException {

        String[] excludePackagesPaths = commentRemover.getExcludePackages();
        if (excludePackagesPaths == null) {
            return;
        } else {
            if (commentRemover.getStartInternalPath() != null) {
                excludePackagesPaths = CommentUtility.getExcludePackagesInValidFormForInternalStarting(excludePackagesPaths);
            } else {
                excludePackagesPaths = CommentUtility.getExcludePackagesInValidFormForExternalStarting(commentRemover.getStartExternalPath(), excludePackagesPaths);
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
