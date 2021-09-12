package online.nasgar.survivalcore.commands;

import online.nasgar.survivalcore.Core;
import online.nasgar.survivalcore.utils.CC;
import online.nasgar.survivalcore.utils.command.BaseCommand;
import online.nasgar.survivalcore.utils.command.Command;
import online.nasgar.survivalcore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class GodCommand extends BaseCommand {

    @Override @Command(name = "god", inGameOnly = true, aliases = {"dios", "godmode"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer(); //Se define player
        String[] args = command.getArgs(); //Se definar los argumentos

        if(!player.hasPermission("survivalcore.god")) { //Si el jugador no tiene permiso para "survivalcore.god"
            player.sendMessage(CC.NO_PERMISSIONS);
            return;
        }

        if(args.length < 1) {
            if(!player.hasMetadata("godmode")) {
                player.setMetadata("godmode", new FixedMetadataValue(Core.getInstance(), true));
                player.sendMessage(CC.translate("&aGod mode enabled"));
                return;
            }

            player.removeMetadata("godmode", Core.getInstance());
            player.sendMessage(CC.translate("&cGod mode disabled"));
            return;
        }

        if(!player.hasPermission("survivalcore.god.other")) {
            player.sendMessage(CC.NO_PERMISSIONS);
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        if(!target.hasMetadata("godmode")) {
            target.setMetadata("godmode", new FixedMetadataValue(Core.getInstance(), true));
            player.sendMessage(CC.translate("&aGod mode enabled"));
            return;
        }

        target.removeMetadata("godmode", Core.getInstance());
        player.sendMessage(CC.translate("&cGod mode disabled"));
        return;

    }
}