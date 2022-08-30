package online.nasgar.vanilla.command;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HealCommand extends Command {

    private final MessageHandler messageHandler;

    public HealCommand(MessageHandler messageHandler) {
        super("heal", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("survivalcore.heal");

        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] array) {
        if (player.getHealth() == 20){
            messageHandler.send(player, "heal.max.player");
            return;
        }

        if (array.length < 1) {
            player.setHealth(20);
            messageHandler.send(player, "heal.restored.player");
            return;
        }

        if (!player.hasPermission("survivalcore.heal.others")){
            return;
        }


        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        if (target.getHealth() == 20){
            messageHandler.sendReplacing(player, "heal.max.target", "%target_name%", array[0]);
            return;
        }

        target.setHealth(20);

        messageHandler.sendReplacing(player, "heal.restored.target.you", "%target_name%", array[0]);
        messageHandler.sendReplacing(target, "heal.restored.target.other", "%staff_name%", player.getName());
    }
}
