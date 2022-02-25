package online.nasgar.survival.redis;

import net.cosmogrp.storage.redis.channel.Channel;
import net.cosmogrp.storage.redis.channel.ChannelListener;
import online.nasgar.survival.randomtp.RandomTPManager;
import online.nasgar.survival.redis.data.MessageData;
import online.nasgar.survival.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RandomTPChannelListener implements ChannelListener<MessageData> {

    public static final String CHANNEL_NAME = "survival_core_random_tp";

    @Override
    public void listen(Channel<MessageData> channel, String server, MessageData messageData) {
        if (!channel.getName().equals(CHANNEL_NAME)) {
            return;
        }

        Player player = Bukkit.getPlayer((UUID) messageData.getValue("_id"));

        if (player == null) {
            throw new IllegalArgumentException("Not found player id in message data");
        }

        Location location = LocationUtil.convertLocation((String) messageData.getValue("location"));

        RandomTPManager.getInstance().getToTeleport().put(player.getName(), location);
    }
}
