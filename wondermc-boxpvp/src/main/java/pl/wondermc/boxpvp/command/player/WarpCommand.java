package pl.wondermc.boxpvp.command.player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.wondermc.api.helper.TimeHelper;
import pl.wondermc.boxpvp.BukkitMain;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.WarpConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.inventory.WarpInventory;
import pl.wondermc.boxpvp.warp.Warp;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class WarpCommand {

    private final WarpConfiguration warpConfiguration;

    public WarpCommand(WarpConfiguration warpConfiguration) {
        this.warpConfiguration = warpConfiguration;
    }

    @Command(name = "warp",  permission = "wondermc.player",aliases = {"warps", "warpy"}, inGameOnly = true)
    public void execute(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();
        if (args.length < 1) {
            WarpInventory warpInventory = new WarpInventory(warpConfiguration);
            warpInventory.open(player);
            return;
        }
        if (args.length == 1) {
            Optional<Warp> optionalWarp = warpConfiguration.findWarp(args[0]);
            if (optionalWarp.isEmpty()) {
                player.sendMessage(ChatHelper.fixColor(warpConfiguration.getWarpNotFound()));
                return;
            }
            Warp warp = optionalWarp.get();
            if (!player.hasPermission(warp.getPermission())) {
                player.sendMessage(ChatHelper.fixColor(warpConfiguration.getNoAccess()));
                return;
            }
            this.teleport(player, warp);
        } else {
            player.sendMessage(ChatHelper.fixColor(warpConfiguration.getWarpCommandUsage()));
        }

    }

    private void teleport(Player player, Warp warp) {
        new BukkitRunnable() {

            final long time = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(warp.getTeleportTime());
            final Location playerLocation = player.getLocation();

            @Override
            public void run() {
                com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(player.getLocation());
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionQuery query = container.createQuery();
                ApplicableRegionSet set = query.getApplicableRegions(loc);
                for (ProtectedRegion protectedRegion : set) {
                    if (protectedRegion.getId().equalsIgnoreCase(warpConfiguration.getSpawnRegionName())) {
                        player.teleport(warp.getLocation());
                        this.cancel();
                        player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                                Component.text(ChatHelper.fixColor(warpConfiguration.getTeleportSucces()))));
                        return;
                    }
                }

                if (time <= System.currentTimeMillis()) {
                    player.teleport(warp.getLocation());
                    this.cancel();
                    player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                            Component.text(ChatHelper.fixColor(warpConfiguration.getTeleportSucces()))));

                    return;
                }
                if (move(playerLocation, player.getLocation())) {
                    player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                            Component.text(ChatHelper.fixColor(warpConfiguration.getTeleportDeny()))));
                    this.cancel();
                    return;
                }
                player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                        Component.text(ChatHelper.fixColor(warpConfiguration.getDuringTeleport()
                                .replace("{TIME}", TimeHelper.timeToString(time
                                        - System.currentTimeMillis()))))));


            }
        }.runTaskTimer(BukkitMain.getInstance(), 0, 20L);
    }

    private boolean move(Location firstLocation, Location secondLocation) {
        return firstLocation.getBlockX() != secondLocation.getBlockX() || firstLocation.getBlockY()
                != secondLocation.getBlockY() || firstLocation.getBlockZ() != secondLocation.getBlockZ();
    }
}
