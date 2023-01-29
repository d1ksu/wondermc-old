package pl.wondermc.api;

public class API {

    private static API instance;

    public API(){
        instance = this;
    }

    public static API getInstance() {
        return instance;
    }
}
