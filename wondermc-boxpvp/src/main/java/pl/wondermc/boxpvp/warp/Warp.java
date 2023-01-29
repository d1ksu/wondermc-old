package pl.wondermc.boxpvp.warp;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;


public class Warp {

    private String name;
    private Location location;
    private int teleportTime;
    private String permission;
    private Material inventoryMaterial;
    private String inventoryName;
    private List<String> inventoryLore;
    private int inventorySlot;

    public Warp(String name, Location location, int teleportTime, String permission, Material inventoryMaterial, String inventoryName, List<String> inventoryLore, int inventorySlot) {
        this.name = name;
        this.location = location;
        this.teleportTime = teleportTime;
        this.permission = permission;
        this.inventoryMaterial = inventoryMaterial;
        this.inventoryName = inventoryName;
        this.inventoryLore = inventoryLore;
        this.inventorySlot = inventorySlot;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public int getTeleportTime() {
        return teleportTime;
    }

    public String getPermission() {
        return permission;
    }

    public Material getInventoryMaterial() {
        return inventoryMaterial;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public List<String> getInventoryLore() {
        return inventoryLore;
    }

    public int getInventorySlot() {
        return inventorySlot;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
