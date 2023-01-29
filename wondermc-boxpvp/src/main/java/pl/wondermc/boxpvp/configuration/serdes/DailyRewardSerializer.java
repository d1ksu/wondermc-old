package pl.wondermc.boxpvp.configuration.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import pl.wondermc.boxpvp.reward.DailyReward;

import java.util.List;

public class DailyRewardSerializer implements ObjectSerializer<DailyReward> {
    @Override
    public boolean supports(Class<? super DailyReward> type) {
        return DailyReward.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(DailyReward dailyReward, SerializationData data) {
        data.add("name", dailyReward.getName());
        data.add("day", dailyReward.getDay());
        data.add("commands", dailyReward.getCommands());

    }

    @Override
    public DailyReward deserialize(DeserializationData data, GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        int day = data.get("day", Integer.class);
        List<String> commands = data.getAsList("commands", String.class);
        return new DailyReward(name, day, commands);

    }
}
