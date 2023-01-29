package pl.wondermc.boxpvp.configuration.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import pl.wondermc.boxpvp.kit.KitItemEnchant;

public class KitItemEnchantSerializer implements ObjectSerializer<KitItemEnchant> {

    @Override
    public boolean supports(Class<? super KitItemEnchant> type) {
        return KitItemEnchant.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(KitItemEnchant enchant, SerializationData data) {
        data.add("enchantment", enchant.getEnchantmentName());
        data.add("level", enchant.getLevel());
    }

    @Override
    public KitItemEnchant deserialize(DeserializationData data, GenericsDeclaration generics) {
        String enchantmentName = data.get("enchantment", String.class);
        int level = data.get("level", Integer.class);
        return new KitItemEnchant(enchantmentName, level);

    }
}

