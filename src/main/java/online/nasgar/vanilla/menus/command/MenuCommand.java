package online.nasgar.vanilla.menus.command;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import online.nasgar.vanilla.menus.MainMenu;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MenuCommand extends Command {

    private final MessageHandler messageHandler;

    public MenuCommand(MessageHandler messageHandler) {
        super("menu", messageHandler);
        this.messageHandler = messageHandler;

        this.setOnlyPlayers(true);
        this.setAliases(Arrays.asList("gui", "mainmenu"));
    }

    @Override public void onCommand(Player player, String[] array) {
        new MainMenu(messageHandler.get(player, "guis.main.title")).openMenu(player);
    }
}
