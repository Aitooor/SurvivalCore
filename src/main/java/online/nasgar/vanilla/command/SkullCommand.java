package online.nasgar.vanilla.command;

import me.yushust.message.MessageHandler;
import net.md_5.bungee.api.ChatColor;
import online.nasgar.vanilla.managers.command.Command;
import online.nasgar.vanilla.utils.skull.SkullBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;

public class SkullCommand extends Command {

    private final MessageHandler messageHandler;

    public SkullCommand(MessageHandler messageHandler) {
        super("skull", messageHandler);
        this.messageHandler = messageHandler;

        this.setAliases(Arrays.asList("skull", "cabeza", "cabezas"));

        this.setPermission("survivalcore.skull");

        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {

        PlayerInventory inventory = player.getInventory();

        if (array.length < 1) {
            inventory.addItem(new ItemStack(SkullBuilder.newBuilder()
                    .setOwner(player.getName())
                    .setDisplayName(ChatColor.YELLOW + player.getName())
                    .toItemStack()
            ));
            messageHandler.send(player, "skull.you");
            return;
        }

        if (!player.hasPermission("survivalcore.skull.others")){
            return;
        }


        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        inventory.addItem(new ItemStack(SkullBuilder.newBuilder()
                .setOwner(target.getName())
                .setDisplayName(ChatColor.YELLOW + target.getName())
                .toItemStack()
        ));
        messageHandler.sendReplacing(player, "skull.target", "%target_name%", array[0]);
    }
}
