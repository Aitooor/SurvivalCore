package online.nasgar.survival.auctions;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Data
public class AuctionData {

    private UUID owner;
    private double price;
    private ItemStack stack;

    private long addedAt, duration;
}
