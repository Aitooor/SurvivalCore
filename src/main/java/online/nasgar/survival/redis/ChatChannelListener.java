package online.nasgar.survival.redis;


import net.cosmogrp.storage.redis.channel.Channel;
import net.cosmogrp.storage.redis.channel.ChannelListener;
import online.nasgar.survival.chat.ChatService;
import online.nasgar.survival.redis.data.MessageData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChatChannelListener implements ChannelListener<MessageData> {

    public static final String CHANNEL_NAME = "survival_core_chat";

    private final ChatService chatService;

    public ChatChannelListener(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void listen(Channel<MessageData> channel, String server, MessageData messageData) {
        String[] messagePart = messageData.getContent().split(";");

        chatService.sendMessage(messagePart[0], messagePart[1]);
    }
}
