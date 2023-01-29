package pl.wondermc.boxpvp.configuration.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;
import pl.wondermc.boxpvp.item.SellItem;
import pl.wondermc.boxpvp.reward.DailyReward;

import java.util.List;

public class SellItemSerializer implements ObjectSerializer<SellItem> {
    @Override
    public boolean supports(Class<? super SellItem> type) {
        return SellItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(SellItem sellItem, SerializationData data) {
        data.add("material", sellItem.getMaterial());
        data.add("buyAmount", sellItem.getBuyAmount());
        data.add("sellAmount", sellItem.getSellAmount());

    }

    @Override
    public SellItem deserialize(DeserializationData data, GenericsDeclaration generics) {
        Material material = data.get("material", Material.class);
        int buyAmount = data.get("buyAmount", Integer.class);
        int sellAmount = data.get("sellAmount", Integer.class);
        return new SellItem(material, buyAmount, sellAmount);

    }
}
