package online.nasgar.survivalcore.commands;

import online.nasgar.survivalcore.utils.command.BaseCommand;
import online.nasgar.survivalcore.utils.command.Command;
import online.nasgar.survivalcore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class VanishCommand extends BaseCommand {

    @Override @Command(name = "vanish", permission = "", inGameOnly = true, aliases = {"v", "invisible", "invisibilidad"})
    public void onCommand(CommandArgs command) {

        Player player = command.getPlayer();

    }
}
