package online.nasgar.survival.redis.data;

import java.util.HashMap;
import java.util.Map;

public class MessageData {

    private final Map<String, Object> contentMap = new HashMap<>();

    public Object getValue(String key) {
        return contentMap.get(key);
    }

    public MessageData addValue(String key, Object value) {
        contentMap.put(key, value);

        return this;
    }
}
