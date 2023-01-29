package pl.wondermc.boxpvp.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.BukkitMain;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

import java.util.Optional;

public class VanishCommand {

    private final CommandConfiguration commandConfiguration;
    private final UserService userService;

    public VanishCommand(CommandConfiguration commandConfiguration, UserService userService) {
        this.commandConfiguration = commandConfiguration;
        this.userService = userService;
    }

    @Command(name = "vanish", aliases = "v", permission = "wondermc.admin.vanish", inGameOnly = true)
    public void execute(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        Optional<User> optionalUser = this.userService.findUser(player.getUniqueId());
        if (optionalUser.isEmpty()) {
            return;
        }
        User user = optionalUser.get();
        user.setVanish(!user.isVanish());
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("wondermc.admin.vanish")) {
                if (user.isVanish()) {
                    onlinePlayer.hidePlayer(BukkitMain.getInstance(), player);
                } else {
                    onlinePlayer.showPlayer(BukkitMain.getInstance(), player);
                }
            }

        }
        player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getVanishInfo()
                .replace("{STATUS}", user.isVanish() ? this.commandConfiguration.getEnableStatus() :
                        this.commandConfiguration.getDisableStatus())));


    }
}
