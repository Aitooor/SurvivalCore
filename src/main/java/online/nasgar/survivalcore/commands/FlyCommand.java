package online.nasgar.survivalcore.commands;

import online.nasgar.survivalcore.utils.CC;
import online.nasgar.survivalcore.utils.command.BaseCommand;
import online.nasgar.survivalcore.utils.command.Command;
import online.nasgar.survivalcore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FlyCommand extends BaseCommand {

    @Override @Command(name = "fly", inGameOnly = true, aliases = {"volar"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();


        if(!player.hasPermission("survivalcore.fly")) {
            player.sendMessage(CC.NO_PERMISSIONS);
            return;
        }

        if(args.length < 1) {
            if(!player.getAllowFlight()) {
                player.setAllowFlight(true);
                player.sendMessage(CC.translate("&aYou can now fly"));
                return;
            }

            player.setAllowFlight(false);
            player.sendMessage(CC.translate("&cCan no longer fly"));
            return;
        }

        if(!player.hasPermission("survivalcore.fly.other")) {
            player.sendMessage(CC.NO_PERMISSIONS);
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        if(!target.getAllowFlight()) {
            target.setAllowFlight(true);
            player.sendMessage(CC.translate("&aYou can now fly"));
            return;
        }

        target.setAllowFlight(false);
        player.sendMessage(CC.translate("&cCan no longer fly"));
        return;

    }
}