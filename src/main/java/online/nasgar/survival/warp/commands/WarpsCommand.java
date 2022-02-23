package online.nasgar.survival.warp.commands;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.warp.menu.WarpsMenu;
import org.bukkit.entity.Player;

public class WarpsCommand extends Command {

    public WarpsCommand() {
        super("warps", null);

        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        new WarpsMenu().openMenu(player);
    }
}