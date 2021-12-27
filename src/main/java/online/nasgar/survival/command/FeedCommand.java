package online.nasgar.survival.command;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FeedCommand extends Command {

    public FeedCommand() {
        super("feed");

        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (player.getFoodLevel() == 20){
            ChatUtil.toPlayer(player, "&cYour feed is at maximum!");
            return;
        }

        if (array.length < 1) {
            player.setFoodLevel(20);
            ChatUtil.toPlayer(player, "&aYour feed has been restored!");
            return;
        }

        if (!player.hasPermission("feed.others.command")){
            return;
        }


        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        if (target.getFoodLevel() == 20){
            ChatUtil.toPlayer(player, "&e" + array[0] + " &cfeed is at maximum!");
            return;
        }

        target.setFoodLevel(20);
        ChatUtil.toPlayer(player, "&e" + array[0] + " &afeed has been restored!");
        ChatUtil.toPlayer(target, "&aYour feed has been restored by &e" + array[0]);
    }
}
