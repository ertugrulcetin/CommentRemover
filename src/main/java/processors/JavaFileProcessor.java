package processors;

import app.CommentRemover;
import exception.CommentRemoverException;
import handling.RegexSelector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaFileProcessor extends AbstractFileProcessor {

    private static final String regex;
    private static final String singleLineCommentSymbol;
    private static final String singleLineCommentEscapeToken;
    private static final String singleLineTodoCommentEscapePrefix;

    static {
        regex = RegexSelector.getRegexByFileType("java");
        singleLineCommentSymbol = "//";
        singleLineCommentEscapeToken = "//" + UUID.randomUUID().toString();
        singleLineTodoCommentEscapePrefix = UUID.randomUUID().toString();
    }

     public JavaFileProcessor(CommentRemover commentRemover) {
        super(commentRemover);
    }

    @Override
    public void replaceCommentsWithBlanks() throws IOException, CommentRemoverException {
        super.replaceCommentsWithBlanks(regex);
    }

    @Override
    protected StringBuilder getFileContent(File file) throws IOException, CommentRemoverException {
        return isGoingToRemoveSingleComments() ? this.getContentForSingleLinesRemoving(file) : super.getPlainFileContent(file);
    }

    private boolean isGoingToRemoveSingleComments() {
        return commentRemover.isRemoveSingleLines();
    }

    private StringBuilder getContentForSingleLinesRemoving(File file) throws IOException {

        StringBuilder content = new StringBuilder((int) file.length());

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        for (String temp = br.readLine(); temp != null; temp = br.readLine()) {

            String trimmedTemp = temp.trim();
            if (trimmedTemp.startsWith(singleLineCommentSymbol) && !isContainTodo(trimmedTemp)) {
                content.append(singleLineCommentEscapeToken).append("\n");
            } else {
                content.append(temp).append("\n");
            }
        }
        br.close();

        return content;
    }

    @Override
    protected StringBuilder doRemoveOperation(StringBuilder fileContent, Matcher matcher) {
        try {
            String sFileContent = fileContent.toString();
            boolean isTodosRemoving = commentRemover.isRemoveTodos();
            boolean isBothCommentTypeNotSelected = isBothCommentTypeNotSelected();
            while (matcher.find()) {

                String foundToken = matcher.group();

                if (isDoubleOrSingleQuoteToken(foundToken)) {
                    continue;
                }

                if (isBothCommentTypeNotSelected) {

                    if (isOnlyMultiLineCommentSelected() && isSingleCommentToken(foundToken)) {
                        continue;
                    }

                    if (isOnlySingleCommentSelected() && isMultiLineCommentToken(foundToken)) {
                        continue;
                    }
                }

                if (isTodosRemoving) {
                    sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
                } else {

                    if (isSingleLineTodoToken(foundToken)) {
                        sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), foundToken.replace("//", singleLineTodoCommentEscapePrefix));
                    }

                    if (!isContainTodo(foundToken)) {
                        sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
                    }
                }
            }

            if (!isTodosRemoving) {
                sFileContent = sFileContent.replace(singleLineTodoCommentEscapePrefix, "//");
            }

            fileContent = new StringBuilder(sFileContent);

        } catch (StackOverflowError e) {
            System.err.println("StackOverflowError:Please increase your stack size! VM option command is: -Xss40m if you need to increase more -Xss{size}m");
            System.exit(0);
        }

        return fileContent;
    }

    private boolean isBothCommentTypeNotSelected() {
        return !(commentRemover.isRemoveSingleLines() && commentRemover.isRemoveMultiLines());
    }

    private boolean isDoubleOrSingleQuoteToken(String foundToken) {
        return foundToken.startsWith("\"") || foundToken.startsWith("\'");
    }

    private boolean isOnlyMultiLineCommentSelected() {
        return !commentRemover.isRemoveSingleLines() && commentRemover.isRemoveMultiLines();
    }

    private boolean isSingleCommentToken(String foundToken) {
        return foundToken.startsWith("//");
    }

    private boolean isOnlySingleCommentSelected() {
        return commentRemover.isRemoveSingleLines() && !commentRemover.isRemoveMultiLines();
    }

    private boolean isMultiLineCommentToken(String foundToken) {
        return foundToken.startsWith("/*");
    }

    private boolean isSingleLineTodoToken(String foundToken) {
        return isSingleCommentToken(foundToken) && isContainTodo(foundToken);
    }
}