package online.nasgar.survivalcore.listeners;

import online.nasgar.survivalcore.chat.ChatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Mansitoh
 * Project: SurvivalCore Date: 16/19/2021 @ 19:06
 * Twitter: @Mansitoh_PY Github: https://github.com/Mansitoh
 * Discord: https://discord.link/Skilled
 * CEO: Skilled | Development
 */
public class HandlerListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        new GodModeListener().onDamage(event);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        new ChatManager().onChat(event);
    }

}
