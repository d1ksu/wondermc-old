package pl.wondermc.boxpvp.listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.wondermc.api.helper.TimeHelper;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;
import pl.wondermc.boxpvp.user.sub.UserFight;

import java.util.Optional;

public class PlayerDeathListener implements Listener {

    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;

    public PlayerDeathListener(UserService userService, PluginConfiguration pluginConfiguration){
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        Player killer = player.getKiller();
        if(killer != null){
            Optional<User> optionalUserKiller = this.userService.findUser(killer.getUniqueId());
            if(optionalUserKiller.isEmpty()){
                return;
            }
            User userKiller = optionalUserKiller.get();
            UserFight userFight = userKiller.getUserFight();
            if(userFight.has()) {
                userFight.setLastFight(System.currentTimeMillis() +
                        TimeHelper.timeFromString(this.pluginConfiguration.getFightDecrease()));
            }
        }
    }
}
