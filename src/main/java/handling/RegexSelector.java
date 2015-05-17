package handling;

import app.RegexPatterns;
import exception.CommentRemoverException;
import exception.Error;

public class RegexSelector {

    public static String getRegexByFileType(String fileType) throws CommentRemoverException {
        return getRegexByFileType(fileType, false, false);
    }

    public static String getRegexByFileType(String fileType, boolean isSingleLinesEnabled, boolean isMultiLinesEnabled) throws CommentRemoverException {

        switch (fileType) {

            case "js":
            case "java":

                displayErrorIfAnyLineTypeNotEnabled(isSingleLinesEnabled, isMultiLinesEnabled, Error.ERROR_NO_LINE_SELECTED);
                return regex(isSingleLinesEnabled, isMultiLinesEnabled);

            case "properties":
                return RegexPatterns.getCommentRegexProperties();

            case "css":
                return RegexPatterns.getCommentRegexMultiLine();

            case "jsp":
                return RegexPatterns.getCommentRegexJsp();

            case "xml":
            case "html":
                return RegexPatterns.getCommentRegexHtmlXml();
        }

        return null;
    }

    private static void displayErrorIfAnyLineTypeNotEnabled(boolean isSingleLinesEnabled, boolean isMultiLinesEnabled, String error) throws CommentRemoverException {
        if (!isSingleLinesEnabled && !isMultiLinesEnabled) {
            throw new CommentRemoverException(error);
        }
    }

    private static String regex(boolean isSingleLinesEnabled, boolean isMultiLinesEnabled) {
        return (isSingleLinesEnabled && isMultiLinesEnabled) ? RegexPatterns.getCommentRegexMultiAndSingleLine() :
                (isSingleLinesEnabled) ? RegexPatterns.getCommentRegexSingleLine() : RegexPatterns.getCommentRegexMultiLine();
    }
}