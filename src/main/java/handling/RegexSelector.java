package handling;

import app.RegexPatterns;

public class RegexSelector {

    public static String getRegexByFileType(String fileType) {
        return getRegexByFileType(fileType, false, false);
    }

    public static String getRegexByFileType(String fileType, boolean isSingleLinesEnabled, boolean isMultiLinesEnabled) {

        switch (fileType) {

            case "js":
            case "java":
                return RegexPatterns.getCommentRegexMultiAndSingleLine();

            case "properties":
                return RegexPatterns.getCommentRegexProperties();

            case "css":
                return RegexPatterns.getCommentRegexCss();

            case "jsp":
                return RegexPatterns.getCommentRegexJsp();

            case "xml":
            case "html":
                return RegexPatterns.getCommentRegexHtmlXml();
        }

        return null;
    }
}
