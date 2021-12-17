package online.nasgar.survival.command.management;

import online.nasgar.survival.Survival;
import online.nasgar.survival.auctions.commands.AuctionCommand;
import online.nasgar.survival.backpack.commands.BackPackCommand;
import online.nasgar.survival.command.*;
import online.nasgar.survival.command.coins.CoinsCommand;
import online.nasgar.survival.command.message.MessageCommand;
import online.nasgar.survival.command.message.ReplyCommand;
import online.nasgar.survival.command.rankup.SeeNextRankUpCommand;
import online.nasgar.survival.command.rankup.SeeRankUpCommand;
import online.nasgar.survival.randomtp.commands.RandomTPCommand;
import online.nasgar.survival.warp.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandManager {

    public CommandManager(Survival plugin){
        this.register(

                new FlyCommand(),
                new GodCommand(),
                new GamemodeCommand(),
                new MessageCommand(),
                new HealCommand(),
                new ReplyCommand(),
                new MenuCommand(),
                new ProfileCommand(),
                new ShopItemCommand(),
                new SeeNextRankUpCommand(plugin),
                new SeeRankUpCommand(plugin),
                new FeedCommand(),
                new CoinsCommand(),
                new SetWarpCommand(),
                new DeleteWarpCommand(),
                new WarpCommand(),
                new SetWarpPermissionCommand(),
                new WarpsCommand(),
                new SetWarpItemCommand(),
                new SetWarpSlotCommand(),
                new BackPackCommand(),
                new RandomTPCommand(),
                new AuctionCommand()
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
