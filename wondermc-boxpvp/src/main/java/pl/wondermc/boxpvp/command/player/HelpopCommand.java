package pl.wondermc.boxpvp.command.player;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.pieszku.messaging.nats.NatsMessenger;
import pl.wondermc.api.helper.TimeHelper;
import pl.wondermc.api.packet.PlayerHelpopRequestPacket;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

import java.util.Optional;

public class HelpopCommand {

    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;
    private final NatsMessenger natsMessenger;


    public HelpopCommand(UserService userService, PluginConfiguration pluginConfiguration, NatsMessenger natsMessenger){
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
        this.natsMessenger = natsMessenger;
    }

    @Command(name = "helpop", aliases = {"report", "zglos"}, permission = "wondermc.player", inGameOnly = true)
    public void execute(CommandArgs commandArgs){
        String[] args = commandArgs.getArgs();
        String message = String.join(" ", args);
        Player player = commandArgs.getPlayer();
        if(args.length < 1){
            player.sendMessage(ChatHelper.fixColor(pluginConfiguration.getHelpopUsage()));
            return;
        }
        Optional<User> optionalUser = userService.findUser(player.getUniqueId());
        if(optionalUser.isEmpty()){
            return;
        }
        User user = optionalUser.get();
        if(user.getUserCooldown().hasHelpopCooldown()){
            player.sendMessage(ChatHelper.fixColor(pluginConfiguration.getHelpopCooldownMessage()
                    .replace("{TIME}",
                            TimeHelper.timeToString(user.getUserCooldown().getLastHelpop() - System.currentTimeMillis()))));
            return;
        }
        if(message.contains("@here") || message.contains("@everyone")){
            return;
        }
        user.getUserCooldown().setLastHelpop(System.currentTimeMillis() +
                TimeHelper.timeFromString(pluginConfiguration.getHelpopCooldown()));

        for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
            if (onlinePlayer.hasPermission(pluginConfiguration.getHelpopPermission())){
                onlinePlayer.sendMessage(ChatHelper.fixColor(pluginConfiguration.getHelpopAdminMessage()
                        .replace("{MESSAGE}", message).replace("{PLAYER}", player.getName())));

            }
        }

        player.sendMessage(ChatHelper.fixColor(pluginConfiguration.getHelpopMessageSent()));
        natsMessenger.getMessengerConnection().sendPacket("wondermc_discord",
                new PlayerHelpopRequestPacket(player.getName(), message));
    }
}
