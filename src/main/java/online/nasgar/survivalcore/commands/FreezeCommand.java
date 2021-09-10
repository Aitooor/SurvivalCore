package online.nasgar.survivalcore.commands;

import online.nasgar.survivalcore.Core;
import online.nasgar.survivalcore.utils.command.BaseCommand;
import online.nasgar.survivalcore.utils.command.Command;
import online.nasgar.survivalcore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class FreezeCommand extends BaseCommand {

    @Override @Command(name = "freeze", permission = "survivalcore.freeze", inGameOnly = true, aliases = {"congelar", "ss"})
    public void onCommand(CommandArgs command) {

        Player player = command.getPlayer();

        List<UUID> freeze = Core.getInstance().getManagers().getFreeze();

        if(freeze.contains(player.getUniqueId())) {



        }
    }
}
