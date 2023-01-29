package pl.wondermc.api.database;

public class Nats {

    private final String hostname;
    private final int port;
    private final String password;

    public Nats(String hostname, int port, String password) {
        this.hostname = hostname;
        this.port = port;
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }
}
