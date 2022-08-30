package online.nasgar.vanilla.backpack.commands;

import net.cosmogrp.storage.ModelService;
import online.nasgar.vanilla.backpack.BackPackMenu;
import online.nasgar.vanilla.managers.command.Command;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import org.bukkit.entity.Player;

public class BackPackCommand extends Command {

    private final ModelService<PlayerData> playerModelCacheService;

    public BackPackCommand(ModelService<PlayerData> playerModelCacheService) {
        super("backpack", null);
        this.playerModelCacheService = playerModelCacheService;

        this.setOnlyPlayers(true);
        this.setPermission("backpack.command");
    }

    @Override
    public void onCommand(Player player, String[] array) {
        BackPackMenu.open(player, playerModelCacheService);
    }
}
