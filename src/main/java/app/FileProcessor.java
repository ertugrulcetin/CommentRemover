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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileProcessor {

    private static final FileProcessor FILE_PROCESSOR = new FileProcessor();

    private CommentRemover commentRemover;
    private String currentFilePath;

    private FileProcessor() {
    }

    public static FileProcessor getInstance() {
        return FILE_PROCESSOR;
    }

    public void setCommentRemover(CommentRemover commentRemover) {
        this.commentRemover = commentRemover;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    public void removeComments() throws IOException, CommentRemoverException {

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

        StringBuilder fileContent = getFileContent(file);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileContent);

        StringBuilder newContent;
        if (commentRemover.isRemoveTodos()) {
            newContent = doRemoveCommentsWithTodos(matcher);
        } else {
            newContent = doNotRemoveCommentsWithTodos(fileContent, matcher);
        }

        setFileContent(file, newContent.toString());
    }

    private StringBuilder getFileContent(File file) throws IOException, CommentRemoverException {

        long fileSize = file.length();

        if (fileSize > Integer.MAX_VALUE) {
            throw new CommentRemoverException("File size so big for scanning !");
        }

        String temp;
        StringBuilder content = new StringBuilder((int) fileSize);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        while ((temp = br.readLine()) != null) {
            content.append(temp).append("\n");
        }
        br.close();

        return content;
    }

    private StringBuilder doRemoveCommentsWithTodos(Matcher matcher) {

        StringBuilder newContent = null;
        try {
            newContent = new StringBuilder(matcher.replaceAll(""));
        } catch (StackOverflowError e) {
            System.err.println("StackOverflowError:Please increase your stack size! VM option command is: -Xss16m if you need to increase more -Xss{size}m");
            System.exit(0);
        }

        return newContent;
    }

    private StringBuilder doNotRemoveCommentsWithTodos(StringBuilder fileContent, Matcher matcher) {

        StringBuilder newContent = null;
        try {
            String sFileContent = fileContent.toString();
            while (matcher.find()) {
                String foundToken = matcher.group();
                if (!isContainTodo(foundToken)) {
                    sFileContent = sFileContent.replace(foundToken, "");
                }
            }
            newContent = new StringBuilder(sFileContent);

        } catch (StackOverflowError e) {
            System.err.println("StackOverflowError:Please increase your stack size! VM option command is: -Xss16m if you need to increase more -Xss{size}m");
            System.exit(0);
        }

        return newContent;
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
