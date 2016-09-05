package com.commentremover.processors;

import com.commentremover.app.CommentRemover;
import com.commentremover.exception.CommentRemoverException;
import com.commentremover.handling.RegexSelector;

import java.io.IOException;

public class JSPFileProcessor extends HTMLFileProcessor {

    private static final String regex = RegexSelector.getRegexByFileType("jsp");

    public JSPFileProcessor(CommentRemover commentRemover) {
        super(commentRemover);
    }

    @Override
    public void replaceCommentsWithBlanks() throws IOException, CommentRemoverException {
        super.replaceCommentsWithBlanks(regex);
    }
}