package units;

import org.junit.Test;
import utility.CommentUtility;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class FileVisitingTest {

    @Test
    public void testVisitAllPaths() throws IOException {

        final String currentPath = System.getProperty("user.dir");

        Path path = Paths.get(currentPath);
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                System.out.println("Pre Dic: " + dir.getFileName());
                FileSystem fileSystem = dir.getFileSystem();

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                System.out.println("File: " + file.getFileName());

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

                System.out.println("Post Dic: " + dir.getFileName());

                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Test
    public void testPassGivenPath() throws IOException {

        Path startPath = Paths.get(System.getProperty("user.dir"));
        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                Objects.requireNonNull(dir);
                Objects.requireNonNull(attrs);

                Path parentPath = dir.getParent();
                if (parentPath == null) {
                    return FileVisitResult.CONTINUE;
                }

                String currentDirectoryPath = dir.toString();

                String excludePackagePath = CommentUtility.getStartPathInValidForm("src.main.java.app");
                if (currentDirectoryPath.equals(excludePackagePath)) {
                    return FileVisitResult.SKIP_SUBTREE;
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                Objects.requireNonNull(file);
                Objects.requireNonNull(attrs);

                String fileName = file.getFileName().toString();
                String fileExtension = CommentUtility.getExtension(fileName);
                if (fileExtension.equals("java")) {
                    System.out.println(fileName);
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {

                System.err.println("Error occurred");

                return super.visitFileFailed(file, exc);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return super.postVisitDirectory(dir, exc);
            }
        });

    }

    @Test
    public void testPassGivenPaths() throws IOException {

        Path startPath = Paths.get(System.getProperty("user.dir"));
        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                Objects.requireNonNull(dir);
                Objects.requireNonNull(attrs);

                Path parentPath = dir.getParent();
                if (parentPath == null) {
                    return FileVisitResult.CONTINUE;
                }

                String currentDirectoryPath = dir.toString();
                String[] excludePackagesPaths = CommentUtility.getExcludePackagesPathsInValidForm(new String[]{"src.main.java.app", "src.main.java.exception", "src.test.java"});
                for (String excludePackagesPath : excludePackagesPaths) {
                    if (currentDirectoryPath.equals(excludePackagesPath)) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                Objects.requireNonNull(file);
                Objects.requireNonNull(attrs);

                String fileName = file.getFileName().toString();
                String fileExtension = CommentUtility.getExtension(fileName);
                if (fileExtension.equals("java")) {
                    System.out.println(fileName);
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {

                System.err.println("Error occurred");

                return super.visitFileFailed(file, exc);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return super.postVisitDirectory(dir, exc);
            }
        });


    }

    @Test
    public void testOSPathSeparator() {

        String filePathSeparator = System.getProperty("file.separator");
        System.out.println(filePathSeparator);
    }

    @Test
    public void testOSCurrentPath() {

        String currentPath = System.getProperty("user.dir");
        System.out.println(currentPath);
    }

    @Test
    public void testStringFormat() {

        String path = String.format("%s%s%s", "/Users/ertugrulcetin", CommentUtility.getFileSeparator(), "Ertu.java");
        System.out.println(path);

    }
}
