package dataset;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Future;

public class VariableTest {





    
















    static {
        try {
            new URL("file://foo/bar/baz.txt");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
