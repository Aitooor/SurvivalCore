package online.nasgar.vanilla.managers.command;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.vanilla.auctions.commands.AuctionCommand;
import online.nasgar.vanilla.backpack.commands.BackPackCommand;
import online.nasgar.vanilla.command.*;
import online.nasgar.vanilla.command.admin.GamemodeCommand;
import online.nasgar.vanilla.command.admin.GodCommand;
import online.nasgar.vanilla.command.admin.ReloadCommand;
import online.nasgar.vanilla.command.admin.TopCommand;
import online.nasgar.vanilla.command.coins.CoinsCommand;
import online.nasgar.vanilla.command.message.MessageCommand;
import online.nasgar.vanilla.command.message.ReplyCommand;
import online.nasgar.vanilla.command.social.DiscordCommand;
import online.nasgar.vanilla.command.social.ShopCommand;
import online.nasgar.vanilla.command.social.WebCommand;
import online.nasgar.vanilla.menus.command.*;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandManager {


    public CommandManager(CachedRemoteModelService<PlayerData> modelService, MessageHandler messageHandler) {
        this.register(

                // SOCIAL
                new ShopCommand(messageHandler),
                new DiscordCommand(messageHandler),
                new WebCommand(messageHandler),

                // BASIS
                new GodCommand(messageHandler),
                new GamemodeCommand(messageHandler),
                new HealCommand(messageHandler),
                new FeedCommand(messageHandler),
                new BackPackCommand(modelService),
                new AuctionCommand(modelService, messageHandler),

                // PREMIUM
                new FlyCommand(messageHandler),
                new HatCommand(messageHandler),
                new EnderchestCommand(messageHandler),
                new SkullCommand(messageHandler),

                // MESSAGE
                new MessageCommand(modelService, messageHandler),
                new ReplyCommand(modelService, messageHandler),

                // MENUS
                new MenuCommand(messageHandler),
                new ProfileCommand(modelService, messageHandler),
                new SettingsCommand(modelService, messageHandler),
                new WikiCommand(modelService, messageHandler),
                new ShopItemCommand(messageHandler),
                new CoinsCommand(modelService, messageHandler),

                // ADMIN
                new ReloadCommand(messageHandler),
                new TopCommand(messageHandler)
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
