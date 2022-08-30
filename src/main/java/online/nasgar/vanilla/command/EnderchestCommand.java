package online.nasgar.vanilla.command;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class EnderchestCommand extends Command {

    private final MessageHandler messageHandler;

    public EnderchestCommand(MessageHandler messageHandler) {
        super("enderchest", messageHandler);
        this.messageHandler = messageHandler;

        this.setAliases(Arrays.asList("ender", "enderchest", "cofreender"));

        this.setPermission("survivalcore.enderchest");

        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {

        if (array.length < 1) {
            player.openInventory(player.getEnderChest());
            messageHandler.send(player, "enderchest.you");
            return;
        }

        if (!player.hasPermission("survivalcore.enderchest.others")){
            return;
        }


        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        player.openInventory(target.getEnderChest());
        messageHandler.sendReplacing(player, "enderchest.target.you", "%target_name%", array[0]);
        messageHandler.sendReplacing(target, "enderchest.target.other", "%staff_name%", player.getName());
    }
}
