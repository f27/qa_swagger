package api.endpoints;

public enum Endpoints {
    PET("/pet"),
    STORE("/store"),
    USER("/user");

    String path;

    Endpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String addPath(String additionalPath) {
        return path + additionalPath;
    }
}
