package online.nasgar.survivalcore.listeners;

import online.nasgar.survivalcore.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;
import java.util.UUID;

public class GodModeListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            List<UUID> godMode = Core.getInstance().getManagers().getGodMode();

            if(godMode.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

    }
}
