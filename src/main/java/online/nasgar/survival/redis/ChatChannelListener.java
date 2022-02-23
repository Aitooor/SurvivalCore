package online.nasgar.survival.redis;


import net.cosmogrp.storage.redis.channel.Channel;
import net.cosmogrp.storage.redis.channel.ChannelListener;
import online.nasgar.survival.redis.data.MessageData;
import online.nasgar.survival.utils.CC;
import org.bukkit.Bukkit;

public class ChatChannelListener implements ChannelListener<MessageData> {

    public static final String CHANNEL_NAME = "survival_core_chat";

    @Override
    public void listen(Channel<MessageData> channel, String server, MessageData object) {
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(CC.translate(object.getContent())));
    }
}
