package online.nasgar.survival.command.management;

import net.cosmogrp.storage.ModelService;
import online.nasgar.survival.auctions.commands.AuctionCommand;
import online.nasgar.survival.backpack.commands.BackPackCommand;
import online.nasgar.survival.command.*;
import online.nasgar.survival.command.coins.CoinsCommand;
import online.nasgar.survival.command.message.MessageCommand;
import online.nasgar.survival.command.message.ReplyCommand;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.randomtp.commands.RandomTPCommand;
import online.nasgar.survival.warp.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandManager {


    public CommandManager(ModelService<PlayerData> playerCacheModelService){
        this.register(

                new FlyCommand(),
                new GodCommand(),
                new GamemodeCommand(),
                new MessageCommand(playerCacheModelService),
                new HealCommand(),
                new ReplyCommand(playerCacheModelService),
                new MenuCommand(),
                new ProfileCommand(playerCacheModelService),
                new ShopItemCommand(),
                new FeedCommand(),
                new CoinsCommand(playerCacheModelService),
                new SetWarpCommand(),
                new DeleteWarpCommand(),
                new WarpCommand(),
                new SetWarpPermissionCommand(),
                new WarpsCommand(),
                new SetWarpItemCommand(),
                new SetWarpSlotCommand(),
                new BackPackCommand(playerCacheModelService),
                new RandomTPCommand(),
                new AuctionCommand(playerCacheModelService),
                new ReloadCommand()
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
