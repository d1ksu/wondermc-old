package pl.wondermc.boxpvp.listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.UserService;

public class PlayerCommandPreProcessListener implements Listener {

    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;

    public PlayerCommandPreProcessListener(UserService userService, PluginConfiguration pluginConfiguration) {
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler()
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) {
            if (this.pluginConfiguration.getNotAllowedCommands().contains(event.getMessage())) {
                event.setCancelled(true);
                player.sendMessage(ChatHelper.fixColor(this.pluginConfiguration.getNotAllowedCommandPerform()));
                return;
            }
            this.userService.findUser(player.getUniqueId())
                    .filter(user -> user.getUserFight().has())
                    .ifPresent(user -> {
                        if (!this.pluginConfiguration.getAllowedCommandsFight().contains(event.getMessage())) {
                            event.setCancelled(true);
                            player.sendMessage(ChatHelper.fixColor(this.pluginConfiguration.getDenyByFight()));
                        }
                    });
        }
    }


}
