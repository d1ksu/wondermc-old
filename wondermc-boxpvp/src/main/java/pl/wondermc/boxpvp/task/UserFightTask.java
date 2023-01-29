package pl.wondermc.boxpvp.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.wondermc.api.helper.TimeHelper;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.UserService;
import pl.wondermc.boxpvp.user.sub.UserBar;
import pl.wondermc.boxpvp.user.sub.UserFight;

public class UserFightTask implements Runnable{

    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;

    public UserFightTask(UserService userService, PluginConfiguration pluginConfiguration) {
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Override
    public void run() {
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
            this.userService.findUser(onlinePlayer.getUniqueId())
                    .ifPresent(user -> {
                        UserFight userFight = user.getUserFight();
                        UserBar userBar = user.getUserBar();
                        if(userFight.has()){
                            userBar.updateActionBar(UserBar.Actionbar.FIGHT,
                                    ChatHelper.fixColor(this.pluginConfiguration.getFightInformation()
                                            .replace("{TIME}", TimeHelper.timeToString(userFight.getLastFight()
                                                    - System.currentTimeMillis()))));
                        } else {
                            userBar.removeActionBar(UserBar.Actionbar.FIGHT);
                        }
                    });
        }
    }
}
