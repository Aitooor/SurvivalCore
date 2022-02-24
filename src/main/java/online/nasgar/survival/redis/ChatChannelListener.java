package online.nasgar.survival.redis;


import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.redis.channel.Channel;
import net.cosmogrp.storage.redis.channel.ChannelListener;
import online.nasgar.survival.redis.data.MessageData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChatChannelListener implements ChannelListener<MessageData> {

    public static final String CHANNEL_NAME = "survival_core_chat";

    private final MessageHandler messageHandler;

    public ChatChannelListener(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void listen(Channel<MessageData> channel, String server, MessageData messageData) {
        Player player = Bukkit.getPlayer((UUID) messageData.getValue("_id"));

        if (player == null) {
            throw new IllegalArgumentException("Not found player id in message data");
        }

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            messageHandler.sendReplacing(onlinePlayer, "chat.format",
                    "%player_name%", player.getName(),
                    "%message%", messageData.getValue("message")
            );
        });
    }
}
