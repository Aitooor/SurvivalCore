package online.nasgar.survival.redis.packets;

import com.google.gson.JsonObject;
import lombok.Data;
import online.nasgar.survival.redis.packet.Packet;
import online.nasgar.survival.redis.packet.json.JsonChain;

@Data
public class ChatPacket implements Packet {

    private String message;

    @Override
    public String id() {
        return "CHAT_PACKET";
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain().addProperty("message", this.message).get();
    }

    @Override
    public void deserialize(JsonObject object) {
        this.message = object.get("message").getAsString();
    }

    public ChatPacket() {}

    public ChatPacket(String message) {
        this.message = message;
    }
}
