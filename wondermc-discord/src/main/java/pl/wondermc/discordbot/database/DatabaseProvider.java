package pl.wondermc.discordbot.database;

import com.zaxxer.hikari.HikariDataSource;
import pl.wondermc.api.database.Database;
import pl.wondermc.discordbot.user.RewardCache;
import pl.wondermc.discordbot.user.RewardUser;
import pl.wondermc.discordbot.configuration.BotConfiguration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseProvider {

    private final BotConfiguration botConfiguration;
    private final HikariDataSource hikariDataSource;
    private final RewardCache rewardCache;


    public DatabaseProvider(BotConfiguration botConfiguration, RewardCache rewardCache) {
        this.botConfiguration = botConfiguration;
        this.rewardCache = rewardCache;
        this.hikariDataSource = new HikariDataSource();

        Database database = botConfiguration.getDatabase();
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
                stringBuilder.append("CREATE TABLE IF NOT EXISTS `boxpvp_discord` (");
                stringBuilder.append("nick varchar(32),");
                stringBuilder.append("collect varchar(10),");
                stringBuilder.append("primary key (nick))");
                statement.executeUpdate(stringBuilder.toString());

                ResultSet resultSet = statement.executeQuery("SELECT * FROM `boxpvp_discord`");
                while (resultSet.next()) {
                    RewardUser user = new RewardUser(resultSet.getString("nick"));
                    user.setCollect(resultSet.getBoolean("collect"));
                    this.rewardCache.getRewardCache().add(user);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }


    }

    public void saveUser(RewardUser user){
        try(Connection connection = hikariDataSource.getConnection()){
            if(connection == null){
                return;
            }
            Statement statement = connection.createStatement();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("INSERT INTO `boxpvp_discord` VALUES(");
            stringBuilder.append("'" + user.getNickName() +"',");
            stringBuilder.append("'" + user.isCollect() + "'");
            stringBuilder.append(") ON DUPLICATE KEY UPDATE ");
            stringBuilder.append("nick='"+ user.getNickName() + "',");
            stringBuilder.append("collect='" + user.isCollect() + "'");
            statement.executeUpdate(stringBuilder.toString());


        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

}
