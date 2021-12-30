package online.nasgar.survival.redis.packets;

import com.google.gson.JsonObject;
import online.nasgar.survival.Survival;
import online.nasgar.survival.redis.packet.Packet;
import online.nasgar.survival.redis.packet.json.JsonChain;
import online.nasgar.survival.utils.LocationUtil;
import org.bukkit.Location;

public class RandomTPPacket implements Packet {

    private String player;
    private Location location;

    @Override
    public String id() {
        return "RANDOM_TP_LOCATION";
    }

    @Override
    public JsonObject serialize() {
        return new JsonChain().addProperty("player", this.player).addProperty("location", LocationUtil.parseLocation(this.location)).get();
    }

    @Override
    public void deserialize(JsonObject object) {
        this.player = object.get("player").getAsString();
        this.location = LocationUtil.convertLocation(object.get("location").getAsString());
    }

    public RandomTPPacket() {}

    public RandomTPPacket(String player, Location location) {
        this.player = player;
        this.location = location;
    }

    public String getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }
}
