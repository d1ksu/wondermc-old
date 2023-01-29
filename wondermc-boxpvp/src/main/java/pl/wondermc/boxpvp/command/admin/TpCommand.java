package pl.wondermc.boxpvp.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;

public class TpCommand {

    private final CommandConfiguration commandConfiguration;

    public TpCommand(CommandConfiguration commandConfiguration){
        this.commandConfiguration = commandConfiguration;
    }


    @Command(name = "teleport", aliases = "tp", permission = "wondermc.admin.teleport", inGameOnly = true)
    public void execute(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();
        if(args.length < 1){
            player.sendMessage(ChatHelper.fixColor(this.commandConfiguration.getTpCommandUse()));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendMessage(this.commandConfiguration.getPlayerIsOffline()
                    .replace("{PLAYER}", args[0]));
            return;
        }
        if(args.length == 1){
            player.teleport(target);
            return;
        }
        if(args.length == 2){
            Player target1 = Bukkit.getPlayer(args[1]);
            if(target1 == null){
                player.sendMessage(this.commandConfiguration.getPlayerIsOffline()
                        .replace("{PLAYER}", args[1]));
                return;
            }
            target.teleport(target1);
        }
    }
}
