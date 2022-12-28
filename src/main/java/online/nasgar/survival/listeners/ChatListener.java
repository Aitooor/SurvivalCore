package online.nasgar.survival.listeners;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import net.cosmogrp.storage.redis.connection.Redis;
import online.nasgar.survival.command.normal.message.event.MessageEvent;
import online.nasgar.survival.managers.playerdata.PlayerData;
import online.nasgar.survival.services.chat.ChatService;
import online.nasgar.survival.services.redis.ChatChannelListener;
import online.nasgar.survival.services.redis.data.MessageData;
import online.nasgar.survival.utils.text.BuildText;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final Redis redis;
    private final ChatService chatService;
    private final CachedRemoteModelService<PlayerData> modelService;
    private final MessageHandler messageHandler;

    public ChatListener(Redis redis, ChatService chatService, MessageHandler messageHandler, CachedRemoteModelService<PlayerData> modelService) {
        this.redis = redis;
        this.chatService = chatService;
        this.modelService = modelService;
        this.messageHandler = messageHandler;
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        redis.getMessenger().getChannel(ChatChannelListener.CHANNEL_NAME, MessageData.class)
                .sendMessage(
                        new MessageData(player.getName() + ";" + message)
                );


        chatService.sendMessage(player.getName(), message);

        event.setCancelled(true);
    }

    @EventHandler
    public void onMessage(MessageEvent event) {
        Player player = event.getPlayer();
        Player target = event.getTarget();
        String message = event.getMessage();

        ChatUtil.toPlayer(player, new BuildText(modelService).of(target, messageHandler.get(player, "message.prefix.to") + message));
        ChatUtil.toPlayer(target, new BuildText(modelService).of(player, messageHandler.get(player, "message.prefix.from") + message));
    }
}
