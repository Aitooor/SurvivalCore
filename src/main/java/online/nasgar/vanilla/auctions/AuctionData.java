package online.nasgar.vanilla.auctions;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Data
public class AuctionData {

    private UUID id;
    private UUID owner;
    private double price;
    private ItemStack stack;

    private long addedAt, duration;
    private boolean removed;
}
