package online.nasgar.survival.listeners;

import net.cosmogrp.storage.redis.connection.Redis;
import online.nasgar.survival.chat.ChatService;
import online.nasgar.survival.redis.ChatChannelListener;
import online.nasgar.survival.redis.data.MessageData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final Redis redis;
    private final ChatService chatService;

    public ChatListener(Redis redis, ChatService chatService) {
        this.redis = redis;
        this.chatService = chatService;
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        redis.getMessenger().getChannel(ChatChannelListener.CHANNEL_NAME, MessageData.class)
                .sendMessage(
                        new MessageData(player.getUniqueId() + ";" + message)
                );


        chatService.sendMessage(player.getName(), message);

        event.setCancelled(true);
    }
}
