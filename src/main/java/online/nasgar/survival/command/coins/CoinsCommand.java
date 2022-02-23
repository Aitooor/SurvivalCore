package online.nasgar.survival.command.coins;

import me.yushust.message.MessageHandler;
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
    private final MessageHandler messageHandler;

    public CoinsCommand(ModelService<PlayerData> playerCacheModelService, MessageHandler messageHandler) {
        super("coins", messageHandler);
        this.playerCacheModelService = playerCacheModelService;
        this.messageHandler = messageHandler;

        this.setAliases(Arrays.asList("economy", "balance"));
        this.setArgumentBase(true);

        this.addArguments(new AddArgument(this.playerCacheModelService, messageHandler), new RemoveArgument(playerCacheModelService, messageHandler), new SetArgument(playerCacheModelService, messageHandler));
    }

    @Override public void onCommand(CommandSender sender, String[] array) {
        if (sender instanceof ConsoleCommandSender){
            messageHandler.send(sender, "coins.limited-console");
            return;
        }

        Player player = (Player) sender;

        ChatUtil.toPlayer(player, new BuildText(playerCacheModelService).of(player, messageHandler.get(player, "coins.have")));
    }
}
