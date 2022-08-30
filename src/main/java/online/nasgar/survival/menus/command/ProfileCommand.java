package online.nasgar.survival.menus.command;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.survival.managers.command.Command;
import online.nasgar.survival.menus.ProfileMenu;
import online.nasgar.survival.managers.playerdata.PlayerData;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ProfileCommand extends Command {

    private final CachedRemoteModelService<PlayerData> modelService;
    private final MessageHandler messageHandler;

    public ProfileCommand(CachedRemoteModelService<PlayerData> modelService, MessageHandler messageHandler) {
        super("profile", messageHandler);
        this.modelService = modelService;
        this.messageHandler = messageHandler;

        this.setOnlyPlayers(true);
        this.setAliases(Arrays.asList("profilemenu", "profilegui"));
    }

    @Override
    public void onCommand(Player player, String[] array) {
        new ProfileMenu(
                messageHandler.get(player, "guis.profile.title"),
                modelService
        ).openMenu(player);
    }
}
