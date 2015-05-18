package handling;

import app.RegexPatterns;

public class RegexSelector {

    public static String getRegexByFileType(String fileType, boolean isSingleLinesEnabled, boolean isMultiLinesEnabled) {

        switch (fileType) {

            case "js":
            case "java":
                return regexJavaAndJavaScript(isSingleLinesEnabled, isMultiLinesEnabled);

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

    private static String regexJavaAndJavaScript(boolean isSingleLinesEnabled, boolean isMultiLinesEnabled) {
        return (isSingleLinesEnabled && isMultiLinesEnabled) ? RegexPatterns.getCommentRegexMultiAndSingleLine() :
                (isSingleLinesEnabled) ? RegexPatterns.getCommentRegexSingleLine() : RegexPatterns.getCommentRegexMultiLine();
    }
}