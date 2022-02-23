package online.nasgar.survival.randomtp.commands;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.randomtp.menu.RandomTPMenu;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RandomTPCommand extends Command {

    public RandomTPCommand() {
        super("randomtp", null);

        this.setOnlyPlayers(true);
        this.setAliases(Arrays.asList("rtp"));
    }

    @Override
    public void onCommand(Player player, String[] array) {
        new RandomTPMenu().openMenu(player);
    }
}
