package online.nasgar.survival.redis.packet.json;

import com.google.gson.JsonObject;

public interface JsonDeserializer<T> {
    T deserialize(JsonObject json);
}
