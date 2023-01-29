package pl.wondermc.boxpvp.configuration.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;
import pl.wondermc.boxpvp.kit.KitItem;
import pl.wondermc.boxpvp.kit.KitItemEnchant;

import java.util.List;

public class KitItemSerializer implements ObjectSerializer<KitItem> {

    @Override
    public boolean supports(Class<? super KitItem> type) {
        return KitItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(KitItem kitItem, SerializationData data) {
        data.add("material", kitItem.getMaterial());
        data.add("amount", kitItem.getAmount());
        data.add("name", kitItem.getName());
        data.add("lore", kitItem.getLore());
        data.add("enchantments", kitItem.getEnchantments());



    }

    @Override
    public KitItem deserialize(DeserializationData data, GenericsDeclaration generics) {
        Material material = data.get("material", Material.class);
        int amount = data.get("amount", Integer.class);
        String name = data.get("name", String.class);
        List<String> lore = data.getAsList("lore", String.class);
        List<KitItemEnchant> enchants = data.getAsList("enchantments", KitItemEnchant.class);
        return new KitItem(material, amount, name, lore, enchants);

    }

}
