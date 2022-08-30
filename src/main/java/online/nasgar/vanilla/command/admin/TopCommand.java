package online.nasgar.vanilla.command.admin;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import online.nasgar.vanilla.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TopCommand extends Command {

    private final MessageHandler messageHandler;

    public TopCommand(MessageHandler messageHandler) {
        super("top", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("survivalcore.top");
        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        Location location = player.getLocation();

        if (array.length == 0){
            messageHandler.send(player, "top.player");
            player.teleport(LocationUtil.getHighestLocation(location));
            return;
        }

        if (!player.hasPermission("survivalcore.top.others")) {
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        messageHandler.sendReplacing(player, "top.target.you", "%target_name%", array[0]);
        messageHandler.sendReplacing(target, "top.target.other", "%staff_name%", player.getName());
        target.teleport(LocationUtil.getHighestLocation(location));
    }

}
