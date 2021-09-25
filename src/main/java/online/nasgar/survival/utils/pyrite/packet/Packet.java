package online.nasgar.survival.utils.pyrite.packet;

import lombok.Data;

/**
 * Packet
 */
@Data
public class Packet {

    private PacketMetadata metadata = new PacketMetadata(this);

}