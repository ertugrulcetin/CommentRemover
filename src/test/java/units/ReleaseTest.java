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
                .removeProperties(true)
                .removeJavaScript(true)
                .removeJSP(true)
                .removeCSS(true)
                .removeHTML(true)
                .removeXML(true)
                .removeSingleLines(true)
                .removeMultiLines(true)
                .removeTodos(true)
//                .startInternalPath(" ")
//                .startExternalPath("/Users/ertugrulcetin/IdeaProjects/dropwizard")
//                .startExternalPath("/Users/ertugrulcetin/IdeaProjects/guava")
                .startExternalPath("/Users/ertugrulcetin/IdeaProjects/elasticsearch")
//                .startExternalPath("/Users/ertugrulcetin/IdeaProjects/elasticsearch/src/main/java/org/elasticsearch/common")
//                .startExternalPath("/Users/ertugrulcetin/Downloads/elasticsearch-master")
//                .startExternalPath("/Users/ertugrulcetin/IdeaProjects/guava/guava-tests/benchmark/com/google/common/base")
//                .startExternalPath("/Users/ertugrulcetin/IdeaProjects/guava/guava/src/com/google/common/html/")
//                        .startExternalPath("/Users/ertugrulcetin/Desktop/ertucan")
                /*.setExcludePackages(new String[]{"blobstore",
                        "breaker",
                        "bytes",
                        "cli",
                        "collect",
                        "component",
                        "compress",
                        "geo",
                        "hash",
                        "inject",
                        "io",
                        "jna",
                        "joda",
                        "lease",
                        "logging",
                        "lucene",
                        "math",
                        "metrics",
                        "netty",
                        "network",
//                        "path",
                        "property",
                        "recycler",
                        "regex",
                        "rounding",
                        "settings",
                        "text",
                        "transport",
                        "unit",
                        "util",
                        "xcontent"})*/
                .build();

        CommentProcessor commentProcessor = new CommentProcessor(commentRemover);
        commentProcessor.start();
        e = System.currentTimeMillis();

        System.out.println("Estimated Time: " + (e - s));
    }
}
