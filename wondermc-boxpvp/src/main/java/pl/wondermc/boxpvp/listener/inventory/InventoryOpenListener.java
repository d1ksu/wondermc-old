package pl.wondermc.boxpvp.listener.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.helper.ItemHelper;
import pl.wondermc.boxpvp.user.UserService;

import java.util.List;

public class InventoryOpenListener implements Listener {

    private final PluginConfiguration pluginConfiguration;
    private final UserService userService;

    public InventoryOpenListener(PluginConfiguration pluginConfiguration, UserService userService){
        this.pluginConfiguration = pluginConfiguration;
        this.userService = userService;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryOpen(InventoryOpenEvent event){
        if(event.isCancelled()) return;
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        InventoryType inventoryType = inventory.getType();
        if(inventoryType == InventoryType.ANVIL){
            event.setCancelled(true);
            final List<Material> acceptableItems = this.pluginConfiguration.getAnvilAcceptableItems();
            for(Material material : acceptableItems){
                if(player.getInventory().getItemInMainHand().getType().equals(material)){
                    player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                            Component.text(ChatHelper.fixColor(this.pluginConfiguration.getAnvilWrongItemDeny()))));
                    return;
                }
            }
            ItemStack playerItem = player.getInventory().getItemInMainHand();
            if (playerItem.getItemMeta() == null) {
                return;
            }
            ItemMeta playerItemMeta = playerItem.getItemMeta();
            if (!(playerItemMeta instanceof Repairable) || !(playerItemMeta instanceof Damageable damageable) || damageable.getDamage() == 0) {
                player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                        Component.text(ChatHelper.fixColor(this.pluginConfiguration.getAnvilRepairedItemDeny()))));
                return;
            }
            final List<ItemStack> itemStackCostList = this.pluginConfiguration.getAnvilRepairCostItem();
            for(ItemStack itemStack : itemStackCostList){
                if(ItemHelper.hasItem(player, itemStack)){
                    ItemHelper.removeItem(player, itemStack, itemStack.getAmount());
                    damageable.setDamage(0);
                    playerItem.setItemMeta(playerItemMeta);
                    player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                            Component.text(ChatHelper.fixColor(this.pluginConfiguration.getAnvilRepairSuccess()))));
                } else {
                    player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                            Component.text(ChatHelper.fixColor(this.pluginConfiguration.getAnvilRepairDeny()))));
                }
            }
        }
    }

    @EventHandler()
    public void onInventoryOpen1(InventoryOpenEvent event){
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        InventoryType inventoryType = inventory.getType();
        if(!player.isOp()){
            this.userService.findUser(player.getUniqueId())
                    .filter(user -> user.getUserFight().has())
                    .ifPresent(user -> {
                        if(!this.pluginConfiguration.getAllowedInventoriesFight().contains(inventoryType)){
                            player.sendMessage(ChatHelper.fixColor(this.pluginConfiguration.getDenyByFight()));
                            event.setCancelled(true);
                        }
                    });
        }
    }




}
