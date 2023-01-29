package pl.wondermc.boxpvp.kit;

public class KitItemEnchant {

    private String enchantmentName;
    private int level;

    public KitItemEnchant(String enchantmentName, int level) {
        this.enchantmentName = enchantmentName;
        this.level = level;
    }

    public String getEnchantmentName() {
        return enchantmentName;
    }

    public int getLevel() {
        return level;
    }
}
