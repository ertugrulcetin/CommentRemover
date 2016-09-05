package com.commentremover.pattern;

public class RegexPatterns {

    private static final String COMMENT_REGEX_MULTI_AND_SINGLE_LINE = "(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/|[\\t]*//.*)|\"(\\\\.|[^\\\\\"])*\"|'(\\\\[\\s\\S]|[^'])*'";

    private static final String COMMENT_REGEX_CSS = "(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|\"(\\\\.|[^\\\\\"])*\"|'(\\\\[\\s\\S]|[^'])*'";

    private static final String COMMENT_REGEX_PROPERTIES = "([\\t]*#.*)|(=.*)";

    private static final String COMMENT_REGEX_HTML_XML = "<!--(?!\\s*(?:\\[if [^\\]]+]|<!|>))(?:(?!-->)(.|\\n))*-->";

    private static final String COMMENT_REGEX_JSP = "<%--(?!\\s*(?:\\[if [^\\]]+]|<!|>))(?:(?!-->)(.|\\n))*--%>";

    public static String getCommentRegexMultiAndSingleLine() {
        return COMMENT_REGEX_MULTI_AND_SINGLE_LINE;
    }

    public static String getCommentRegexCss() {
        return COMMENT_REGEX_CSS;
    }

    public static String getCommentRegexProperties() {
        return COMMENT_REGEX_PROPERTIES;
    }

    public static String getCommentRegexHtmlXml() {
        return COMMENT_REGEX_HTML_XML;
    }

    public static String getCommentRegexJsp() {
        return COMMENT_REGEX_JSP;
    }
}
