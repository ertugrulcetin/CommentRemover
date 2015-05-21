package units;

import org.junit.Test;

public class PassingTest {


    @Test
    public void testStringBuilder(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ertucum");

        StringBuilder newContent = passing(stringBuilder);
        System.out.println(newContent);
    }

    public StringBuilder passing(StringBuilder stringBuilder) {
        return new StringBuilder("cansu");
    }
}
