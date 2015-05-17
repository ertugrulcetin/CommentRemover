package handling;

import exception.CommentRemoverException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileProcessor {

    private static final FileProcessor FILE_PROCESSOR = new FileProcessor();

    private FileProcessor() {
    }

    public static FileProcessor getInstance() {
        return FILE_PROCESSOR;
    }

    public String getFileContent(File file) throws IOException, CommentRemoverException {

        long fileSize = file.length();

        if (fileSize > Integer.MAX_VALUE) {
            throw new CommentRemoverException("File size so big for scanning !");
        }

        String temp;
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder((int) fileSize);
        while ((temp = br.readLine()) != null) {
            content.append(temp).append("\n");
        }
        br.close();

        return content.toString();
    }

    public void setFileContent(File file, String newContent) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(newContent);
        bw.flush();
        bw.close();
    }

}