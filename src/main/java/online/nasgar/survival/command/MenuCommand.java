package online.nasgar.survival.command;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.menu.impl.MainMenu;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MenuCommand extends Command {

    public MenuCommand() {
        super("menu");

        this.setPermission("menu.command");
        this.setOnlyPlayers(true);
        this.setAliases(Arrays.asList("gui", "mainmenu"));
    }

    @Override public void onCommand(Player player, String[] array) {
        new MainMenu().openMenu(player);
    }
}
