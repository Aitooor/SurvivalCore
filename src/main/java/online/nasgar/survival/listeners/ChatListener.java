package online.nasgar.survival.listeners;

import net.cosmogrp.storage.redis.connection.Redis;
import online.nasgar.survival.redis.ChatChannelListener;
import online.nasgar.survival.redis.data.MessageData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final Redis redis;

    public ChatListener(Redis redis) {
        this.redis = redis;
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        redis.getMessenger().getChannel(ChatChannelListener.CHANNEL_NAME, MessageData.class)
                .sendMessage(
                        new MessageData()
                                .addValue("_id", player.getUniqueId())
                                .addValue("message", event.getMessage())
                );

        event.setCancelled(true);
    }
}
