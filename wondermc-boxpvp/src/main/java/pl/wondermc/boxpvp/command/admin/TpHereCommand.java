package pl.wondermc.boxpvp.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;

public class TpHereCommand {

    private final CommandConfiguration commandConfiguration;

    public TpHereCommand(CommandConfiguration commandConfiguration){
        this.commandConfiguration = commandConfiguration;
    }


    @Command(name = "tphere", aliases = {"s", "teleporthere"}, permission = "wondermc.admin.teleport", inGameOnly = true)
    public void execute(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();
        if(args.length != 1){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportHereUsage()));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getPlayerIsOffline()));
            return;
        }
        target.teleport(player.getLocation());
        player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportedHerePlayer()
                .replace("{PLAYER}", target.getName())));
        target.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTeleportedHereByPlayer()
                .replace("{PLAYER}", player.getName())));


    }
}
