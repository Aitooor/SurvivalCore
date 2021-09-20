package online.nasgar.survival.scoreboard.listener;

import lombok.AllArgsConstructor;
import online.nasgar.survival.scoreboard.NautilusManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

@AllArgsConstructor
public class NautilusListener implements Listener {

    private final NautilusManager nautilusManager;

    @EventHandler public void onPlayerJoin(PlayerJoinEvent event){
        this.nautilusManager.add(event.getPlayer().getUniqueId());
    }

    @EventHandler public void onPlayerQuit(PlayerQuitEvent event){
        this.nautilusManager.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler public void onPluginDisable(PluginDisableEvent event){
        this.nautilusManager.stop();
    }

}
