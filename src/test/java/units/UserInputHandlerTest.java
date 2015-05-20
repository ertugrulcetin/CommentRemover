package units;

import app.CommentRemover;
import exception.CommentRemoverException;
import handling.UserInputHandler;
import org.junit.Test;

public class UserInputHandlerTest {


    @Test(expected = CommentRemoverException.class)
    public void test_Failed_checkAtLeastOneFileTypeAssigned() {

        CommentRemover commentRemover = new CommentRemover.CommentRemoverBuilder().build();

        UserInputHandler userInputHandler = new UserInputHandler(commentRemover);
        try {
            userInputHandler.checkAllStates();
        } catch (CommentRemoverException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_Passed_allStates() throws CommentRemoverException {

        CommentRemover commentRemover = new CommentRemover.CommentRemoverBuilder()
                .removeJava(true)
                .removeSingleLines(true)
                .startPath("src.main")

                .build();

        System.out.println(commentRemover);

        UserInputHandler userInputHandler = new UserInputHandler(commentRemover);
        userInputHandler.checkAllStates();
    }

    @Test(expected = CommentRemoverException.class)
    public void test_Failed_ExcludePaths() throws CommentRemoverException {

        CommentRemover commentRemover = new CommentRemover.CommentRemoverBuilder()
                .removeCSS(true)
                .removeSingleLines(true)
                .startPath("src.main")
                .setExcludePackagesPaths(new String[]{"src.main.java.handling", "src.main.utility2"})
                .build();

        UserInputHandler userInputHandler = new UserInputHandler(commentRemover);
        userInputHandler.checkAllStates();
    }
}
