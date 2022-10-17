package online.nasgar.survival.managers.command;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.survival.command.*;
import online.nasgar.survival.command.admin.GamemodeCommand;
import online.nasgar.survival.command.admin.GodCommand;
import online.nasgar.survival.command.admin.ReloadCommand;
import online.nasgar.survival.command.admin.TopCommand;
import online.nasgar.survival.command.coins.CoinsCommand;
import online.nasgar.survival.command.message.MessageCommand;
import online.nasgar.survival.command.message.ReplyCommand;
import online.nasgar.survival.command.social.DiscordCommand;
import online.nasgar.survival.command.social.ShopCommand;
import online.nasgar.survival.command.social.WebCommand;
import online.nasgar.survival.menus.command.*;
import online.nasgar.survival.managers.playerdata.PlayerData;
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
                new SwitchCommand(messageHandler),
                new GodCommand(messageHandler),
                new GamemodeCommand(messageHandler),
                new HealCommand(messageHandler),
                new FeedCommand(messageHandler),

                // PREMIUM
                new FlyCommand(messageHandler),
                new HatCommand(messageHandler),
                new EnderchestCommand(messageHandler),
                new WordkbenchCommand(messageHandler),
                new SkullCommand(messageHandler),

                // MESSAGE
                new MessageCommand(modelService, messageHandler),
                new ReplyCommand(modelService, messageHandler),

                // MENUS
                new MenuCommand(messageHandler),
                new ProfileCommand(modelService, messageHandler),
                new WikiCommand(modelService, messageHandler),
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
