package online.nasgar.vanilla.menus.command;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.vanilla.managers.command.Command;
import online.nasgar.vanilla.menus.WikiMenu;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class WikiCommand extends Command {

    private final CachedRemoteModelService<PlayerData> modelService;
    private final MessageHandler messageHandler;

    public WikiCommand(CachedRemoteModelService<PlayerData> modelService, MessageHandler messageHandler) {
        super("wiki", messageHandler);
        this.modelService = modelService;
        this.messageHandler = messageHandler;

        this.setOnlyPlayers(true);
        this.setAliases(Arrays.asList("wikimenu", "wikigui"));
    }

    @Override
    public void onCommand(Player player, String[] array) {
        new WikiMenu(
                messageHandler.get(player, "guis.wiki.title"),
                modelService
        ).openMenu(player);
    }
}
