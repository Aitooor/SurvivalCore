package online.nasgar.survivalcore.commands;

import online.nasgar.survivalcore.utils.CC;
import online.nasgar.survivalcore.utils.command.BaseCommand;
import online.nasgar.survivalcore.utils.command.Command;
import online.nasgar.survivalcore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HealCommand extends BaseCommand {

    @Override @Command(name = "heal", inGameOnly = true, aliases = {"recuperar", "vida", "health"})
    public void onCommand(CommandArgs command) {

        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if(!player.hasPermission("survivalcore.heal")) {
            player.sendMessage(CC.NO_PERMISSIONS);
            return;
        }

        if(args.length < 1) {
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            player.sendMessage(CC.translate("&aYour life has been recovered"));
            return;
        }

        if(!player.hasPermission("survivalcore.heal.other")) {
            player.sendMessage(CC.NO_PERMISSIONS);
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            player.sendMessage(CC.translate("&cPlayer is offline"));
            return;
        }

        target.setFoodLevel(20);
        player.sendMessage(CC.translate("&aThe player &7" + target.getName() + " &aalready has his whole life"));

    }
}
