package pl.wondermc.discordbot.nats;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import org.pieszku.messaging.api.connection.MessengerConnection;
import org.pieszku.messaging.api.handler.MessengerPacketRequestHandler;
import org.pieszku.messaging.api.handler.type.MessengerPacketHandlerInfo;
import pl.wondermc.api.packet.PlayerHelpopRequestPacket;
import pl.wondermc.discordbot.BotMain;
import pl.wondermc.discordbot.configuration.BotConfiguration;

public class PlayerHelpopHandler implements MessengerPacketRequestHandler {

    private final BotConfiguration botConfiguration = BotMain.getInstance().getBotConfiguration();
    private final WebhookClient webhookClient = WebhookClient.withUrl(botConfiguration.getWebhookUrl());
    private final WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();


    @MessengerPacketHandlerInfo(listenChannelName = "wondermc_discord", packetClass = PlayerHelpopRequestPacket.class)
    public void handle(PlayerHelpopRequestPacket packet, long callbackId, MessengerConnection connection){
        this.webhookClient.send(this.webhookMessageBuilder
                .setContent(packet.getMessage())
                .setUsername(packet.getPlayerName())
                .setAvatarUrl("https://mc-heads.net/avatar/" + packet.getPlayerName())
                .build());
    }
}
