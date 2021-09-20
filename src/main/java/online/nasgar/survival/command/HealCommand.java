package online.nasgar.survival.command;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HealCommand extends Command {

    public HealCommand() {
        super("heal");

        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (player.getHealth() == 20){
            ChatUtil.toPlayer(player, "&cYour health is at maximum!");
            return;
        }

        player.setHealth(20);
        ChatUtil.toPlayer(player, "&aYour health has been restored!");

        if (!player.hasPermission("heal.others.command")){
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        if (target.getHealth() == 20){
            ChatUtil.toPlayer(player, "&e" + array[0] + " &chealth is at maximum!");
            return;
        }

        target.setHealth(20);
        ChatUtil.toPlayer(player, "&e" + array[0] + " &ahealth has been restored!");
        ChatUtil.toPlayer(target, "&aYour health has been restored by &e" + array[0]);
    }
}
