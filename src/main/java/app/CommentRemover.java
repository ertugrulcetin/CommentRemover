package app;

import java.util.Arrays;

public final class CommentRemover {

    private boolean removeCSS;
    private boolean removeHTML;
    private boolean removeJavaScript;
    private boolean removeXML;
    private boolean removeJSP;
    private boolean removeJava;
    private boolean removeProperties;

    private boolean removeTodos;
    private boolean removeMultiLines;
    private boolean removeSingleLines;

    private String startPath;
    private String startExternalPath;
    private String[] excludePackagesPaths;

    private CommentRemover(CommentRemoverBuilder commentRemoverBuilder) {
        this.removeCSS = commentRemoverBuilder.removeCSS;
        this.removeHTML = commentRemoverBuilder.removeHTML;
        this.removeJavaScript = commentRemoverBuilder.removeJavaScript;
        this.removeXML = commentRemoverBuilder.removeXML;
        this.removeJSP = commentRemoverBuilder.removeJSP;
        this.removeJava = commentRemoverBuilder.removeJava;
        this.removeProperties = commentRemoverBuilder.removeProperties;
        this.removeTodos = commentRemoverBuilder.removeTodos;
        this.removeMultiLines = commentRemoverBuilder.removeMultiLines;
        this.removeSingleLines = commentRemoverBuilder.removeSingleLines;
        this.startPath = commentRemoverBuilder.startPath;
        this.startExternalPath = commentRemoverBuilder.startExternalPath;
        this.excludePackagesPaths = commentRemoverBuilder.excludePackagesPaths;
    }

    public boolean isRemoveCSS() {
        return removeCSS;
    }

    public boolean isRemoveHTML() {
        return removeHTML;
    }

    public boolean isRemoveJavaScript() {
        return removeJavaScript;
    }

    public boolean isRemoveXML() {
        return removeXML;
    }

    public boolean isRemoveJSP() {
        return removeJSP;
    }

    public boolean isRemoveJava() {
        return removeJava;
    }

    public boolean isRemoveProperties() {
        return removeProperties;
    }

    public boolean isRemoveTodos() {
        return removeTodos;
    }

    public boolean isRemoveMultiLines() {
        return removeMultiLines;
    }

    public boolean isRemoveSingleLines() {
        return removeSingleLines;
    }

    public String getStartPath() {
        return startPath;
    }

    public String getStartExternalPath() {
        return startExternalPath;
    }

    public String[] getExcludePackagesPaths() {
        return excludePackagesPaths;
    }

    public static class CommentRemoverBuilder {

        private boolean removeCSS = false;
        private boolean removeHTML = false;
        private boolean removeJavaScript = false;
        private boolean removeXML = false;
        private boolean removeJSP = false;
        private boolean removeJava = false;
        private boolean removeProperties = false;

        private boolean removeTodos = true;
        private boolean removeMultiLines = false;
        private boolean removeSingleLines = false;

        private String startPath = null;
        private String startExternalPath = null;
        private String[] excludePackagesPaths = null;

        public CommentRemoverBuilder removeCSS(boolean removeCSS) {
            this.removeCSS = removeCSS;
            return this;
        }

        public CommentRemoverBuilder removeHTML(boolean removeHTML) {
            this.removeHTML = removeHTML;
            return this;
        }

        public CommentRemoverBuilder removeJavaScript(boolean removeJavaScript) {
            this.removeJavaScript = removeJavaScript;
            return this;
        }

        public CommentRemoverBuilder removeXML(boolean removeXML) {
            this.removeXML = removeXML;
            return this;
        }

        public CommentRemoverBuilder removeJSP(boolean removeJSP) {
            this.removeJSP = removeJSP;
            return this;
        }

        public CommentRemoverBuilder removeJava(boolean removeJava) {
            this.removeJava = removeJava;
            return this;
        }

        public CommentRemoverBuilder removeProperties(boolean removeProperties) {
            this.removeProperties = removeProperties;
            return this;
        }

        public CommentRemoverBuilder removeTodos(boolean removeTodos) {
            this.removeTodos = removeTodos;
            return this;
        }

        public CommentRemoverBuilder removeMultiLines(boolean removeMultiLines) {
            this.removeMultiLines = removeMultiLines;
            return this;
        }

        public CommentRemoverBuilder removeSingleLines(boolean removeSingleLines) {
            this.removeSingleLines = removeSingleLines;
            return this;
        }

        public CommentRemoverBuilder startPath(String startPath) {
            this.startPath = startPath;
            return this;
        }

        public CommentRemoverBuilder startExternalPath(String externalFullPath) {
            this.startExternalPath = externalFullPath;
            return this;
        }

        public CommentRemoverBuilder setExcludePackagesPaths(String[] excludePackagesPaths) {
            this.excludePackagesPaths = excludePackagesPaths;
            return this;
        }

        public CommentRemover build() {
            return new CommentRemover(this);
        }
    }

    @Override
    public String toString() {
        return "CommentRemover{" +
                "removeCSS=" + removeCSS +
                ", removeHTML=" + removeHTML +
                ", removeJavaScript=" + removeJavaScript +
                ", removeXML=" + removeXML +
                ", removeJSP=" + removeJSP +
                ", removeJava=" + removeJava +
                ", removeProperties=" + removeProperties +
                ", removeTodos=" + removeTodos +
                ", removeMultiLines=" + removeMultiLines +
                ", removeSingleLines=" + removeSingleLines +
                ", startPath='" + startPath + '\'' +
                ", startExternalPath='" + startExternalPath + '\'' +
                ", excludePackagesPaths=" + Arrays.toString(excludePackagesPaths) +
                '}';
    }
}
