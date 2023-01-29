package pl.wondermc.boxpvp.user;

import pl.wondermc.boxpvp.configuration.DailyRewardConfiguration;
import pl.wondermc.boxpvp.configuration.KitConfiguration;
import pl.wondermc.boxpvp.kit.Kit;
import pl.wondermc.boxpvp.reward.DailyReward;
import pl.wondermc.boxpvp.user.sub.UserBar;
import pl.wondermc.boxpvp.user.sub.UserCooldown;
import pl.wondermc.boxpvp.user.sub.UserFight;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class User {

    private final UUID uniqueId;
    private final String nickname;
    private final UserCooldown userCooldown;
    private long joinedAt;
    private long leftAt;
    private boolean newlyCreated;
    private final Map<Integer, Long> spentTimeMap;
    private final Map<Integer, Boolean> dailyRewardMap;
    private final Map<Integer, Long> dailyRewardMissedMap;
    private final Map<String, Long> kitMap;
    private final UserBar userBar;
    private final UserFight userFight;
    private boolean joinedDiscord;
    private boolean collectedDiscordReward;
    private boolean god;
    private boolean vanish;
    private String lastMessage;
    private final Set<String> tpaRequests;
    private final Set<String> ignoredPlayers;
    private boolean socialSpy;


    public User(UUID uniqueId, String nickname, DailyRewardConfiguration dailyRewardConfiguration,
                KitConfiguration kitConfiguration) {
        this.uniqueId = uniqueId;
        this.nickname = nickname;

        this.newlyCreated = true;
        this.spentTimeMap = new ConcurrentHashMap<>();
        this.dailyRewardMap = new ConcurrentHashMap<>();
        this.dailyRewardMissedMap = new ConcurrentHashMap<>();
        this.kitMap = new ConcurrentHashMap<>();
        for (DailyReward dailyReward : dailyRewardConfiguration.getDailyRewardList()) {
            this.dailyRewardMap.put(dailyReward.getDay(), false);
            this.spentTimeMap.put(dailyReward.getDay(), 0L);
        }
        for (Kit kit : kitConfiguration.getKits()) {
            this.kitMap.put(kit.getName(), 0L);
        }
        this.userCooldown = new UserCooldown();
        this.userFight = new UserFight();
        this.userBar = new UserBar();
        this.joinedDiscord = false;
        this.tpaRequests = new HashSet<>();
        this.ignoredPlayers = new HashSet<>();
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getNickname() {
        return nickname;
    }

    public UserCooldown getUserCooldown() {
        return userCooldown;
    }

    public UserFight getUserFight() {
        return userFight;
    }

    public long getJoinedAt() {
        return joinedAt;
    }

    public long getLeftAt() {
        return leftAt;
    }

    public Map<Integer, Long> getSpentTimeMap() {
        return spentTimeMap;
    }

    public boolean isNewlyCreated() {
        return newlyCreated;
    }

    public void setNewlyCreated(boolean newlyCreated) {
        this.newlyCreated = newlyCreated;
    }

    public Map<Integer, Boolean> getDailyRewardMap() {
        return dailyRewardMap;
    }

    public Map<Integer, Long> getDailyRewardMissedMap() {
        return dailyRewardMissedMap;
    }

    public void setGod(boolean god) {
        this.god = god;
    }

    public boolean isVanish() {
        return vanish;
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
    }

    public Map<String, Long> getKitMap() {
        return kitMap;
    }

    public void setJoinedAt(long joinedAt) {
        this.joinedAt = joinedAt;
    }

    public void setLeftAt(long leftAt) {
        this.leftAt = leftAt;
    }

    public boolean isJoinedDiscord() {
        return joinedDiscord;
    }

    public void setJoinedDiscord(boolean joinedDiscord) {
        this.joinedDiscord = joinedDiscord;
    }

    public boolean isGod() {
        return god;
    }

    public UserBar getUserBar() {
        return userBar;
    }

    public boolean isCollectedDiscordReward() {
        return collectedDiscordReward;
    }

    public void setCollectedDiscordReward(boolean collectedDiscordReward) {
        this.collectedDiscordReward = collectedDiscordReward;
    }


    public Set<String> getIgnoredPlayers() {
        return ignoredPlayers;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Set<String> getTpaRequests() {
        return tpaRequests;
    }

    public boolean isSocialSpy() {
        return socialSpy;
    }

    public void setSocialSpy(boolean socialSpy) {
        this.socialSpy = socialSpy;
    }
}
