package online.nasgar.vanilla.services.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import me.yushust.message.MessageHandler;
import org.bukkit.Bukkit;

public class ChatService {

    private final MessageHandler messageHandler;

    public ChatService(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void sendMessage(String playerName, String message) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {

            String rank = "%vault_prefix% ";
            rank = PlaceholderAPI.setPlaceholders(onlinePlayer.getPlayer(), rank);

            messageHandler.sendReplacing(onlinePlayer, "chat.format",
                    "%player_name%", playerName,
                    "%message%", message,
                    "%rank%", rank
            );
        });
    }
}
