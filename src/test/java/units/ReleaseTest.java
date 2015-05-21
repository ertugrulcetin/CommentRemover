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
                .removeJava(true)
//                .removeProperties(true)
                .removeSingleLines(true)
                .removeMultiLines(true)
//                .removeTodos(false)
//                .removeTodos(false)
//                .startInternalPath(" ")
                .startExternalPath("/Users/ertugrulcetin/IdeaProjects/guava")
//                .startExternalPath("/Users/ertugrulcetin/IdeaProjects/guava/guava-tests/benchmark/com/google/common/base")
//                .startExternalPath("/Users/ertugrulcetin/IdeaProjects/guava/guava/src/com/google/common/html/")
//                        .startExternalPath("/Users/ertugrulcetin/Desktop/ertucan")
//                .setExcludePackages(new String[]{"src.test"})
                .build();

        CommentProcessor commentProcessor = new CommentProcessor(commentRemover);
        commentProcessor.start();
        e = System.currentTimeMillis();

        System.out.println("Estimated Time: " + (e - s));
    }
}
