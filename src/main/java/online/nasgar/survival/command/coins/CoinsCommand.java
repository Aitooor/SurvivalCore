package online.nasgar.survival.command.coins;

import net.cosmogrp.storage.ModelService;
import online.nasgar.survival.command.coins.arguments.AddArgument;
import online.nasgar.survival.command.coins.arguments.RemoveArgument;
import online.nasgar.survival.command.coins.arguments.SetArgument;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.text.BuildText;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CoinsCommand extends Command {

    private final ModelService<PlayerData> playerCacheModelService;

    public CoinsCommand(ModelService<PlayerData> playerCacheModelService) {
        super("coins");
        this.playerCacheModelService = playerCacheModelService;

        this.setAliases(Arrays.asList("economy", "balance"));
        this.setArgumentBase(true);

        this.addArguments(new AddArgument(this.playerCacheModelService), new RemoveArgument(playerCacheModelService), new SetArgument(playerCacheModelService));
    }

    @Override public void onCommand(CommandSender sender, String[] array) {
        if (sender instanceof ConsoleCommandSender){
            ChatUtil.toSender(sender, "&cYou do not have a limited money!");
            return;
        }

        Player player = (Player) sender;

        ChatUtil.toPlayer(player, new BuildText(playerCacheModelService).of(player, "&aYou have &e<coins>$"));
    }
}
