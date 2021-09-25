package online.nasgar.survival.command.coins.arguments;

import online.nasgar.survival.Survival;
import online.nasgar.survival.command.management.Argument;
import online.nasgar.survival.utils.StringUtils;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddArgument extends Argument {

    public AddArgument() {
        super("add");

        this.setPermission("coins.add.command");
    }

    @Override public void onArgument(CommandSender sender, String[] array) {
        if (array.length < 1){
            ChatUtil.toSender(sender, "&cUsage: /coins add <player> <amount>");
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        if (!StringUtils.isInteger(array[1])){
            ChatUtil.toSender(sender, "&e" + array[1] + " &cis a invalid number!");
            return;
        }

        int amount = Integer.parseInt(array[1]);

        Survival.getInstance().getPlayerDataManager().get(target.getUniqueId()).addCoins(amount);
        ChatUtil.toSender(sender, "&aSuccessfully added to &e" + array[0] + " &aa amount of &e" + amount + "$&a!");
        ChatUtil.toPlayer(target, "&e" + sender.getName() + " &ahas added &e" + amount + "$ &afor you!");
    }
}
