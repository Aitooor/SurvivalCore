package online.nasgar.vanilla.command;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FlyCommand extends Command {

    private final MessageHandler messageHandler;

    public FlyCommand(MessageHandler messageHandler) {
        super("fly", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("survivalcore.fly");
        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (array.length == 0){
            if (!player.getAllowFlight()){
                player.setAllowFlight(true);

                messageHandler.send(player, "fly.enabled.player");
            } else {
                player.setAllowFlight(false);

                messageHandler.send(player, "fly.disabled.player");
            }
            return;
        }

        if (!player.hasPermission("survivalcore.fly.others")) {
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        if (!target.getAllowFlight()) {
            target.setAllowFlight(true);

            messageHandler.sendReplacing(player, "fly.enabled.target.you", "%target_name%", array[0]);

            messageHandler.sendReplacing(target, "fly.enabled.target.other", "%staff_name%", player.getName());
        } else {
            target.setAllowFlight(false);

            messageHandler.sendReplacing(player, "fly.disabled.target.you", "%target_name%", array[0]);

            messageHandler.sendReplacing(target, "fly.disabled.target.other", "%staff_name%", player.getName());
        }
    }

}
