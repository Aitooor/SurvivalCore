package online.nasgar.survival.rankup;

import lombok.AllArgsConstructor;
import online.nasgar.survival.playerdata.PlayerDataManager;
import online.nasgar.survival.utils.TaskUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class RankUpListener implements Listener {

    private final PlayerDataManager dataManager;

    @EventHandler public void onPlayerJoin(PlayerJoinEvent event){
        TaskUtil.runTaskAsync(() -> this.dataManager.load(event.getPlayer().getUniqueId()));
    }

    @EventHandler public void onPlayerQuit(PlayerQuitEvent event){
        TaskUtil.runTaskAsync(() -> this.dataManager.save(event.getPlayer().getUniqueId()));
    }

}
