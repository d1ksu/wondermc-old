package pl.wondermc.boxpvp.kit;

import org.bukkit.Material;

import java.util.List;

public class Kit {

    private final String name;
    private final String delayTime;
    private final List<KitItem> items;
    private final String inventoryName;
    private final Material inventoryMaterial;
    private final List<String> inventoryLore;
    private final int inventorySlot;

    public Kit(String name, String delayTime, List<KitItem> items, String inventoryName, Material inventoryMaterial, List<String> inventoryLore, int inventorySlot) {
        this.name = name;
        this.delayTime = delayTime;
        this.items = items;
        this.inventoryName = inventoryName;
        this.inventoryMaterial = inventoryMaterial;
        this.inventoryLore = inventoryLore;
        this.inventorySlot = inventorySlot;
    }

    public String getName() {
        return name;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public List<KitItem> getItems() {
        return items;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public Material getInventoryMaterial() {
        return inventoryMaterial;
    }

    public List<String> getInventoryLore() {
        return inventoryLore;
    }

    public int getInventorySlot() {
        return inventorySlot;
    }
}
