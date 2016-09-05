package com.commentremover.processors;

import com.commentremover.app.CommentRemover;
import com.commentremover.exception.CommentRemoverException;
import com.commentremover.handling.RegexSelector;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSSFileProcessor extends AbstractFileProcessor {

    private static final String regex = RegexSelector.getRegexByFileType("css");

    public CSSFileProcessor(CommentRemover commentRemover) {
        super(commentRemover);
    }

    @Override
    public void replaceCommentsWithBlanks() throws IOException, CommentRemoverException {
        super.replaceCommentsWithBlanks(regex);
    }

    @Override
    protected StringBuilder getFileContent(File file) throws IOException, CommentRemoverException {
        return super.getPlainFileContent(file);
    }

    @Override
    protected StringBuilder doRemoveOperation(StringBuilder fileContent, Matcher matcher) throws StackOverflowError{

        String sFileContent = fileContent.toString();
        boolean isTodosRemoving = commentRemover.isRemoveTodos();

        while (matcher.find()) {

            String foundToken = matcher.group();

            if (isDoubleOrSingleQuoteToken(foundToken)) {
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

    private boolean isDoubleOrSingleQuoteToken(String foundToken) {
        return foundToken.startsWith("\"") || foundToken.startsWith("\'");
    }
}