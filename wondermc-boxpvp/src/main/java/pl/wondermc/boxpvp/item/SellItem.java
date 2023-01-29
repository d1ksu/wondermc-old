package pl.wondermc.boxpvp.item;

import org.bukkit.Material;

public class SellItem {

    private final Material material;
    private final int buyAmount;
    private final int sellAmount; // NAGRODA

    public SellItem(Material material, int buyAmount, int sellAmount) {
        this.material = material;
        this.buyAmount = buyAmount;
        this.sellAmount = sellAmount;
    }

    public Material getMaterial() {
        return material;
    }

    public int getBuyAmount() {
        return buyAmount;
    }

    public int getSellAmount() {
        return sellAmount;
    }
}
