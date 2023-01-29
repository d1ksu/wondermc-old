package pl.wondermc.boxpvp.kit;

import org.bukkit.Material;

import java.util.List;

public class KitItem {

    private final Material material;
    private final int amount;
    private final String name;
    private final List<String> lore;
    private final List<KitItemEnchant> enchantments;

    public KitItem(Material material, int amount, String name, List<String> lore, List<KitItemEnchant> enchantments) {
        this.material = material;
        this.amount = amount;
        this.name = name;
        this.lore = lore;
        this.enchantments = enchantments;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public List<KitItemEnchant> getEnchantments() {
        return enchantments;
    }
}
