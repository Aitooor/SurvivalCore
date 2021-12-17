package online.nasgar.survival.shop.event;

import lombok.Getter;
import online.nasgar.survival.shop.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public class TransactionEvent extends PlayerEvent {

    private static HandlerList handlerList = new HandlerList();

    private final ShopItem shopItem;

    public TransactionEvent(Player player, ShopItem shopItem) {
        super(player);
        this.shopItem = shopItem;

        Bukkit.getPluginManager().callEvent(this);
    }

    @Override
    public HandlerList getHandlers() {
        return this.handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
