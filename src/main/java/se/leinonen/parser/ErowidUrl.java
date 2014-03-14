package se.leinonen.parser;

public class ErowidUrl implements Comparable<ErowidUrl> {

    public enum Type {
        DRUG("drug"),
        BASICS("basics"),
        EFFECTS("effects"),
        IMAGES("images"),
        UNKNOWN("unknown");
        private String type;

        private Type(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    // Magic constants for processing urls.
    private static final int BASE_INDEX = 0;
    private static final int HTTP_INDEX = 7;

    private String originalUrl;
    private String baseUrl;
    private String fileName;
    private String category;
    private String path;
    private String url;
    private String simpleName;
    private Type type;
    private ErowidUrl parent;

    private ErowidUrl(){
    }

    /**
     * Default constructor using an url as a parameter.
     *
     * @param url the source url.
     */
    public ErowidUrl(String url) {
        this(url, null);
    }

    /**
     * Constructor with a parent parameter so we can create a
     * complete url from a "broken" url, assuming the parent url is correct.
     *
     * @param url the source url.
     * @param parent parent url (assumed to be complete).
     */
    public ErowidUrl(String url, ErowidUrl parent) {
        processUrl(url, parent);
        identifyType();
    }

    public int compareTo(ErowidUrl that) {
        //return this.originalUrl.compareTo(o.getOriginalUrl());
        return this.url.compareTo(that.url);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public ErowidUrl getParent() {
        return parent;
    }

    public String getPath() {
        return path;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public Type getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean isHttp() {
        return isHttp(getUrl());
    }

    public boolean isValidErowidUrl() {
        boolean urlValid = isHttp(url) && !invalidUrl(url) && url.endsWith(".shtml");
        boolean originalValid = originalUrl.startsWith("/plants") || originalUrl.startsWith("/chemicals");
        return urlValid || originalValid;
    }

    @Override
    public String toString() {
        return getUrl();
    }

    private String assemble(String base, String path, String fileName) {
        StringBuilder sb = new StringBuilder();
        if (!"".equals(base)) {
            sb.append(base).append("/");
        }
        if (!"".equals(path)) {
            sb.append(path).append("/");
        }
        sb.append(fileName);
        return sb.toString();
    }

    private String calcBase(String url) {
        if (isHttp(url)) {
            return url.substring(BASE_INDEX, HTTP_INDEX + url.substring(HTTP_INDEX).indexOf("/"));

        } else {
            return "";
        }
    }

    private String calcCategory(String path) {
        if (containsSlash(path)) {
            return path.substring(BASE_INDEX, path.lastIndexOf("/"));
        } else {
            return "";
        }
    }

    private String calcPath(String url) {
        if (isHttp(url)) {
            return calcCategory(url.substring(HTTP_INDEX).substring(url.substring(HTTP_INDEX).indexOf("/") + 1));
        } else {
            return "";
        }
    }

    private boolean containsSlash(String url) {
        return url.contains("/");
    }

    private String createSimpleName() {
        int index = fileName.indexOf(".");
        if (index > 0) {
            return fileName.substring(0, index).replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        }
        return fileName;
    }

    private String erowidFix(String url) {
        url = trimPath(url, "psychoactives");
        url = trimPath(url, "general");
        return url;
    }

    private String trimPath(String url, String path) {
        String[] keywords = {"chemicals", "plants", "entheogens", "herbs",
                             "library", "general", "psychoactives"};
        for (String key : keywords) {
            if (url.contains("/" + path + "/" + key + "/")) {
                url = url.replace("/" + path + "/", "/");
                break;
            }
        }
        return url;
    }

    /**
     * Try to identify the type of the url..
     */
    private void identifyType() {
        if (this.url.contains("plants") || this.url.contains("chemicals")) {
            this.type = Type.DRUG;
            this.simpleName = createSimpleName();
        }
        if (this.fileName.endsWith("basics.shtml")) {
            this.type = Type.BASICS;
        }
        if (this.fileName.endsWith("effects.shtml")) {
            this.type = Type.EFFECTS;
        }
        if (this.fileName.endsWith("images.shtml")) {
            this.type = Type.IMAGES;
        }
    }

    private boolean invalidUrl(String url) {
        String[] invalido = {"-", "#", ".php"};
        for (String s : invalido) {
            if (url.contains(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isHttp(String url) {
        return url.startsWith("http://");
    }

    /**
     * Processes the url, trying to figure out what kind of url it is
     * and also extracting "important" parts of it so we can use it later.
     * @param theUrl the url to be processed.
     * @param parent specify a parent url if theUrl is incomplete.
     */
    private void processUrl(String theUrl, ErowidUrl parent) {
        this.parent = parent;
        this.type = Type.UNKNOWN;
        this.originalUrl = theUrl;
        this.simpleName = "";
        this.baseUrl = "";
        this.fileName = "";
        this.category = "";
        this.path = "";

        if (invalidUrl(theUrl)) {

            this.url = erowidFix(assemble(this.baseUrl, this.path, this.fileName));
            return;

        } else if (theUrl.startsWith("/")) {

            this.fileName = theUrl.substring(1);
            this.url = erowidFix(assemble(this.baseUrl, this.path, this.fileName));
            return;

        } else if (containsSlash(theUrl) && theUrl.substring(HTTP_INDEX).contains("/")) {

            theUrl = erowidFix(theUrl);
            this.fileName = theUrl.substring(theUrl.lastIndexOf("/") + 1);
            this.path = calcPath(theUrl);
            this.category = calcCategory(calcPath(theUrl));
            this.baseUrl = calcBase(theUrl);
            this.url = assemble(this.baseUrl, this.path, this.fileName);

        } else {

            this.fileName = theUrl;
            this.url = erowidFix(assemble(this.baseUrl, this.path, this.fileName));
        }

        if (!isHttp(this.originalUrl) && this.parent != null) {
            this.baseUrl = parent.getBaseUrl();
            this.category = parent.getCategory();
            this.path = parent.getPath();
            this.url = erowidFix(assemble(parent.getBaseUrl(), parent.getPath(), this.fileName));
        }

    }
}
