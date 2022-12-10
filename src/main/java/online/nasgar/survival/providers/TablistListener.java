package online.nasgar.survival.providers;

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
        Player player = event.getPlayer();
        MessageHandler messageHandler = Survival.getInstance().getMessageHandler();

        String bungeeTotal = PlaceholderAPI.setPlaceholders(player, "%bungee_total%");

        String survival = PlaceholderAPI.setPlaceholders(player, "%pb_pc_Survivals%");
        int survivalInt = Integer.parseInt(survival);

        player.setPlayerListHeaderFooter(
                messageHandler.replacing(player, "tab.header","%online%", survivalInt),
                messageHandler.replacing(player, "tab.footer", "%online%", bungeeTotal)
        );

        String playerListNames = PlaceholderAPI.setPlaceholders(
                event.getPlayer(),
                PlaceholderAPI.setPlaceholders(player, "%vault_prefix%&r %player_name%")
        );
        player.setPlayerListName(playerListNames);
    }
}
