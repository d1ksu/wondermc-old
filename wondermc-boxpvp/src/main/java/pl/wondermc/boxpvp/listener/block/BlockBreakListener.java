package pl.wondermc.boxpvp.listener.block;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.helper.ItemHelper;

public class BlockBreakListener implements Listener {

    private final PluginConfiguration pluginConfiguration;

    public BlockBreakListener(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event){
        if(event.isCancelled()) return;
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) return;
        this.pluginConfiguration.getAvoidItems().forEach(item -> {
            if(!event.getBlock().getType().equals(item)){
                event.setCancelled(true);
                ItemStack itemStack = new ItemStack(event.getBlock().getType());
                ItemHelper.addItem(player, itemStack);
                event.getBlock().setType(Material.AIR);
            }
        });


    }
}
