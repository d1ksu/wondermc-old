package pl.wondermc.boxpvp.configuration.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Location;
import org.bukkit.Material;
import pl.wondermc.api.database.Database;
import pl.wondermc.boxpvp.warp.Warp;

import java.util.List;


public class WarpSerializer implements ObjectSerializer<Warp> {

    @Override
    public boolean supports(Class<? super Warp> type) {
        return Warp.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(Warp warp, SerializationData data) {
        data.add("name", warp.getName());
        data.add("location", warp.getLocation());
        data.add("time", warp.getTeleportTime());
        data.add("permission", warp.getPermission());
        data.add("material", warp.getInventoryMaterial());
        data.add("inventoryName", warp.getInventoryName());
        data.add("lore", warp.getInventoryLore());
        data.add("slot", warp.getInventorySlot());


    }

    @Override
    public Warp deserialize(DeserializationData data, GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        Location location = data.get("location", Location.class);
        int time = data.get("time", Integer.class);
        String permission = data.get("permission", String.class);
        Material material = data.get("material", Material.class);
        String inventoryName = data.get("inventoryName", String.class);
        List<String> lore = data.getAsList("lore", String.class);
        int slot = data.get("slot", Integer.class);
        return new Warp(name, location, time, permission, material,inventoryName, lore, slot);

    }
}
