package pl.wondermc.boxpvp.command.admin;

import org.bukkit.entity.Player;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.WarpConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.warp.Warp;

import java.util.Optional;

public class SetWarpCommand {

    private final WarpConfiguration warpConfiguration;

    public SetWarpCommand(WarpConfiguration warpConfiguration){
        this.warpConfiguration = warpConfiguration;
    }

    @Command(name = "setwarp", aliases = "ustawwarp", permission = "wondermc.admin.setwarp", inGameOnly = true)
    public void execute(CommandArgs commandArgs){
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();
        if(!player.hasPermission(warpConfiguration.getWarpSetPermission())){
            player.sendMessage(ChatHelper.fixColor(warpConfiguration.getNoPermissionToSet()));
            return;
        }
        if(args.length != 1){
            player.sendMessage(ChatHelper.fixColor(warpConfiguration.getSetWarpCommandUsage()));
            return;
        }
        Optional<Warp> optionalWarp = warpConfiguration.findWarp(args[0]);
        if(optionalWarp.isEmpty()){
            player.sendMessage(ChatHelper.fixColor(warpConfiguration.getWarpNotFound()));
            return;
        }
        Warp warp = optionalWarp.get();
        warp.setLocation(player.getLocation());
        player.sendMessage(ChatHelper.fixColor(warpConfiguration.getWarpSuccessfullySet()));
        this.warpConfiguration.save();

    }
}
