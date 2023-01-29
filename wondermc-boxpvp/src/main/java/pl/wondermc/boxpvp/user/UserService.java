package pl.wondermc.boxpvp.user;

import pl.wondermc.boxpvp.configuration.DailyRewardConfiguration;
import pl.wondermc.boxpvp.configuration.KitConfiguration;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {

    private final Map<UUID, User> userMap;
    private final DailyRewardConfiguration dailyRewardConfiguration;
    private final KitConfiguration kitConfiguration;

    public UserService(DailyRewardConfiguration dailyRewardConfiguration, KitConfiguration kitConfiguration){
        this.dailyRewardConfiguration = dailyRewardConfiguration;
        this.kitConfiguration = kitConfiguration;
        this.userMap = new ConcurrentHashMap<>();
    }

    public User create(UUID uniqueId, String nickname){
        User user = new User(uniqueId, nickname, dailyRewardConfiguration, kitConfiguration);
        this.userMap.put(uniqueId, user);
        return user;
    }

    public Optional<User> findUser(UUID uniqueId){
        return Optional.ofNullable(this.userMap.get(uniqueId));
    }

    public Optional<User> findUser(String nickname){
        return this.userMap
                .values()
                .stream()
                .filter(user -> user.getNickname().equalsIgnoreCase(nickname))
                .findFirst();
    }

    public Map<UUID, User> getUserMap() {
        return userMap;
    }
}
