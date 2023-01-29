package pl.wondermc.api.database;

public class Database {

    private final String hostname;
    private final String database;
    private final int port;
    private final String user;
    private final String password;

    public Database(String hostname, String database, int port, String user, String password) {
        this.hostname = hostname;
        this.database = database;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public String getDatabase() {
        return database;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
