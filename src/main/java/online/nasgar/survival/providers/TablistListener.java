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

        //TODO Change this method to another one using bungeecord messagechannel
        String survival1 = "%bungee_Survival-1%";
        survival1 = PlaceholderAPI.setPlaceholders(player, survival1);
        int survival1Int = Integer.parseInt(survival1);
        String survival2 = "%bungee_Survival-2%";
        survival2 = PlaceholderAPI.setPlaceholders(player, survival2);
        int survival2Int = Integer.parseInt(survival2);

        int survivalCountInt = survival1Int + survival2Int;

        player.setPlayerListHeaderFooter(
                messageHandler.replacing(player, "tab.header","%online%", survivalCountInt),
                messageHandler.replacing(player, "tab.footer", "%online%", bungeeTotal));

        String playerListNames = "%vault_prefix% %player_name%";
        playerListNames = PlaceholderAPI.setPlaceholders(event.getPlayer(), playerListNames);
        player.setPlayerListName(playerListNames);
    }
}
