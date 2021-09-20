package online.nasgar.survival.command.management;

import online.nasgar.survival.command.*;
import online.nasgar.survival.command.message.MessageCommand;
import online.nasgar.survival.command.message.ReplyCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandManager {

    public CommandManager(){
        this.register(

                new FlyCommand(),
                new GodCommand(),
                new GamemodeCommand(),
                new MessageCommand(),
                new HealCommand(),
                new ReplyCommand(),
                new MenuCommand()

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
