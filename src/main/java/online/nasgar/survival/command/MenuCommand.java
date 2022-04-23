package online.nasgar.survival.command;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.menu.impl.MainMenu;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MenuCommand extends Command {

    private final MessageHandler messageHandler;

    public MenuCommand(MessageHandler messageHandler) {
        super("menu", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("menu.command");
        this.setOnlyPlayers(true);
        this.setAliases(Arrays.asList("gui", "mainmenu"));
    }

    @Override public void onCommand(Player player, String[] array) {
        new MainMenu(messageHandler.get(player, "guis.main.title")).openMenu(player);
    }
}
