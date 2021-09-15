package online.nasgar.survivalcore.commands;

import online.nasgar.survivalcore.utils.CC;
import online.nasgar.survivalcore.utils.command.BaseCommand;
import online.nasgar.survivalcore.utils.command.Command;
import online.nasgar.survivalcore.utils.command.CommandArgs;
import org.bukkit.entity.Player;


public class RepairCommand extends BaseCommand {
    @Override @Command(name = "repair", inGameOnly = true, aliases = {"reparar"})
    public void onCommand(CommandArgs command) {

        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if(!player.hasPermission("survivalcore.repair")) {
            player.sendMessage(CC.NO_PERMISSIONS);
            return;
        }

        if(args.length < 1) {
            player.getInventory().getItemInHand().setDurability((short) 0);
            player.sendMessage(CC.translate("&aYour item has been fixed"));
        }

    }
}
