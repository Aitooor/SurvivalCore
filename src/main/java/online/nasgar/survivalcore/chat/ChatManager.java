package online.nasgar.survivalcore.chat;

import online.nasgar.survivalcore.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Mansitoh
 * Project: SurvivalCore Date: 16/19/2021 @ 19:08
 * Twitter: @Mansitoh_PY Github: https://github.com/Mansitoh
 * Discord: https://discord.link/Skilled
 * CEO: Skilled | Development
 */
public class ChatManager {

    public void onChat(AsyncPlayerChatEvent event) {
        String msg = event.getMessage();
        Player p = event.getPlayer();
        event.setCancelled(true);
        event.getRecipients().add(event.getPlayer());
        //LUEGO SE IMPLEMENTARÁ OTRO SISTEMA DE CHAT POR ESO SE HACE CON LOS RECIPIENTS Y NO SE CAMBIA EL FORMAT
        String chatformat = "&f%player_name%: &7%msg%";
        for (Player players : event.getRecipients()) {
            players.sendMessage(CC.chat(chatformat.replace("%player_name%", p.getName()).replace("%msg%", msg)));
        }
    }
}
