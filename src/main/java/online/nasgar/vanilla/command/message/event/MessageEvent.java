package online.nasgar.vanilla.command.message.event;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public class MessageEvent extends PlayerEvent implements Cancellable {

    private static HandlerList handlerList = new HandlerList();

    private final Player target;

    private final String message;
    private boolean cancelled;

    public MessageEvent(Player player, Player target, String message) {
        super(player);

        this.target = target;
        this.message = message;

        Bukkit.getPluginManager().callEvent(this);
    }

    @Override public boolean isCancelled() {
        return this.cancelled;
    }

    @Override public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
