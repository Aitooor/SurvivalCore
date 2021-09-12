package online.nasgar.survivalcore.commands;

import online.nasgar.survivalcore.utils.CC;
import online.nasgar.survivalcore.utils.command.BaseCommand;
import online.nasgar.survivalcore.utils.command.Command;
import online.nasgar.survivalcore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FeedCommand extends BaseCommand {

    @Override @Command(name = "feed", permission = "survivalcore.feed", inGameOnly = true, aliases = {"comida"})
    public void onCommand(CommandArgs command) {

        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if(args.length < 1) { // Solo pone /feed, por lo cual solo a ti se te llena la comida
            player.setFoodLevel(20);
            player.sendMessage(CC.translate("&aYour food has been restored"));
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            player.sendMessage(CC.translate("&cThe player is offline"));
            return;
        }

        target.setFoodLevel(20);
        player.sendMessage(CC.translate("&aThe player &7" + target.getName() + " &aalready has his whole feed"));
    }
}
