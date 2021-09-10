package online.nasgar.survivalcore.commands;

import online.nasgar.survivalcore.Core;
import online.nasgar.survivalcore.utils.CC;
import online.nasgar.survivalcore.utils.command.BaseCommand;
import online.nasgar.survivalcore.utils.command.Command;
import online.nasgar.survivalcore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GodCommand extends BaseCommand {

    @Override @Command(name = "god", permission = "survivalcore.god", inGameOnly = true, aliases = {"dios", "godmode"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        List<UUID> godMode = Core.getInstance().getManagers().getGodMode();

        if(!godMode.contains(player.getUniqueId())) {
            godMode.add(player.getUniqueId());
            player.sendMessage(CC.translate("&aGod mode enabled"));
            return;
        }

        godMode.remove(player.getUniqueId());
        player.sendMessage(CC.translate("&cGod mode disabled"));
    }
}