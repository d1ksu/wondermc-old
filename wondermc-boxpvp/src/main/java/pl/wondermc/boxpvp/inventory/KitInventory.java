package pl.wondermc.boxpvp.inventory;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.wondermc.api.helper.TimeHelper;
import pl.wondermc.boxpvp.configuration.KitConfiguration;
import pl.wondermc.boxpvp.database.DatabaseProvider;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.helper.ItemHelper;
import pl.wondermc.boxpvp.kit.Kit;
import pl.wondermc.boxpvp.kit.KitItem;
import pl.wondermc.boxpvp.user.User;

import java.util.ArrayList;
import java.util.List;

public class KitInventory {

    private final KitConfiguration kitConfiguration;
    private final DatabaseProvider databaseProvider;

    public KitInventory(KitConfiguration kitConfiguration, DatabaseProvider databaseProvider){
        this.kitConfiguration = kitConfiguration;
        this.databaseProvider = databaseProvider;

    }

    public void open(Player player, User user){
        Gui gui = Gui.gui()
                .rows(this.kitConfiguration.getInventoryRows())
                .title(Component.text(ChatHelper.fixColor(this.kitConfiguration.getInventoryName())))
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

        for(Kit kit : this.kitConfiguration.getKits()){
            List<Component> components = new ArrayList<>();
            kit.getInventoryLore().forEach(text -> components.add(Component.text(ChatHelper.fixColor(text))));
            GuiItem guiItem = ItemBuilder.from(kit.getInventoryMaterial())
                    .name(Component.text(ChatHelper.fixColor(kit.getInventoryName())))
                    .lore(components)
                    .asGuiItem(event -> {
                        this.openKit(player, user, kit);
                    });

            gui.setItem(kit.getInventorySlot(), guiItem);
        }


        gui.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });

        gui.open(player);
    }

    private void openKit(Player player, User user, Kit kit){
        Gui gui = Gui.gui()
                .rows(this.kitConfiguration.getKitInventoryRows())
                .title(Component.text(ChatHelper.fixColor(this.kitConfiguration.getKitInventoryName()
                        .replace("{KIT}", kit.getName()))))
                .create();
        int[] pinkGlassSlots = {0,2,4,6,8,18,26, 36 , 38, 40, 42};
        int[] whiteGlassSlots = {1,3,5,7,9,17, 35, 27, 37, 39, 41, 43};
        GuiItem whiteGlass = ItemBuilder.from(Material.WHITE_STAINED_GLASS_PANE).asGuiItem();
        GuiItem pinkGlass = ItemBuilder.from(Material.MAGENTA_STAINED_GLASS_PANE).asGuiItem();
        for(int i : pinkGlassSlots){
            gui.setItem(i, pinkGlass);
        }
        for(int i : whiteGlassSlots){
            gui.setItem(i, whiteGlass);
        }
        for(KitItem kitItem: kit.getItems()){
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
            GuiItem guiItem = ItemBuilder.from(item)
                    .asGuiItem();
            gui.addItem(guiItem);
        }

        GuiItem goBackItem = ItemBuilder.from(this.kitConfiguration.getGoBackItemMaterial())
                .name(Component.text(ChatHelper.fixColor(this.kitConfiguration.getGoBackItemInventoryName())))
                .asGuiItem(event -> this.open(player, user));
        gui.setItem(this.kitConfiguration.getGoBackItemSlot(), goBackItem);

        long kitTime = user.getKitMap().get(kit.getName());
        List<Component> canCollectLore = new ArrayList<>();
        this.kitConfiguration.getCanCollectLore().forEach(text ->{
            canCollectLore.add(Component.text(ChatHelper.fixColor(text)));
        });
        List<Component> cannotCollectLore = new ArrayList<>();
        this.kitConfiguration.getCannotCollectLore().forEach(text ->{
            cannotCollectLore.add(Component.text(ChatHelper.fixColor(text
                    .replace("{TIME}", TimeHelper.timeToString(kitTime - System.currentTimeMillis())))));
        });

        GuiItem collectItem = ItemBuilder.from((kitTime > System.currentTimeMillis()) ?
                        this.kitConfiguration.getCannotCollectMaterial() : this.kitConfiguration.getCanCollectMaterial())
                .name((kitTime > System.currentTimeMillis()) ?
                        Component.text(ChatHelper.fixColor(this.kitConfiguration.getCannotCollectItemName()))
                        : Component.text(ChatHelper.fixColor(this.kitConfiguration.getCanCollectItemName())))
                .lore((kitTime > System.currentTimeMillis())
                        ? cannotCollectLore : canCollectLore)
                .asGuiItem(event -> {
                    if(kitTime > System.currentTimeMillis()){
                        player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                                Component.text(ChatHelper.fixColor(this.kitConfiguration.getCannotCollectTitle()
                                        .replace("{TIME}", TimeHelper.timeToString(kitTime - System.currentTimeMillis()))))));
                        return;
                    }
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
                    user.getKitMap().put(kit.getName(), System.currentTimeMillis() +
                            TimeHelper.timeFromString(kit.getDelayTime()));
                    player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                            Component.text(ChatHelper.fixColor(this.kitConfiguration.getCollectTitle()))));
                    player.closeInventory();
                    this.databaseProvider.saveUser(user);
                });

        gui.setItem(this.kitConfiguration.getCollectItemSlot(), collectItem);
        gui.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });
        gui.open(player);
    }
}
