package pl.wondermc.boxpvp.configuration.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;
import pl.wondermc.boxpvp.kit.Kit;
import pl.wondermc.boxpvp.kit.KitItem;

import java.util.List;

public class KitSerializer implements ObjectSerializer<Kit> {

    @Override
    public boolean supports(Class<? super Kit> type) {
        return Kit.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(Kit kit, SerializationData data) {
        data.add("name", kit.getName());
        data.add("delayTime", kit.getDelayTime());
        data.add("items", kit.getItems());
        data.add("inventoryName", kit.getInventoryName());
        data.add("inventoryMaterial", kit.getInventoryMaterial());
        data.add("inventoryLore", kit.getInventoryLore());
        data.add("inventorySlot", kit.getInventorySlot());


    }

    @Override
    public Kit deserialize(DeserializationData data, GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        String delayTime = data.get("delayTime", String.class);
        List<KitItem> items = data.getAsList("items", KitItem.class);
        String inventoryName = data.get("inventoryName", String.class);
        Material inventoryMaterial = data.get("inventoryMaterial", Material.class);
        List<String> inventoryLore = data.getAsList("inventoryLore", String.class);
        int inventorySlot = data.get("inventorySlot", Integer.class);
        return new Kit(name, delayTime, items,inventoryName, inventoryMaterial, inventoryLore, inventorySlot);

    }
}
