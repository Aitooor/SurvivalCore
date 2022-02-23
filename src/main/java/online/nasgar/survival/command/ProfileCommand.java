package online.nasgar.survival.command;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.ModelService;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.menu.impl.ProfileMenu;
import online.nasgar.survival.playerdata.PlayerData;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ProfileCommand extends Command {

    private final ModelService<PlayerData> playerCacheModelService;
    private final MessageHandler messageHandler;

    public ProfileCommand(ModelService<PlayerData> playerCacheModelService, MessageHandler messageHandler) {
        super("profile", messageHandler);
        this.playerCacheModelService = playerCacheModelService;
        this.messageHandler = messageHandler;

        this.setPermission("profile.command");
        this.setOnlyPlayers(true);
        this.setAliases(Arrays.asList("profilemenu", "profilegui"));
    }

    @Override public void onCommand(Player player, String[] array) {
        new ProfileMenu(
                messageHandler.get(player, "guis.profile"),
                playerCacheModelService
        ).openMenu(player);
    }
}
