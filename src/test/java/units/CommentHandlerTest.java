package units;

import app.CommentRemover;
import exception.CommentRemoverException;
import handling.CommentHandler;
import org.junit.Test;

public class CommentHandlerTest {


    @Test(expected = CommentRemoverException.class)
    public void test_Failed_checkAtLeastOneFileTypeAssigned() {

        CommentRemover commentRemover = new CommentRemover.CommentRemoveBuilder().build();

        CommentHandler commentHandler = new CommentHandler(commentRemover);
        try {
            commentHandler.checkAllStates();
        } catch (CommentRemoverException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_Passed_allStates() throws CommentRemoverException {

        CommentRemover commentRemover = new CommentRemover.CommentRemoveBuilder()
                .removeJava(true)
                .removeSingleLines(true)
//                .startPath("src.main")
                .startExternalPath("/Users/ertugrulcetin")
                .build();

        CommentHandler commentHandler = new CommentHandler(commentRemover);
        commentHandler.checkAllStates();
    }

}