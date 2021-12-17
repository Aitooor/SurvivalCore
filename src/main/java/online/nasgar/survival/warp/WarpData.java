package online.nasgar.survival.warp;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

@Data
public class WarpData {

    private String name;
    private Location location;

    private String permission = "";

    private ItemStack stack;
    private int slot;
}
