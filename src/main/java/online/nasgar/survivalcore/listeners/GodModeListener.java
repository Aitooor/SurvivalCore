package online.nasgar.survivalcore.listeners;

import online.nasgar.survivalcore.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;
import java.util.UUID;

public class GodModeListener {


    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(player.hasMetadata("godmode")) {
                event.setCancelled(true);
            }
        }
    }
}
