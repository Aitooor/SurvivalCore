package online.nasgar.survival.redis.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class MessageData {

    private final String authorId;
    private final String content;
}
