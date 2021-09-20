package online.nasgar.survival.transaction.event;

import lombok.Getter;
import online.nasgar.survival.transaction.TransactionPrice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public class TransactionEvent extends PlayerEvent {

    private final HandlerList handlerList = new HandlerList();

    private final TransactionPrice price;

    public TransactionEvent(Player player, TransactionPrice price) {
        super(player);
        this.price = price;

        Bukkit.getPluginManager().callEvent(this);
    }

    @Override public HandlerList getHandlers() {
        return this.handlerList;
    }
}
