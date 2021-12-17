package online.nasgar.survival.graves;

import lombok.Data;
import org.bukkit.Location;

import java.util.UUID;

@Data
public class GraveData {

    private UUID owner;
    private Location location;
}
