package com.commentremover.processors;

import com.commentremover.app.CommentRemover;
import com.commentremover.exception.CommentRemoverException;
import com.commentremover.handling.RegexSelector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyFileProcessor extends AbstractFileProcessor {

    private static final String regex;
    private static final String singleLineCommentSymbol;
    private static final String singleLineCommentEscapeToken;

    static {
        regex = RegexSelector.getRegexByFileType("properties");
        singleLineCommentSymbol = "#";
        singleLineCommentEscapeToken = "#" + UUID.randomUUID().toString();
    }

    public PropertyFileProcessor(CommentRemover commentRemover) {
        super(commentRemover);
    }

    @Override
    public void replaceCommentsWithBlanks() throws IOException, CommentRemoverException {
        super.replaceCommentsWithBlanks(regex);
    }

    @Override
    protected StringBuilder getFileContent(File file) throws IOException, CommentRemoverException {

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
    protected StringBuilder doRemoveOperation(StringBuilder fileContent, Matcher matcher) throws StackOverflowError {

        String sFileContent = fileContent.toString();
        boolean isTodosRemoving = commentRemover.isRemoveTodos();
        while (matcher.find()) {

            String foundToken = matcher.group();

            if (isEqualsToken(foundToken)) {
                continue;
            }

            if (isTodosRemoving) {
                sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
            } else {
                if (!isContainTodo(foundToken)) {
                    sFileContent = sFileContent.replaceFirst(Pattern.quote(foundToken), "");
                }
            }
        }

        fileContent = new StringBuilder(sFileContent);


        return fileContent;
    }

    private boolean isEqualsToken(String foundToken) {
        return foundToken.startsWith("=");
    }
}