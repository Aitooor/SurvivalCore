package online.nasgar.survivalcore.commands;

import online.nasgar.survivalcore.utils.CC;
import online.nasgar.survivalcore.utils.command.BaseCommand;
import online.nasgar.survivalcore.utils.command.Command;
import online.nasgar.survivalcore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class FlyCommand extends BaseCommand {

    @Override @Command(name = "fly", permission = "survivalcore.fly", inGameOnly = true, aliases = {"volar"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if(!player.getAllowFlight()) {
            player.setAllowFlight(true);
            player.sendMessage(CC.translate("&aYou can now fly"));
            return;
        }

        player.setAllowFlight(false);
        player.sendMessage(CC.translate("&cCan no longer fly"));
    }
}