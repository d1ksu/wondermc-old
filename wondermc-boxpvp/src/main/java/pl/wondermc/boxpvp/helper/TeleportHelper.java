package pl.wondermc.boxpvp.helper;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.wondermc.api.helper.TimeHelper;
import pl.wondermc.boxpvp.BukkitMain;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;

public class TeleportHelper {

    public static void teleport(Player player, long time, Location location, CommandConfiguration configuration){
        new BukkitRunnable(){
            final long timeTeleport = time;
            final Location playerLocation = player.getLocation();
            @Override
            public void run() {
                player.sendTitle("", ChatHelper.fixColor(configuration.getTeleportTitle()
                        .replace("{TIME}", TimeHelper.timeToString(timeTeleport - System.currentTimeMillis()))));

                if(timeTeleport <= System.currentTimeMillis()){
                    player.teleport(location);
                    player.sendTitle("", ChatHelper.fixColor(configuration.getTeleportSuccess()));
                    this.cancel();
                    return;
                }
                if(!playerHasMove(playerLocation, player.getLocation())){
                    player.sendTitle("", ChatHelper.fixColor(configuration.getTeleportMove()));
                    this.cancel();
                }
            }
        }.runTaskTimer(BukkitMain.getInstance(), 1L, 20L);
    }
    public static boolean playerHasMove(Location locationPlayer, Location locationActual){
        return locationPlayer.getBlockX() == locationActual.getBlockX() && locationPlayer.getBlockY() == locationActual.getBlockY() && locationPlayer.getBlockZ() == locationActual.getBlockZ();
    }
}
