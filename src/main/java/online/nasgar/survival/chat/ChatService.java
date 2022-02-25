package online.nasgar.survival.chat;

import me.yushust.message.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatService {

    private final MessageHandler messageHandler;

    public ChatService(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void sendMessage(Player author, String message) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            messageHandler.sendReplacing(onlinePlayer, "chat.format",
                    "%player_name%", author.getName(),
                    "%message%", message
            );
        });
    }
}
