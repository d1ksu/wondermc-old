package pl.wondermc.boxpvp.listener.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.wondermc.api.helper.TimeHelper;
import pl.wondermc.boxpvp.BukkitMain;
import pl.wondermc.boxpvp.configuration.DailyRewardConfiguration;
import pl.wondermc.boxpvp.configuration.KitConfiguration;
import pl.wondermc.boxpvp.configuration.PluginConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.helper.ItemHelper;
import pl.wondermc.boxpvp.kit.Kit;
import pl.wondermc.boxpvp.kit.KitItem;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;

import java.util.*;

public class PlayerJoinListener implements Listener {

    private final PluginConfiguration pluginConfiguration;
    private final KitConfiguration kitConfiguration;
    private final UserService userService;
    private final DailyRewardConfiguration dailyRewardConfiguration;

    public PlayerJoinListener(PluginConfiguration pluginConfiguration, KitConfiguration kitConfiguration,
                              UserService userService, DailyRewardConfiguration dailyRewardConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
        this.kitConfiguration = kitConfiguration;
        this.userService = userService;
        this.dailyRewardConfiguration = dailyRewardConfiguration;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        Optional<User> optionalUser = userService.findUser(player.getUniqueId());
        if (optionalUser.isEmpty()) {
            return;
        }
        User user = optionalUser.get();
        user.setJoinedAt(System.currentTimeMillis());
        for (String message : this.pluginConfiguration.getPlayerGlobalJoinMessage()) {
            event.setJoinMessage(ChatHelper.fixColor(message.replace("{PLAYER}", player.getName())));
        }
        for (String message : this.pluginConfiguration.getPlayerJoinMessage()) {
            player.sendMessage(ChatHelper.fixColor(message).replace("{PLAYER}", player.getName()));
        }
        if(user.isNewlyCreated()){
            for(Kit kit : this.kitConfiguration.getKits()){
                if(kit.getName().equalsIgnoreCase( this.kitConfiguration.getNewUserKit())){
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
            user.setNewlyCreated(false);
        }
        Date currentDate = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar. getInstance();
        calendar.setTime(currentDate);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        user.getDailyRewardMap().forEach((day, status) -> {
            if(day < dayOfMonth && !status) {
                if(user.getSpentTimeMap().get(day) < TimeHelper.timeFromString(this.dailyRewardConfiguration.getRequiredSpentTime())){
                    user.getDailyRewardMissedMap().put(day, user.getSpentTimeMap().get(day));
                }
            }
        });
        if (user.isVanish()) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission("wondermc.admin.vanish")) {
                    onlinePlayer.hidePlayer(BukkitMain.getInstance(), player);
                }
            }
        }


    }
}
