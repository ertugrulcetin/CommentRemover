package processors;

import app.CommentRemover;

public class JavaScriptFileProcessor extends JavaFileProcessor {

    public JavaScriptFileProcessor(CommentRemover commentRemover) {
        super(commentRemover);
    }
}