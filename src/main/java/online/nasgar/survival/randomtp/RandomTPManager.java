package online.nasgar.survival.randomtp;

import lombok.Data;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

@Data
public class RandomTPManager {

    @Getter private static RandomTPManager instance;

    public RandomTPManager() {
        instance = this;
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

        player.teleport(new Location(player.getWorld(), x, player.getWorld().getHighestBlockYAt(x, z), z));
    }
}
