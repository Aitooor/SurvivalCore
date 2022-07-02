package online.nasgar.survival.command.management;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.survival.auctions.commands.AuctionCommand;
import online.nasgar.survival.backpack.commands.BackPackCommand;
import online.nasgar.survival.command.*;
import online.nasgar.survival.command.coins.CoinsCommand;
import online.nasgar.survival.command.message.MessageCommand;
import online.nasgar.survival.command.message.ReplyCommand;
import online.nasgar.survival.menu.impl.command.MenuCommand;
import online.nasgar.survival.menu.impl.command.ProfileCommand;
import online.nasgar.survival.menu.impl.command.ShopItemCommand;
import online.nasgar.survival.menu.impl.command.WikiCommand;
import online.nasgar.survival.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandManager {


    public CommandManager(CachedRemoteModelService<PlayerData> modelService, MessageHandler messageHandler) {
        this.register(

                new FlyCommand(messageHandler),
                new GodCommand(messageHandler),
                new GamemodeCommand(messageHandler),
                new MessageCommand(modelService, messageHandler),
                new HealCommand(messageHandler),
                new ReplyCommand(modelService, messageHandler),
                new MenuCommand(messageHandler),
                new ProfileCommand(modelService, messageHandler),
                new WikiCommand(modelService, messageHandler),
                new ShopItemCommand(messageHandler),
                new FeedCommand(messageHandler),
                new CoinsCommand(modelService, messageHandler),
                new BackPackCommand(modelService),
                new AuctionCommand(modelService, messageHandler),
                new ReloadCommand(messageHandler)
        );
    }

    public void register(Command... commands) {
        CommandMap commandMap;
        SimplePluginManager pluginManager = (SimplePluginManager) Bukkit.getPluginManager();

        try {

            Field field = pluginManager.getClass().getDeclaredField("commandMap");
            field.setAccessible(true);

            commandMap = (CommandMap) field.get(pluginManager);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        Arrays.asList(commands).forEach(command -> commandMap.register("survival", command));
    }

}
