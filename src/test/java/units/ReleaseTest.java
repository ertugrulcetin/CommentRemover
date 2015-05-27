package units;

import app.CommentProcessor;
import app.CommentRemover;
import org.junit.Test;

public class ReleaseTest {
    @Test
    public void testFirstAttempt() {

        CommentRemover commentRemover = new CommentRemover.CommentRemoverBuilder()
                .removeJava(true)
                .removeSingleLines(true)
                .removeMultiLines(true)
                .startInternalPath("src")
                .setExcludePackages(new String[]{"src.main.java.app", "src.main.java.exception"})
                .build();

        CommentProcessor commentProcessor = new CommentProcessor(commentRemover);
        commentProcessor.start();
    }

    @Test
    public void testExternalPath() {
        long s, e;

        s = System.currentTimeMillis();
        CommentRemover commentRemover = new CommentRemover.CommentRemoverBuilder()
//                .removeXML(true)
//                .removeCSS(true)
//                .removeHTML(true)
                .removeJSP(true)
                .removeSingleLines(true)
                .removeMultiLines(true)
                .removeTodos(true)
                .startInternalPath(" ")
                .build();

        CommentProcessor commentProcessor = new CommentProcessor(commentRemover);
        commentProcessor.start();
        e = System.currentTimeMillis();

        System.out.println("Estimated Time: " + (e - s));
    }
}
