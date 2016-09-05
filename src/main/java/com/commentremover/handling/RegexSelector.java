package com.commentremover.handling;

import com.commentremover.pattern.RegexPatterns;

public class RegexSelector {

    public static String getRegexByFileType(String fileType) {

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
