package online.nasgar.survival.command;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.menu.impl.ShopItemMenu;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ShopItemCommand extends Command {

    public ShopItemCommand() {
        super("shopitem", messageHandler);

        this.setPermission("shopitem.command");
        this.setOnlyPlayers(true);
        this.setAliases(Arrays.asList("shopitemmenu", "shopitemgui", "shopgui"));
    }

    @Override public void onCommand(Player player, String[] array) {
        new ShopItemMenu().openMenu(player);
    }
}
