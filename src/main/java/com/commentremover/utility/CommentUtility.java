package com.commentremover.utility;

public class CommentUtility {

    private static final char EXTENSION_SEPARATOR = '.';

    private static final char UNIX_SEPARATOR = '/';

    private static final char WINDOWS_SEPARATOR = '\\';

    private CommentUtility() {
    }

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

    public static String getStartInternalPathInValidForm(String path) {
        return getCurrentPath() + getFileSeparator() + replaceDotWithSlash(path).trim();
    }

    public static String getStartExternalPathInValidForm(String path) {
        return path.trim();
    }

    public static String[] getExcludePackagesInValidFormForInternalStarting(String[] paths) {

        String[] pathArray = new String[paths.length];
        for (int i = 0; i < pathArray.length; i++) {
            pathArray[i] = getCurrentPath() + getFileSeparator() + replaceDotWithSlash(paths[i]).trim();
        }

        return pathArray;
    }

    public static String[] getExcludePackagesInValidFormForExternalStarting(String path, String[] paths) {

        String[] pathArray = new String[paths.length];
        for (int i = 0; i < pathArray.length; i++) {
            pathArray[i] = path + getFileSeparator() + replaceDotWithSlash(paths[i]).trim();
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
}
