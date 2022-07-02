package online.nasgar.survival.services.redis;

import net.cosmogrp.storage.redis.channel.Channel;
import net.cosmogrp.storage.redis.channel.ChannelListener;
import online.nasgar.survival.Survival;
import online.nasgar.survival.services.chat.ChatService;
import online.nasgar.survival.managers.config.ConfigFile;
import online.nasgar.survival.services.redis.data.MessageData;

public class ChatChannelListener implements ChannelListener<MessageData> {

    static ConfigFile configFile = Survival.getInstance().getConfigFile();

    public static final String CHANNEL_NAME = configFile.getString("redis.channel.chat");

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
