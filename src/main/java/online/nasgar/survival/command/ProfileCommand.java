package online.nasgar.survival.command;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.menu.impl.ProfileMenu;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ProfileCommand extends Command {

    public ProfileCommand() {
        super("profile");

        this.setPermission("profile.command");
        this.setOnlyPlayers(true);
        this.setAliases(Arrays.asList("profilemenu", "profilegui"));
    }

    @Override public void onCommand(Player player, String[] array) {
        new ProfileMenu().openMenu(player);
    }
}
