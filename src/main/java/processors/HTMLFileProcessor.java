package processors;

import app.CommentRemover;

public class HTMLFileProcessor extends XMLFileProcessor {

    public HTMLFileProcessor(CommentRemover commentRemover) {
        super(commentRemover);
    }
}