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

        String bungeeTotal = "%bungee_total%";
        bungeeTotal = PlaceholderAPI.setPlaceholders(player, bungeeTotal);

        String survival = "%pb_pc_Survivals%";
        survival = PlaceholderAPI.setPlaceholders(player, survival);
        int survivalInt = Integer.parseInt(survival);

        player.setPlayerListHeaderFooter(
                messageHandler.replacing(player, "tab.header","%online%", survivalInt),
                messageHandler.replacing(player, "tab.footer", "%online%", bungeeTotal));

        String playerListNames = PlaceholderAPI.setPlaceholders(player, "%vault_prefix%&r %player_name%");
        playerListNames = PlaceholderAPI.setPlaceholders(event.getPlayer(), playerListNames);
        player.setPlayerListName(playerListNames);
    }
}
