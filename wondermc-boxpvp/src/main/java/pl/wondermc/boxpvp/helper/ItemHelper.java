package pl.wondermc.boxpvp.helper;


import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemHelper {

    private ItemHelper() {
    }

    public static void removeItem(final Player player, final ItemStack firstItemStack,
                                  final int amount) {
        final Function<ItemStack, Boolean> comparatorFunc =
                !firstItemStack.hasItemMeta() ? secondItemStack ->
                        Objects.equals(secondItemStack.getType(), firstItemStack.getType())
                                && secondItemStack.getDurability() == firstItemStack.getDurability()
                        : secondItemStack -> secondItemStack.isSimilar(firstItemStack);
        final PlayerInventory inventory = player.getInventory();
        final ItemStack[] itemStacks = inventory.getContents();
        int removed = amount;
        for (int slot = 0; slot < itemStacks.length; slot++) {
            final ItemStack secondItemStack = itemStacks[slot];
            if (Objects.isNull(secondItemStack) || !comparatorFunc.apply(secondItemStack)) {
                continue;
            }

            int contentAmount = secondItemStack.getAmount();
            if (contentAmount <= removed) {
                inventory.clear(slot);
                removed -= contentAmount;
            } else {
                secondItemStack.setAmount(contentAmount - removed);
                removed = 0;
            }

            if (removed <= 0) {
                break;
            }
        }
    }

    public static void addItem(final Player player, final ItemStack itemStack) {
        addItems(player, Collections.singletonList(itemStack));
    }

    public static void addItems(final Player player, final List<ItemStack> itemStacks) {
        if (!itemStacks.isEmpty()) {
            for (final ItemStack droppedItemStack : player.getInventory()
                    .addItem(itemStacks.toArray(new ItemStack[0])).values()) {
                player.getWorld().dropItem(player.getLocation(), droppedItemStack);
            }
        }
    }

    public static void subtractItem(final Player player, final ItemStack itemStack) {
        if (itemStack.getAmount() > 1) {
            itemStack.setAmount(itemStack.getAmount() - 1);
            return;
        }

        player.getInventory().removeItem(itemStack);
    }

    public static ItemStack parseItem(final String string) {
        final String[] split = string.split(" ");
        final String[] materialSplit = split[1].split(":");
        final Material material = Material.matchMaterial(materialSplit[0]);
        if (Objects.isNull(material)) {
            return new ItemStack(Material.AIR);
        }

        final ItemStack itemStack = new ItemStack(material, Integer.parseInt(split[0], 1),
                (short) (materialSplit.length > 1 ? Integer.parseInt(materialSplit[1]) : 0));
        final ItemMeta itemMeta = itemStack.getItemMeta();
        for (int i = 2; i < split.length; i++) {
            final String[] attributeSplit = split[i].split(":");
            final String[] attributeValue = Arrays.copyOfRange(attributeSplit, 1, attributeSplit.length);
            final String attributeName = attributeSplit[0];

            if (attributeName.equalsIgnoreCase("name")) {
                itemMeta.setDisplayName(
                        ChatHelper.fixColor(StringUtils.replace(String.join(":", attributeValue), "_", " ")));
            }

            if (attributeName.equalsIgnoreCase("lore")) {
                itemMeta.setLore(Arrays.stream(String.join(":", attributeValue).split("#"))
                        .map(s -> StringUtils.replace(ChatHelper.fixColor(s), "_", " "))
                        .collect(Collectors.toList()));
            }

            if (attributeName.equalsIgnoreCase("enchantment")) {
                final Enchantment enchantment = Enchantment.getByName(attributeValue[0]);
                if (Objects.isNull(enchantment)) {
                    continue;
                }

                itemMeta.addEnchant(enchantment, Integer.parseInt(attributeValue[1], 1), true);
            }
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static boolean hasItem(final Player player, final ItemStack firstItemStack,
                                  final int needed) {
        return countItemAmount(player, firstItemStack) >= needed;
    }

    public static boolean hasItem(final Player player, final ItemStack firstItemStack) {
        return countItemAmount(player, firstItemStack) >= firstItemStack.getAmount();
    }
    public static int countItemAmount(final Player player, final ItemStack firstItemStack) {
        final Function<ItemStack, Boolean> comparatorFunc =
                !firstItemStack.hasItemMeta() ? secondItemStack ->
                        Objects.equals(secondItemStack.getType(), firstItemStack.getType())
                                && secondItemStack.getDurability() == firstItemStack.getDurability()
                        : secondItemStack -> secondItemStack.isSimilar(firstItemStack);
        int amount = 0;
        for (final ItemStack secondItemStack : player.getInventory().getContents()) {
            if (secondItemStack != null && comparatorFunc.apply(secondItemStack)) {
                amount += secondItemStack.getAmount();
            }
        }

        return amount;
    }
    public static int getItemAmountInInventory(Player player, ItemStack itemStack) {
        int amount = 0;
        ItemStack[] contents;
        for (int length = (contents = player.getInventory().getContents()).length, i = 0; i < length; ++i) {
            final ItemStack is = contents[i];
            if (is != null && isSimilar(is, itemStack)) {
                amount += is.getAmount();
            }
        }
        return amount;
    }

    public static boolean isSimilar(ItemStack item1, ItemStack item2) {
        if (item2.getType() == item1.getType() && item2.getDurability() == item1.getDurability()) {
            ItemMeta itemMeta1 = item1.getItemMeta();
            ItemMeta itemMeta2 = item2.getItemMeta();
            if (itemMeta1.hasDisplayName() != itemMeta2.hasDisplayName()) {
                return false;
            }
            if (itemMeta1.hasDisplayName()) {
                if (!itemMeta1.getDisplayName().equals(itemMeta2.getDisplayName())) {
                    return false;
                }
            }
            if (itemMeta1.hasLore() != itemMeta2.hasLore()) {
                return false;
            }
            if (itemMeta1.hasLore()) {
                if (itemMeta1.getLore().size() != itemMeta2.getLore().size()) {
                    return false;
                }
                for (int index = 0; index < itemMeta1.getLore().size(); index++) {
                    if (itemMeta1.getLore().get(index).equals(itemMeta2.getLore().get(index))) {
                        return false;
                    }
                }
            }
            if (itemMeta1.hasEnchants() != itemMeta2.hasEnchants()) {
                return false;
            }
            if (itemMeta1.hasEnchants()) {
                if (itemMeta1.getEnchants().size() != itemMeta2.getEnchants().size()) {
                    return false;
                }
                for (Map.Entry<Enchantment, Integer> enchantInfo : itemMeta1.getEnchants().entrySet()) {
                    if (itemMeta1.getEnchantLevel(enchantInfo.getKey()) != itemMeta2.getEnchantLevel(enchantInfo.getKey())) {
                        return false;
                    }
                }
            }
            if (itemMeta1.getItemFlags().size() != itemMeta2.getItemFlags().size()) {
                return false;
            }
            for (ItemFlag flag : itemMeta1.getItemFlags()) {
                if (!itemMeta2.hasItemFlag(flag)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isValid(final ItemStack itemStack) {
        if (Objects.isNull(itemStack)) {
            return false;
        }

        return !itemStack.getType().isBlock() && !itemStack.getType().isFlammable() &&
                !itemStack.getType().isEdible() && !itemStack.getType().isBurnable() &&
                !itemStack.getType().isTransparent() && itemStack.getType() != Material.AIR
                && itemStack.getDurability() > 1;
    }



}
