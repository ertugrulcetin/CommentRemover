package utility;

import java.util.Arrays;
import java.util.List;


public class CommentUtility {

    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList("java", "js", "jsp", "html", "css", "xml", "properties");

    private static final char EXTENSION_SEPARATOR = '.';

    private static final char UNIX_SEPARATOR = '/';

    private static final char WINDOWS_SEPARATOR = '\\';

    public static String replaceDotWithSlash(String path) {

        String pathSeparator = getFileSeparator();

        return path.replaceAll("\\.", pathSeparator);
    }

    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    public static String getCurrentPath() {
        return System.getProperty("user.dir");
    }

    public static String getStartPathInValidForm(String path) {
        return (getCurrentPath() + getFileSeparator() + replaceDotWithSlash(path)).trim();
    }

    public static String getStartExternalPath(String path) {
        return path;
    }

    public static String[] getExcludePackagesPathsInValidForm(String[] paths) {

        String[] pathArray = new String[paths.length];
        for (int i = 0; i < pathArray.length; i++) {
            pathArray[i] = (getCurrentPath() + getFileSeparator() + replaceDotWithSlash(paths[i])).trim();
        }

        return pathArray;
    }

    public static String[] getExcludePackagesPathsInValidFormForExternalPath(String path, String[] paths) {

        String[] pathArray = new String[paths.length];
        for (int i = 0; i < pathArray.length; i++) {
            pathArray[i] = (path + getFileSeparator() + replaceDotWithSlash(paths[i])).trim();
        }

        return pathArray;
    }

    public static String getExtension(String filename) {

        if (filename == null) {
            return null;
        }

        int index = indexOfExtension(filename);
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    private static int indexOfExtension(String filename) {

        if (filename == null) {
            return -1;
        }

        int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        int lastSeparator = indexOfLastSeparator(filename);

        return (lastSeparator > extensionPos ? -1 : extensionPos);
    }

    private static int indexOfLastSeparator(String filename) {

        if (filename == null) {
            return -1;
        }

        int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
        int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);

        return Math.max(lastUnixPos, lastWindowsPos);
    }

    public static List<String> getSupportedExtensions() {
        return SUPPORTED_EXTENSIONS;
    }
}
