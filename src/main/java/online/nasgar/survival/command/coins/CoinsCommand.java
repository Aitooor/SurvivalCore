package online.nasgar.survival.command.coins;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.mongo.MongoModelService;
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

    private final MongoModelService<PlayerData> playerDataMongoModelService;
    private final MessageHandler messageHandler;

    public CoinsCommand(MongoModelService<PlayerData> playerDataMongoModelService, MessageHandler messageHandler) {
        super("coins", messageHandler);
        this.playerDataMongoModelService = playerDataMongoModelService;
        this.messageHandler = messageHandler;

        this.setAliases(Arrays.asList("economy", "balance"));
        this.setArgumentBase(true);

        this.addArguments(new AddArgument(this.playerDataMongoModelService, messageHandler), new RemoveArgument(playerDataMongoModelService, messageHandler), new SetArgument(playerDataMongoModelService, messageHandler));
    }

    @Override public void onCommand(CommandSender sender, String[] array) {
        if (sender instanceof ConsoleCommandSender){
            messageHandler.send(sender, "coins.limited-console");
            return;
        }

        Player player = (Player) sender;

        String text = new BuildText(playerDataMongoModelService).of(player, messageHandler.get(player, "coins.have"));


        ChatUtil.toPlayer(player, text);
        return;
    }
}
