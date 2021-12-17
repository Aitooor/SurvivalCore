package online.nasgar.survival.backpack.commands;

import online.nasgar.survival.backpack.BackPackMenu;
import online.nasgar.survival.command.management.Command;
import org.bukkit.entity.Player;

public class BackPackCommand extends Command {

    public BackPackCommand() {
        super("backpack");

        this.setOnlyPlayers(true);
        this.setPermission("backpack.command");
    }

    @Override
    public void onCommand(Player player, String[] array) {
        BackPackMenu.open(player);
    }
}
