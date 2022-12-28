package online.nasgar.survival.command.premium;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.managers.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class WorkbenchCommand extends Command {

    private final MessageHandler messageHandler;

    public WorkbenchCommand(MessageHandler messageHandler) {
        super("workdbench", messageHandler);
        this.messageHandler = messageHandler;

        this.setAliases(Arrays.asList("wb", "mesatrabajo", "mesacrafteo"));

        this.setPermission("survivalcore.workbench");

        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {

        if (array.length < 1) {
            player.openWorkbench(player.getLocation(), true);
            messageHandler.send(player, "workbench.you");
            return;
        }

        if (!player.hasPermission("survivalcore.workbench.others")){
            return;
        }


        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        target.openWorkbench(target.getLocation(), true);
        messageHandler.sendReplacing(player, "workbench.target.you", "%target_name%", array[0]);
        messageHandler.sendReplacing(target, "workbench.target.other", "%staff_name%", player.getName());
    }
}
