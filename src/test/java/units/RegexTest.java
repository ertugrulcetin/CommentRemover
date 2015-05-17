package units;

import app.RegexPatterns;
import exception.CommentRemoverException;
import handling.FileProcessor;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class RegexTest {

    @Test
    public void testSingleLineComment() {

        String line = "//Deneme ses";

        Pattern singleLinePattern = Pattern.compile(RegexPatterns.getCommentRegexSingleLine());
        Matcher matcher = singleLinePattern.matcher(line);

        line = matcher.replaceAll("");

        assertEquals(line, "");
    }

    @Test
    public void testMultiLineComment() throws IOException, CommentRemoverException {

        FileProcessor fileProcessor = FileProcessor.getInstance();
        String content = fileProcessor.getFileContent(new File("/Users/ertugrulcetin/IdeaProjects/CommentRemover/src/test/java/dataset/multiline.txt"));

//        System.out.println(content);
        Pattern singleLinePattern = Pattern.compile(RegexPatterns.getCommentRegexMultiLine());
        Matcher matcher = singleLinePattern.matcher(content);

        System.out.println("Group Count: " + matcher.groupCount());

        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    @Test
    public void testSingleAndMultilineTest() throws IOException, CommentRemoverException {

        FileProcessor fileProcessor = FileProcessor.getInstance();
        File file = new File("/Users/ertugrulcetin/IdeaProjects/CommentRemover/src/test/java/dataset/multiline.txt");
        String content = fileProcessor.getFileContent(file);

//        System.out.println(content);
        Pattern singleLinePattern = Pattern.compile(RegexPatterns.getCommentRegexMultiAndSingleLine());
        Matcher matcher = singleLinePattern.matcher(content);

        String newContent = matcher.replaceAll("");
        fileProcessor.setFileContent(file, newContent);
    }

}