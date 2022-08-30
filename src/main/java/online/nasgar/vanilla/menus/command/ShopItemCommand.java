package online.nasgar.vanilla.menus.command;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import online.nasgar.vanilla.menus.ShopItemMenu;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ShopItemCommand extends Command {

    private final MessageHandler messageHandler;

    public ShopItemCommand(MessageHandler messageHandler) {
        super("shopitem", messageHandler);
        this.messageHandler = messageHandler;

        this.setOnlyPlayers(true);
        this.setAliases(Arrays.asList("shopitemmenu", "shopitemgui", "shopgui"));
    }

    @Override public void onCommand(Player player, String[] array) {
        new ShopItemMenu(messageHandler.get(player, "guis.shop")).openMenu(player);
    }
}
