package online.nasgar.survival.chat;

import online.nasgar.survival.utils.pyrite.packet.PacketContainer;
import online.nasgar.survival.utils.pyrite.packet.PacketListener;
import online.nasgar.survival.utils.text.BuildText;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;

public class ChatPacketListener implements PacketContainer {

    @PacketListener(channels = { "chat" })
    public void onStaff(ChatPacket packet) {

        Bukkit.getOnlinePlayers()
                .forEach(player -> ChatUtil.toPlayer(player, BuildText.of(packet.getPlayer(),
                        "&b[<server>] &f<prefix><player>&f: <message>")
                        .replace("<server>", packet.getServer())
                        .replace("<message>", packet.getServer())));
    }

}
