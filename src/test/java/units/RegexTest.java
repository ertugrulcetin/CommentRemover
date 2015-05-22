package units;

import app.RegexPatterns;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class RegexTest {

    @Test
    public void testSingleLineComment() {

        String line = "// A note on memory visibility.";

        Pattern singleLinePattern = Pattern.compile(RegexPatterns.getCommentRegexCss());
        Matcher matcher = singleLinePattern.matcher(line);

        line = matcher.replaceAll("");

        assertEquals(line, "");
    }


    @Test
    public void testDoubleQutoe() {

        String singleLineRegex = RegexPatterns.getCommentRegexCss();

        Pattern pattern = Pattern.compile(singleLineRegex);
        Matcher matcher = pattern.matcher("file://foo/bar/baz.txt");

        String newregex = "\"([^\"]|\\\\\")*\"";
        Pattern pattern2 = Pattern.compile(newregex);
        Matcher matcher2 = pattern2.matcher("file://foo/bar/baz.txt");

        if (matcher.find()) {
            System.out.println(matcher.group());
            if (matcher2.find()) {
                System.out.println(matcher2.group());
            }
        }

    }

    @Test
    public void testMultiAndSingle() {


        String test = "/* deneme selamlar //kel adam geldi */ //\"deneme\" " + "\n" +
                "String ertu = \"file://ertucum benim\"";
        Pattern pattern = Pattern.compile("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/|[ \\t]*//.*)|\"([^\"]|\\\\\")*\"");
        Matcher matcher = pattern.matcher(test);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    @Test
    public void testSingle() {

        String content = "// This benchmark has no concept of \"noWorkToDo\".";

        Pattern pattern = Pattern.compile(RegexPatterns.getCommentRegexCss());
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        content = matcher.replaceAll("");

        assertEquals(content, "");

    }

    @Test
    public void testNestedQuto() {
        String details = "Hello \"world\"!";
        details = details.replace("\"", "\'");
        System.out.println(details);
    }

    @Test
    public void testString() {

        String temp = "";
        assertEquals(temp.trim().length() == 0, true);

    }

    @Test
    public void testComments() {

        String foundToken = "/*\n" +
                " * Copyright (C) 2011 The Guava Authors\n" +
                " *\n" +
                " * Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                " * you may not use this file except in compliance with the License.\n" +
                " * You may obtain a copy of the License at\n" +
                " *\n" +
                " * http://www.apache.org/licenses/LICENSE-2.0\n" +
                " *\n" +
                " * Unless required by applicable law or agreed to in writing, software\n" +
                " * distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                " * See the License for the specific language governing permissions and\n" +
                " * limitations under the License.\n" +
                " */";

        boolean isSingleLineComment = Pattern.compile("([\\t]*//.*)").matcher(foundToken).find();
        System.out.println("Is single line?: " + isSingleLineComment);
    }

}

