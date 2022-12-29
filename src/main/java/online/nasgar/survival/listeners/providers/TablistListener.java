package online.nasgar.survival.listeners.providers;

import me.clip.placeholderapi.PlaceholderAPI;
import me.yushust.message.MessageHandler;
import online.nasgar.survival.Survival;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TablistListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        //TODO Need to add update interval
        Player player = event.getPlayer();
        MessageHandler messageHandler = Survival.getInstance().getMessageHandler();

        player.setPlayerListHeaderFooter(
                PlaceholderAPI.setPlaceholders(player, messageHandler.replacing(player, "tab.header")),
                PlaceholderAPI.setPlaceholders(player, messageHandler.replacing(player, "tab.footer"))
        );

        String playerListNames = PlaceholderAPI.setPlaceholders(
                event.getPlayer(),
                PlaceholderAPI.setPlaceholders(player, "%vault_prefix% %player_name%")
        );
        player.setPlayerListName(playerListNames);
    }

}
