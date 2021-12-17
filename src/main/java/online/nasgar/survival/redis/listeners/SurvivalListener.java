package online.nasgar.survival.redis.listeners;

import online.nasgar.survival.redis.packet.handler.IncomingPacketHandler;
import online.nasgar.survival.redis.packet.listener.PacketListener;
import online.nasgar.survival.redis.packets.ChatPacket;
import online.nasgar.survival.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SurvivalListener implements PacketListener {

    @IncomingPacketHandler
    public void onChat(ChatPacket packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(CC.translate(packet.getMessage()));
        }
    }
}
