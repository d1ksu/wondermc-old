package pl.wondermc.boxpvp.inventory;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.wondermc.api.helper.TimeHelper;
import pl.wondermc.boxpvp.BukkitMain;
import pl.wondermc.boxpvp.configuration.WarpConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.warp.Warp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WarpInventory {

    private final WarpConfiguration warpConfiguration;

    public WarpInventory(WarpConfiguration warpConfiguration) {
        this.warpConfiguration = warpConfiguration;
    }

    public void open(Player player) {
        Gui gui = Gui
                .gui()
                .title(Component.text(ChatHelper.fixColor(warpConfiguration.getInventoryName())))
                .rows(warpConfiguration.getInventoryRows())
                .create();
        int[] pinkGlassSlots = {0,2,4,6,8,18,20,22,24,26};
        int[] whiteGlassSlots = {1,3,5,7,9,17, 19, 21, 23, 25};
        GuiItem whiteGlass = ItemBuilder.from(Material.WHITE_STAINED_GLASS_PANE).asGuiItem();
        GuiItem pinkGlass = ItemBuilder.from(Material.MAGENTA_STAINED_GLASS_PANE).asGuiItem();
        for(int i : pinkGlassSlots){
            gui.setItem(i, pinkGlass);
        }
        for(int i : whiteGlassSlots){
            gui.setItem(i, whiteGlass);
        }
        for (Warp warp : warpConfiguration.getWarpList()) {
            List<Component> guiItemLore = new ArrayList<>();
            warp.getInventoryLore().forEach(text -> {
                guiItemLore.add(Component.text(ChatHelper.fixColor(text)));
            });
            GuiItem guiItem = ItemBuilder.from(warp.getInventoryMaterial())
                    .name(Component.text(ChatHelper.fixColor(warp.getInventoryName())))
                    .lore(guiItemLore)
                    .asGuiItem(event -> {
                        if (player.hasPermission(warp.getPermission())) {
                            player.closeInventory();
                            this.teleport(player, warp);
                        } else {
                            player.sendMessage(ChatHelper.fixColor(warpConfiguration.getNoAccess()));
                        }

                    });
            gui.setItem(warp.getInventorySlot(), guiItem);
        }

        gui.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });
        gui.open(player);
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
                for(ProtectedRegion protectedRegion : set){
                    if(protectedRegion.getId().equalsIgnoreCase(warpConfiguration.getSpawnRegionName())){
                        player.teleport(warp.getLocation());
                        player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                                Component.text(ChatHelper.fixColor(warpConfiguration.getTeleportSucces()))));
                        this.cancel();
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
