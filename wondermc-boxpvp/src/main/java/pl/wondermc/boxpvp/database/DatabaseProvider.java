package pl.wondermc.boxpvp.database;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import pl.wondermc.api.database.Database;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseProvider {

    private final PluginConfiguration pluginConfiguration;
    private final HikariDataSource hikariDataSource;
    private final UserService userService;


    public DatabaseProvider(PluginConfiguration pluginConfiguration, UserService userService) {
        this.pluginConfiguration = pluginConfiguration;
        this.userService = userService;
        this.hikariDataSource = new HikariDataSource();

        Database database = pluginConfiguration.getDatabase();
        this.hikariDataSource.setJdbcUrl("jdbc:mysql://" + database.getHostname() + ":" + database.getPort() + "/" +
                database.getDatabase() + "?characterEncoding=UTF-8&useUnicode=true&useSSL=true");
        this.hikariDataSource.setUsername(database.getUser());
        this.hikariDataSource.setPassword(database.getPassword());
        this.hikariDataSource.addDataSourceProperty("cachePrepStmts", "true");
        this.hikariDataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        this.hikariDataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        this.hikariDataSource.addDataSourceProperty("useServerPrepStmts", true);
        this.hikariDataSource.addDataSourceProperty("cacheResultSetMetadata", true);
        this.hikariDataSource.addDataSourceProperty("tcpKeepAlive", true);

        this.initialize();
    }

    private void initialize(){
        try (Connection connection = hikariDataSource.getConnection()) {
            if (connection == null) {
                return;
            }
            try {
                Statement statement = connection.createStatement();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("CREATE TABLE IF NOT EXISTS `boxpvp_users` (");
                stringBuilder.append("uuid varchar(36),");
                stringBuilder.append("json longText not null,");
                stringBuilder.append("primary key (uuid))");
                statement.executeUpdate(stringBuilder.toString());

                ResultSet resultSet = statement.executeQuery("SELECT * FROM `boxpvp_users`");
                while (resultSet.next()) {
                    User user = new Gson().fromJson(resultSet.getString("json"), User.class);
                    this.userService.getUserMap().put(user.getUniqueId(), user);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }


    }

    public void saveUser(User user){
        try(Connection connection = hikariDataSource.getConnection()){
            if(connection == null){
                return;
            }
            Statement statement = connection.createStatement();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("INSERT INTO `boxpvp_users` VALUES(");
            stringBuilder.append("'" + user.getUniqueId() +"',");
            stringBuilder.append("'" + new Gson().toJson(user) + "'");
            stringBuilder.append(") ON DUPLICATE KEY UPDATE ");
            stringBuilder.append("uuid = '"+ user.getUniqueId() + "',");
            stringBuilder.append("json='" + new Gson().toJson(user) + "'");
            statement.executeUpdate(stringBuilder.toString());


        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }


}
