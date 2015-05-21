package app;

public class RegexPatterns {

    private static final String COMMENT_REGEX_MULTI_AND_SINGLE_LINE = "(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/|[ \\t]*//.*)|\"(\\\\.|[^\\\\\"])*\"";

    private static final String COMMENT_REGEX_MULTI_LINE = "/\\*([^*]|[\\r\\n]|(\\*+([^*?/]|[\\r\\n])))*\\*+/";

    private static final String COMMENT_REGEX_SINGLE_LINE = "([\\t]*//.*)|\"(\\\\.|[^\\\\\"])*\"";

    private static final String COMMENT_REGEX_PROPERTIES = "([\\t]*#.*)";

    private static final String COMMENT_REGEX_HTML_XML = "<!--(?!\\s*(?:\\[if [^\\]]+]|<!|>))(?:(?!-->)(.|\\n))*-->";

    private static final String COMMENT_REGEX_JSP = "<%--(?!\\s*(?:\\[if [^\\]]+]|<!|>))(?:(?!-->)(.|\\n))*-->";

    public static String getCommentRegexMultiAndSingleLine() {
        return COMMENT_REGEX_MULTI_AND_SINGLE_LINE;
    }

    public static String getCommentRegexMultiLine() {
        return COMMENT_REGEX_MULTI_LINE;
    }

    public static String getCommentRegexSingleLine() {
        return COMMENT_REGEX_SINGLE_LINE;
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
