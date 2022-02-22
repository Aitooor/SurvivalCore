package online.nasgar.survival.randomtp;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;
import online.nasgar.survival.Survival;
import online.nasgar.survival.redis.CoreRedisDatabase;
import online.nasgar.survival.redis.packets.RandomTPPacket;
import online.nasgar.survival.utils.BungeeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class RandomTPManager implements Listener {

    @Getter private static RandomTPManager instance;

    private Map<String, Location> toTeleport;

    public RandomTPManager() {
        instance = this;

        this.toTeleport = Maps.newConcurrentMap();

        Bukkit.getPluginManager().registerEvents(this, Survival.getInstance());
    }

    public void teleportToRandomLocation(Player player) {
        player.closeInventory();

        int radius = 500;

        if (player.hasPermission("randomtp.1000")) radius = 1000;
        if (player.hasPermission("randomtp.1500")) radius = 1500;
        if (player.hasPermission("randomtp.2000")) radius = 2000;
        if (player.hasPermission("randomtp.2500")) radius = 2500;
        if (player.hasPermission("randomtp.3000")) radius = 3000;
        if (player.hasPermission("randomtp.3500")) radius = 3500;
        if (player.hasPermission("randomtp.4000")) radius = 4000;

        int x = ThreadLocalRandom.current().nextInt(radius);
        int z = ThreadLocalRandom.current().nextInt(radius);

        Location location = new Location(player.getWorld(), x, player.getWorld().getHighestBlockYAt(x, z), z);

        if (!Survival.getInstance().getConfigFile().getStringList("random_tp_server").contains(Survival.getInstance().getServerId())) {
            List<String> servers = Survival.getInstance().getConfigFile().getStringList("random_tp_server");

            BungeeUtil.sendToServer(player, servers.get(ThreadLocalRandom.current().nextInt(servers.size())));
            CoreRedisDatabase.getInstance().sendPacket(new RandomTPPacket(player.getName(), location));
            return;
        }

        player.teleport(location);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!this.toTeleport.containsKey(player.getName())) return;

        player.teleport(this.toTeleport.remove(player.getName()));
    }
}