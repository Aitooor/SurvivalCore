package online.nasgar.survival.command.coins;

import online.nasgar.survival.command.coins.arguments.AddArgument;
import online.nasgar.survival.command.coins.arguments.RemoveArgument;
import online.nasgar.survival.command.coins.arguments.SetArgument;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.text.BuildText;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CoinsCommand extends Command {

    public CoinsCommand() {
        super("coins");

        this.setAliases(Arrays.asList("economy", "balance"));
        this.setArgumentBase(true);

        this.addArguments(new AddArgument(), new RemoveArgument(), new SetArgument());
    }

    @Override public void onCommand(CommandSender sender, String[] array) {
        if (sender instanceof ConsoleCommandSender){
            ChatUtil.toSender(sender, "&cYou do not have a limited money!");
            return;
        }

        Player player = (Player) sender;

        ChatUtil.toPlayer(player, BuildText.of(player, "&aYou have &e<coins>$"));
    }
}
