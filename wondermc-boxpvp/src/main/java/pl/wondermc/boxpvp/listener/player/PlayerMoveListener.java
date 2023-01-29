package pl.wondermc.boxpvp.listener.player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.user.UserService;

public class PlayerMoveListener implements Listener {

    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;

    public PlayerMoveListener(UserService userService, PluginConfiguration pluginConfiguration) {
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler()
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) {
            this.userService.findUser(player.getUniqueId())
                    .filter(user -> user.getUserFight().has())
                    .ifPresent(user -> {
                        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(event.getTo());
                        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                        RegionQuery query = container.createQuery();
                        ApplicableRegionSet set = query.getApplicableRegions(loc);
                        for (ProtectedRegion protectedRegion : set) {
                            if (protectedRegion.getId().equalsIgnoreCase(this.pluginConfiguration.getSafeZoneRegion())) {
                                Location locationKnockBack = new Location(player.getWorld(),
                                        (protectedRegion.getMinimumPoint().getX() + protectedRegion.getMaximumPoint().getX()) / 2, 80, (protectedRegion.getMinimumPoint().getZ() + protectedRegion.getMaximumPoint().getZ()) / 2);
                                this.knockBackPlayer(player, locationKnockBack);
                                player.sendMessage(ChatHelper.fixColor(this.pluginConfiguration.getSafeZoneInfo()));
                            }
                        }
                    });
        }
    }

    private void knockBackPlayer(Player player, Location location) {
        Location locationVector = player.getLocation().subtract(location);
        double distance = player.getLocation().distance(location);
        if ((1.0 / distance <= 0)) return;
        if (distance <= 0) return;
        player.setVelocity(locationVector.toVector().add(new Vector(0, 0.10, 0)).multiply(6 / distance));
    }
}
