package online.nasgar.survival.providers;

import me.clip.placeholderapi.PlaceholderAPI;
import me.yushust.message.MessageHandler;
import online.nasgar.survival.Survival;
import online.nasgar.survival.utils.BungeeUtil;
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

        String survival1 = BungeeUtil.getPlayerCount(player,"Survival-1");
        int survival1Int = Integer.parseInt(survival1);
        String survival2 = BungeeUtil.getPlayerCount(player,"Survival-2");
        int survival2Int = Integer.parseInt(survival2);

        int survivalCount =  survival1Int + survival2Int;

        player.setPlayerListHeaderFooter(
                messageHandler.replacing(player, "tab.header","%online%", survivalCount),
                messageHandler.replacing(player, "tab.footer", "%online%", bungeeTotal));

        String playerListNames = "%vault_prefix% %player_name%";
        playerListNames = PlaceholderAPI.setPlaceholders(event.getPlayer(), playerListNames);
        player.setPlayerListName(playerListNames);
    }
}
