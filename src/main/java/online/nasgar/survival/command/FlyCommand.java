package online.nasgar.survival.command;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FlyCommand extends Command {

    public FlyCommand() {
        super("fly");

        this.setPermission("fly.command");
        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (array.length == 0){
            if (!player.getAllowFlight()){
                player.setAllowFlight(true);

                ChatUtil.toPlayer(player, "&aFly enabled");
            } else {
                player.setAllowFlight(false);

                ChatUtil.toPlayer(player, "&cFly disabled");
            }
            return;
        }

        if (!player.hasPermission("fly.others.command")) {
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        if (!target.getAllowFlight()) {
            target.setAllowFlight(true);

            ChatUtil.toPlayer(player, "&aFly for &e" + array[0] + " &aenabled!");
            ChatUtil.toPlayer(target, "&aFly enabled by &e"+ player.getName());
        } else {
            target.setAllowFlight(false);

            ChatUtil.toPlayer(player, "&cFly for &e" + array[0] + " &cdisabled!");
            ChatUtil.toPlayer(target, "&cFly disabled by &e"+ player.getName());
        }
    }

}
