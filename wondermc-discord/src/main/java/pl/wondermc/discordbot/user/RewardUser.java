package pl.wondermc.discordbot.user;

public class RewardUser {

    private final String nickName;
    private boolean collect = false;

    public RewardUser(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
}
