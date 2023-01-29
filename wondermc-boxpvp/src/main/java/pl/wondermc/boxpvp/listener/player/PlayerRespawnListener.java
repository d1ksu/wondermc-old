package pl.wondermc.boxpvp.listener.player;

import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.wondermc.boxpvp.configuration.KitConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.helper.ItemHelper;
import pl.wondermc.boxpvp.kit.Kit;
import pl.wondermc.boxpvp.kit.KitItem;
import pl.wondermc.boxpvp.user.UserService;
import pl.wondermc.boxpvp.user.sub.UserFight;

import java.util.ArrayList;
import java.util.List;

public class PlayerRespawnListener implements Listener {

    private final KitConfiguration kitConfiguration;
    private final UserService userService;

    public PlayerRespawnListener(KitConfiguration kitConfiguration, UserService userService) {
        this.kitConfiguration = kitConfiguration;
        this.userService = userService;
    }

    @EventHandler()
    public void onPlayerDeath(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        this.userService.findUser(player.getUniqueId())
                .ifPresent(user -> {
                    UserFight userFight = user.getUserFight();
                    if(userFight.has()){
                        userFight.setLastFight(0);
                        userFight.setLastAttacker(null);
                    }
                });

        for(Kit kit : this.kitConfiguration.getKits()){
            if(kit.getName().equalsIgnoreCase(this.kitConfiguration.getNewUserKit())){
                for(KitItem kitItem : kit.getItems()){
                    ItemStack item = new ItemStack(kitItem.getMaterial(), kitItem.getAmount());
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.displayName(Component.text(ChatHelper.fixColor(kitItem.getName())));
                    List<Component> components = new ArrayList<>();
                    kitItem.getLore().forEach(text -> {
                        components.add(Component.text(ChatHelper.fixColor(text)));
                    });
                    itemMeta.lore(components);
                    kitItem.getEnchantments().forEach(enchantment -> {
                        itemMeta.addEnchant(Enchantment.getByName(enchantment.getEnchantmentName().toUpperCase()), enchantment.getLevel(), true);
                    });
                    item.setItemMeta(itemMeta);
                    ItemHelper.addItem(player, item);
                }
            }
        }
    }
}
