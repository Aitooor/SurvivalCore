package online.nasgar.survival.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import online.nasgar.survival.utils.pyrite.packet.Packet;
import org.bukkit.entity.Player;

@Getter @Setter @AllArgsConstructor
public class ChatPacket extends Packet {

    private Player player;
    private String message, server;

}
