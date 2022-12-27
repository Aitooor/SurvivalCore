package online.nasgar.survival.services.redis.data;

public class MessageData {

    public MessageData(String content) {
        this.content = content;
    }

    private final String content;

    public String getContent() {
        return content;
    }
}
