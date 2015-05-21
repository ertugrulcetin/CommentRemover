package app;

import exception.CommentRemoverException;
import handling.RegexSelector;
import utility.CommentUtility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class FileProcessor {

    private static final FileProcessor FILE_PROCESSOR = new FileProcessor();

    private CommentRemover commentRemover;
    private String currentFilePath;

    private static final Map<String, String> mappingEmptySingleLineCommentToEscapingComment;
    private static final Map<String, String> mappingFileTypeToSingleComment;
    private static final List<String> singleLineSupportedFileTypes;

    static {
        mappingEmptySingleLineCommentToEscapingComment = new HashMap<>();
        mappingEmptySingleLineCommentToEscapingComment.put("//", "//!-COMMENT_REMOVER_SINGLE_COMMENT_ESCAPE-!");
        mappingEmptySingleLineCommentToEscapingComment.put("#", "#!-COMMENT_REMOVER_SINGLE_COMMENT_ESCAPE-!");

        mappingFileTypeToSingleComment = new HashMap<>();
        mappingFileTypeToSingleComment.put("js", "//");
        mappingFileTypeToSingleComment.put("java", "//");
        mappingFileTypeToSingleComment.put("properties", "#");

        singleLineSupportedFileTypes = new LinkedList<>();
        singleLineSupportedFileTypes.add("js");
        singleLineSupportedFileTypes.add("java");
        singleLineSupportedFileTypes.add("properties");
    }

    private FileProcessor() {
    }

    protected static FileProcessor getInstance() {
        return FILE_PROCESSOR;
    }

    protected void setCommentRemover(CommentRemover commentRemover) {
        this.commentRemover = commentRemover;
    }

    protected void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    protected void removeComments() throws IOException, CommentRemoverException {

        String fileExtension = CommentUtility.getExtension(currentFilePath);

        switch (fileExtension) {

            case "js":
                if (commentRemover.isRemoveJavaScript()) {
                    doJavaScriptOperation();
                }
                break;

            case "java":
                if (commentRemover.isRemoveJava()) {
                    doJavaOperation();
                }
                break;

            case "jsp":
                if (commentRemover.isRemoveJSP()) {
                    doJspOperation();
                }
                break;

            case "css":
                if (commentRemover.isRemoveCSS()) {
                    doCssOperation();
                }
                break;

            case "properties":
                if (commentRemover.isRemoveProperties()) {
                    doPropertiesOperation();
                }
                break;

            case "html":
                if (commentRemover.isRemoveHTML()) {
                    doHtmlOperation();
                }
                break;

            case "xml":
                if (commentRemover.isRemoveXML()) {
                    doXmlOperation();
                }
                break;
        }
    }

    private void doJavaScriptOperation() throws IOException, CommentRemoverException {

        String regex = RegexSelector.getRegexByFileType("js", commentRemover.isRemoveSingleLines(), commentRemover.isRemoveMultiLines());
        replaceCommentsWithABlank(regex);
    }

    private void doJavaOperation() throws IOException, CommentRemoverException {

        String regex = RegexSelector.getRegexByFileType("java", commentRemover.isRemoveSingleLines(), commentRemover.isRemoveMultiLines());
        replaceCommentsWithABlank(regex);
    }

    private void doJspOperation() throws IOException, CommentRemoverException {

        String regex = RegexSelector.getRegexByFileType("jsp");
        replaceCommentsWithABlank(regex);
    }

    private void doCssOperation() throws IOException, CommentRemoverException {

        String regex = RegexSelector.getRegexByFileType("css");
        replaceCommentsWithABlank(regex);
    }

    private void doPropertiesOperation() throws IOException, CommentRemoverException {

        String regex = RegexSelector.getRegexByFileType("properties");
        replaceCommentsWithABlank(regex);
    }

    private void doHtmlOperation() throws IOException, CommentRemoverException {

        String regex = RegexSelector.getRegexByFileType("html");
        replaceCommentsWithABlank(regex);
    }

    private void doXmlOperation() throws IOException, CommentRemoverException {

        String regex = RegexSelector.getRegexByFileType("xml");
        replaceCommentsWithABlank(regex);
    }

    private void replaceCommentsWithABlank(String regex) throws IOException, CommentRemoverException {

        File file = new File(currentFilePath);
        String fileType = CommentUtility.getExtension(file.getName());
        StringBuilder fileContent = getFileContentByFileType(file, fileType);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileContent);

        StringBuilder newContent = doRemoveOperation(fileContent, matcher, fileType);

        setFileContent(file, newContent.toString());
    }

    private StringBuilder getFileContentByFileType(File file, String fileType) throws IOException, CommentRemoverException {

        long fileSize = file.length();

        if (fileSize > Integer.MAX_VALUE) {
            throw new CommentRemoverException("File size so big for scanning !");
        }

        StringBuilder content = new StringBuilder((int) fileSize);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

        if (isSingleLineSupportedFileType(fileType)) {
            getSupportingSingleLineContent(br, content, fileType);
        } else {
            getNonSupportingSingleLineContent(br, content);
        }

        return content;
    }

    private void getSupportingSingleLineContent(BufferedReader br, StringBuilder content, String fileType) throws IOException {

        String commentSymbol = getCommentSymbolByFileType(fileType);
        String commentEscaped = getCommentEscapedByCommentSymbol(commentSymbol);

        String temp;
        while ((temp = br.readLine()) != null) {

            if (temp.trim().equals(commentSymbol)) {
                content.append(commentEscaped).append("\n");
            } else {
                content.append(temp).append("\n");
            }
        }
        br.close();
    }

    private String getCommentSymbolByFileType(String fileType) {
        return mappingFileTypeToSingleComment.get(fileType);
    }

    private String getCommentEscapedByCommentSymbol(String commentSymbol) {
        return mappingEmptySingleLineCommentToEscapingComment.get(commentSymbol);
    }

    private void getNonSupportingSingleLineContent(BufferedReader br, StringBuilder content) throws IOException {

        String temp;
        while ((temp = br.readLine()) != null) {
            content.append(temp).append("\n");
        }
        br.close();
    }

    private StringBuilder doRemoveOperation(StringBuilder fileContent, Matcher matcher, String fileType) {

        try {
            String sFileContent = fileContent.toString();
            boolean isTodosRemoving = commentRemover.isRemoveTodos();

            while (matcher.find()) {

                String foundToken = matcher.group();
                if (isTodosRemoving) {
                    if (!isDoubleQuoteToken(foundToken)) {
                        sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
                    }
                } else {
                    if (!isDoubleQuoteToken(foundToken) && !isContainTodo(foundToken)) {
                        sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
                    }
                }
            }

            if (isTodosRemoving && isSingleLineSupportedFileType(fileType)) {
                String commentSymbol = getCommentSymbolByFileType(fileType);
                String commentEscaped = getCommentEscapedByCommentSymbol(commentSymbol);
                sFileContent = sFileContent.replace(commentEscaped, "");
            }

            fileContent = new StringBuilder(sFileContent);

        } catch (StackOverflowError e) {
            System.err.println("StackOverflowError:Please increase your stack size! VM option command is: -Xss50m if you need to increase more -Xss{size}m");
            System.exit(0);
        }

        return fileContent;
    }

    private boolean isSingleLineSupportedFileType(String fileType) {
        return singleLineSupportedFileTypes.contains(fileType);
    }

    private boolean isDoubleQuoteToken(String foundToken) {
        return foundToken.startsWith("\"");
    }

    private boolean isContainTodo(String foundToken) {
        return Pattern.compile(Pattern.quote("todo"), Pattern.CASE_INSENSITIVE).matcher(foundToken).find();
    }

    private void setFileContent(File file, String newContent) throws IOException {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        bw.write(newContent);
        bw.flush();
        bw.close();
    }
}
