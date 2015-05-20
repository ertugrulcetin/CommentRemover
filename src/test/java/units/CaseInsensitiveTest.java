package units;

import org.junit.Test;

import java.util.regex.Pattern;

public class CaseInsensitiveTest {

    @Test
    public void testCaseSensitivity() {

        String content = "//Todo sesler";
        String todo = "TODO";

        if (Pattern.compile(Pattern.quote(todo), Pattern.CASE_INSENSITIVE).matcher(content).find()) {
            System.out.println("Oldu");
        }

    }
}
