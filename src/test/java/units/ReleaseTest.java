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
                .startPath("src")
                .setExcludePackagesPaths(new String[]{"src.main.java.app", "src.main.java.exception"})
                .build();

        CommentProcessor commentProcessor = new CommentProcessor(commentRemover);
        commentProcessor.start();
    }

    @Test
    public void testExternalPath() {
        long s, e;

        s = System.currentTimeMillis();
        CommentRemover commentRemover = new CommentRemover.CommentRemoverBuilder()
                .removeJava(true)
                .removeProperties(true)
                .removeSingleLines(true)
                .removeMultiLines(true)
                .removeTodos(false)
                .startPath(" ")
//                .startExternalPath("/Users/ertugrulcetin/IdeaProjects/guava")
                .setExcludePackagesPaths(new String[]{"src.test"})
                .build();

        CommentProcessor commentProcessor = new CommentProcessor(commentRemover);
        commentProcessor.start();
        e = System.currentTimeMillis();

        System.out.println("Estimated Time: " + (e - s));
    }
}