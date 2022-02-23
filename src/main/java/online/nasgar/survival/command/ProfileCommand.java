package online.nasgar.survival.command;

import net.cosmogrp.storage.ModelService;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.menu.impl.ProfileMenu;
import online.nasgar.survival.playerdata.PlayerData;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ProfileCommand extends Command {

    private final ModelService<PlayerData> playerCacheModelService;

    public ProfileCommand(ModelService<PlayerData> playerCacheModelService) {
        super("profile", messageHandler);
        this.playerCacheModelService = playerCacheModelService;

        this.setPermission("profile.command");
        this.setOnlyPlayers(true);
        this.setAliases(Arrays.asList("profilemenu", "profilegui"));
    }

    @Override public void onCommand(Player player, String[] array) {
        new ProfileMenu(playerCacheModelService).openMenu(player);
    }
}
