package online.nasgar.survival.graves;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;
import online.nasgar.survival.Survival;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class GravesManager implements Listener {

    @Getter private static GravesManager instance;

    public Map<UUID, List<GraveData>> graves;

    public GravesManager() {
        instance = this;

        this.graves = Maps.newConcurrentMap();

        Bukkit.getPluginManager().registerEvents(this, Survival.getInstance());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        List<GraveData> graves = new ArrayList<>(this.graves.getOrDefault(player.getUniqueId(), new ArrayList<>()));

        GraveData graveData = new GraveData();
        graveData.setOwner(player.getUniqueId());
        graveData.setLocation(player.getLocation());
        graves.add(graveData);

        this.graves.put(player.getUniqueId(), graves);

        Location location1Chest = player.getLocation().clone();
        Location location2Chest = player.getLocation().clone().add(1, 0, 0);

        location1Chest.getBlock().setType(Material.CHEST);
        location2Chest.getBlock().setType(Material.CHEST);

        DoubleChest chest = (DoubleChest) location1Chest.getBlock().getState();

        for (int i = 0; i < event.getDrops().size(); i++) {
            chest.getInventory().setItem(i, event.getDrops().get(i));
        }

        event.getDrops().clear();
    }
}
