package processors;

import app.CommentRemover;
import exception.CommentRemoverException;
import handling.RegexSelector;

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