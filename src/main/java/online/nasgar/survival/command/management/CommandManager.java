package online.nasgar.survival.command.management;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.ModelService;
import net.cosmogrp.storage.mongo.MongoModelService;
import online.nasgar.survival.auctions.commands.AuctionCommand;
import online.nasgar.survival.backpack.commands.BackPackCommand;
import online.nasgar.survival.command.*;
import online.nasgar.survival.command.coins.CoinsCommand;
import online.nasgar.survival.command.message.MessageCommand;
import online.nasgar.survival.command.message.ReplyCommand;
import online.nasgar.survival.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandManager {


    public CommandManager(MongoModelService<PlayerData> playerDataMongoModelService,
                          ModelService<PlayerData> playerCacheModelService, MessageHandler messageHandler){
        this.register(

                new FlyCommand(messageHandler),
                new GodCommand(messageHandler),
                new GamemodeCommand(messageHandler),
                new MessageCommand(playerCacheModelService, messageHandler),
                new HealCommand(messageHandler),
                new ReplyCommand(playerCacheModelService, messageHandler),
                new MenuCommand(messageHandler),
                new ProfileCommand(playerCacheModelService, messageHandler),
                new ShopItemCommand(messageHandler),
                new FeedCommand(messageHandler),
                new CoinsCommand(playerDataMongoModelService, messageHandler),
                new BackPackCommand(playerCacheModelService),
                new AuctionCommand(playerCacheModelService, messageHandler),
                new ReloadCommand(messageHandler)
        );
    }

    public void register(Command... commands){
        CommandMap commandMap;
        SimplePluginManager pluginManager = (SimplePluginManager) Bukkit.getPluginManager();

        try {

            Field field = pluginManager.getClass().getDeclaredField("commandMap");
            field.setAccessible(true);

            commandMap = (CommandMap) field.get(pluginManager);
        } catch (Exception exception){
            throw new RuntimeException(exception);
        }

        Arrays.asList(commands).forEach(command -> commandMap.register("survival", command));
    }

}
