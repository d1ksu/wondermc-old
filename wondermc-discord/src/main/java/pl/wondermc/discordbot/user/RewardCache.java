package pl.wondermc.discordbot.user;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RewardCache {

    private final Set<RewardUser> rewardCache = new HashSet<>();

    public RewardUser add(String playerName){
        var user = new RewardUser(playerName);
        this.rewardCache.add(user);
        return user;
    }
    public Optional<RewardUser> findUserByNickName(String nickName){
        return this.rewardCache
                .stream()
                .filter(rewardUser -> rewardUser.getNickName().equalsIgnoreCase(nickName))
                .findFirst();
    }

    public Set<RewardUser> getRewardCache() {
        return rewardCache;
    }
}
