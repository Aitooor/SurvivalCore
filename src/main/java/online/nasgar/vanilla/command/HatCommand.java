package online.nasgar.vanilla.command;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class HatCommand extends Command {

    private final MessageHandler messageHandler;

    public HatCommand(MessageHandler messageHandler) {
        super("hat", messageHandler);
        this.messageHandler = messageHandler;

        this.setAliases(Arrays.asList("gorro", "hats", "cascos", "casco", "gorros"));

        this.setPermission("survivalcore.hat");

        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        ItemStack x = player.getInventory().getItemInMainHand();
        ItemStack y = player.getInventory().getHelmet();

        if (player.getInventory().getHelmet() != null) {
            messageHandler.send(player, "hat.already-have");
            return;
        }

        if (array.length < 1) {
            if (Objects.equals(String.valueOf(x), "ItemStack{AIR x 0}")) {
                messageHandler.send(player, "hat.no-item");
                return;
            }
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), y);
            player.getInventory().remove(x);
            player.getInventory().setHelmet(x);
            messageHandler.send(player, "hat.update");
            return;
        }

        if (!player.hasPermission("survivalcore.hat.others")){
            return;
        }


        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        if (target.getInventory().getHelmet() != null) {
            messageHandler.sendReplacing(player, "hat.target.already-have", "%target_name%", target.getName());
            return;
        }

        if (Objects.equals(String.valueOf(x), "ItemStack{AIR x 0}")) {
            messageHandler.send(player, "hat.no-item");
            return;
        }
        target.getInventory().setItem(target.getInventory().getHeldItemSlot(), y);
        target.getInventory().remove(x);
        target.getInventory().setHelmet(x);

        messageHandler.sendReplacing(player, "hat.target.you", "%target_name%", target.getName());
        messageHandler.sendReplacing(target, "hat.target.other", "%staff_name%", player.getName());
    }
}
