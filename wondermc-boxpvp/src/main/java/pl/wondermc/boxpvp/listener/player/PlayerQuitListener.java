package pl.wondermc.boxpvp.listener.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.wondermc.api.helper.TimeHelper;
import pl.wondermc.boxpvp.configuration.DailyRewardConfiguration;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.database.DatabaseProvider;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;
import pl.wondermc.boxpvp.user.sub.UserFight;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class PlayerQuitListener implements Listener {

    private final UserService userService;
    private final DailyRewardConfiguration dailyRewardConfiguration;
    private final DatabaseProvider databaseProvider;
    private final PluginConfiguration pluginConfiguration;

    public PlayerQuitListener(UserService userService, DailyRewardConfiguration dailyRewardConfiguration,
                              DatabaseProvider databaseProvider, PluginConfiguration pluginConfiguration){
        this.userService = userService;
        this.dailyRewardConfiguration = dailyRewardConfiguration;
        this.databaseProvider = databaseProvider;
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        for (String message : this.pluginConfiguration.getPlayerGlobalQuitMessage()) {
            event.setQuitMessage(ChatHelper.fixColor(message.replace("{PLAYER}", player.getName())));
        }
        Optional<User> optionalUser = userService.findUser(player.getUniqueId());
        if(optionalUser.isEmpty()){
            return;
        }
        User user = optionalUser.get();
        Date currentDate = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar. getInstance();
        calendar.setTime(currentDate);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        user.setLeftAt(System.currentTimeMillis());
        if(user.getSpentTimeMap().get(dayOfMonth) != null){
            user.getSpentTimeMap().put(dayOfMonth, (user.getSpentTimeMap().get(dayOfMonth) + user.getLeftAt() - user.getJoinedAt()));
        }
        user.getDailyRewardMap().forEach((day, status) -> {
            if(day < dayOfMonth && !status) {
                if(user.getSpentTimeMap().get(day) > TimeHelper.timeFromString(this.dailyRewardConfiguration.getRequiredSpentTime())){
                    user.getDailyRewardMissedMap().put(day, user.getSpentTimeMap().get(day));
                }
            }
        });

        UserFight userFight = user.getUserFight();
        if(userFight.has()){
            Player killer = Bukkit.getPlayerExact(userFight.getLastAttacker());
            event.setQuitMessage(ChatHelper.fixColor(this.pluginConfiguration.getFightQuitInformation()
                    .replace("{PLAYER}", player.getName())));
            userFight.setLastFight(0L);
            userFight.setLastAttacker(null);
            if(killer != null){
                player.damage(100, killer);
            } else {
                player.setHealth(0);
            }

        }

        this.databaseProvider.saveUser(user);
    }
}
