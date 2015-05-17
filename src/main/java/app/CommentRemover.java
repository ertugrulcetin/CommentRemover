package app;

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
    private boolean reformatAfterRemoving;

    private String startPath;
    private String[] excludePackagesPaths;

    private CommentRemover(CommentRemoveBuilder commentRemoveBuilder) {
        this.removeCSS = commentRemoveBuilder.removeCSS;
        this.removeHTML = commentRemoveBuilder.removeHTML;
        this.removeJavaScript = commentRemoveBuilder.removeJavaScript;
        this.removeXML = commentRemoveBuilder.removeXML;
        this.removeJSP = commentRemoveBuilder.removeJSP;
        this.removeJava = commentRemoveBuilder.removeJava;
        this.removeProperties = commentRemoveBuilder.removeProperties;
        this.removeTodos = commentRemoveBuilder.removeTodos;
        this.removeMultiLines = commentRemoveBuilder.removeMultiLines;
        this.removeSingleLines = commentRemoveBuilder.removeSingleLines;
        this.reformatAfterRemoving = commentRemoveBuilder.reformatAfterRemoving;
        this.startPath = commentRemoveBuilder.startPath;
        this.excludePackagesPaths = commentRemoveBuilder.excludePackagesPaths;
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

    public boolean isReformatAfterRemoving() {
        return reformatAfterRemoving;
    }

    public String getStartPath() {
        return startPath;
    }

    public String[] getExcludePackagesPaths() {
        return excludePackagesPaths;
    }

    public static class CommentRemoveBuilder {

        private boolean removeCSS = false;
        private boolean removeHTML = false;
        private boolean removeJavaScript = false;
        private boolean removeXML = false;
        private boolean removeJSP = false;
        private boolean removeJava = false;
        private boolean removeProperties = false;

        private boolean removeTodos = false;
        private boolean removeMultiLines = false;
        private boolean removeSingleLines = false;
        private boolean reformatAfterRemoving = false;

        public String startPath = System.getProperty("user.dir");
        private String[] excludePackagesPaths;

        public CommentRemoveBuilder removeCSS(boolean removeCSS) {
            this.removeCSS = removeCSS;
            return this;
        }

        public CommentRemoveBuilder removeHTML(boolean removeHTML) {
            this.removeHTML = removeHTML;
            return this;
        }

        public CommentRemoveBuilder removeJavaScript(boolean removeJavaScript) {
            this.removeJavaScript = removeJavaScript;
            return this;
        }

        public CommentRemoveBuilder removeXML(boolean removeXML) {
            this.removeXML = removeXML;
            return this;
        }

        public CommentRemoveBuilder removeJSP(boolean removeJSP) {
            this.removeJSP = removeJSP;
            return this;
        }

        public CommentRemoveBuilder removeJava(boolean removeJava) {
            this.removeJava = removeJava;
            return this;
        }

        public CommentRemoveBuilder removeProperties(boolean removeProperties) {
            this.removeProperties = removeProperties;
            return this;
        }

        public CommentRemoveBuilder removeTodos(boolean removeTodos) {
            this.removeTodos = removeTodos;
            return this;
        }

        public CommentRemoveBuilder removeMultiLines(boolean removeMultiLines) {
            this.removeMultiLines = removeMultiLines;
            return this;
        }

        public CommentRemoveBuilder removeSingleLines(boolean removeSingleLines) {
            this.removeSingleLines = removeSingleLines;
            return this;
        }

        public CommentRemoveBuilder reformatAfterRemoving(boolean reformatAfterRemoving) {
            this.reformatAfterRemoving = reformatAfterRemoving;
            return this;
        }

        public CommentRemoveBuilder startPath(String startPath) {
            this.startPath = startPath;
            return this;
        }

        public CommentRemoveBuilder setExcludePackagesPaths(String[] excludePackagesPaths) {
            this.excludePackagesPaths = excludePackagesPaths;
            return this;
        }

        public CommentRemover build() {
            return new CommentRemover(this);
        }
    }
}