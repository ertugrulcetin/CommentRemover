package com.commentremover.processors;

import com.commentremover.app.CommentRemover;
import com.commentremover.exception.CommentRemoverException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractFileProcessor {

    public abstract void replaceCommentsWithBlanks() throws IOException, CommentRemoverException;

    protected abstract StringBuilder getFileContent(File file) throws IOException, CommentRemoverException;

    protected abstract StringBuilder doRemoveOperation(StringBuilder fileContent, Matcher matcher);


    protected final CommentRemover commentRemover;
    private String currentFilePath;

    AbstractFileProcessor(CommentRemover commentRemover) {
        this.commentRemover = commentRemover;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    public void replaceCommentsWithBlanks(String regex) throws IOException, CommentRemoverException {

        File file = new File(currentFilePath);
        checkFileSize(file);

        StringBuilder fileContent = getFileContent(file);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileContent);

        StringBuilder newContent = doRemoveOperation(fileContent, matcher);

        setFileContent(file, newContent.toString());
    }

    protected void checkFileSize(File file) throws CommentRemoverException {

        long fileSize = file.length();

        if (fileSize > Integer.MAX_VALUE) {
            throw new CommentRemoverException("File size so big for scanning ! -> " + file.getAbsolutePath());
        }
    }

    protected StringBuilder getPlainFileContent(File file) throws IOException, CommentRemoverException {

        StringBuilder content = new StringBuilder((int) file.length());

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        for (String temp = br.readLine(); temp != null; temp = br.readLine()) {
            content.append(temp).append("\n");
        }
        br.close();

        return content;
    }

    protected void setFileContent(File file, String newContent) throws IOException {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        bw.write(newContent);
        bw.flush();
        bw.close();
    }

    protected boolean isContainTodo(String foundToken) {
        return Pattern.compile(Pattern.quote("todo"), Pattern.CASE_INSENSITIVE).matcher(foundToken).find();
    }
}
