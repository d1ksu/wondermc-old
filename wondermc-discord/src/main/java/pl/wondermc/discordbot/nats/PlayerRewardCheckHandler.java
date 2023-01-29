package pl.wondermc.discordbot.nats;

import org.pieszku.messaging.api.connection.MessengerConnection;
import org.pieszku.messaging.api.handler.MessengerPacketRequestHandler;
import org.pieszku.messaging.api.handler.type.MessengerPacketHandlerInfo;
import pl.wondermc.api.packet.PlayerCollectedRewardCheckPacket;
import pl.wondermc.api.packet.PlayerCollectedRewardCheckResponsePacket;
import pl.wondermc.discordbot.BotMain;
import pl.wondermc.discordbot.user.RewardCache;
import pl.wondermc.discordbot.user.RewardUser;

public class PlayerRewardCheckHandler implements MessengerPacketRequestHandler {

    private final RewardCache rewardCache = BotMain.getInstance().getRewardCache();

    @MessengerPacketHandlerInfo(listenChannelName = "wondermc_discord", packetClass = PlayerCollectedRewardCheckPacket.class)
    public void handle(PlayerCollectedRewardCheckPacket packet, long callbackId, MessengerConnection connection) {
        String playerName = packet.getPlayerName();
        var rewardUserOptional = rewardCache.findUserByNickName(playerName);
        RewardUser rewardUser;
        if (rewardUserOptional.isEmpty()) {
            rewardUser = rewardCache.add(playerName);
            BotMain.getInstance().getDatabaseProvider().saveUser(rewardUser);
        } else {
            rewardUser = rewardUserOptional.get();
        }
        PlayerCollectedRewardCheckResponsePacket playerRewardResponsePacket = new PlayerCollectedRewardCheckResponsePacket(rewardUser.isCollect());
        playerRewardResponsePacket.setResponse(true);
        playerRewardResponsePacket.setCallbackId(callbackId);
        playerRewardResponsePacket.setDone(true);
        connection.reply(playerRewardResponsePacket);
    }
}
