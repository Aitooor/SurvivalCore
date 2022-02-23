package online.nasgar.survival.command.message;

import net.cosmogrp.storage.ModelService;
import online.nasgar.survival.Survival;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.command.message.event.MessageEvent;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.StringUtils;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReplyCommand extends Command {

    private final ModelService<PlayerData> playerCacheModelService;

    public ReplyCommand(ModelService<PlayerData> playerCacheModelService) {
        super("reply");
        this.playerCacheModelService = playerCacheModelService;

        this.setPermission("reply.command");
        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (array.length == 0){
            ChatUtil.toPlayer(player, "&cUsage: /reply <message>");
            return;
        }

        PlayerData playerData = playerCacheModelService.findSync(player.getUniqueId().toString());

        Player target = Bukkit.getPlayer(playerData.getLastConverser());

        if (this.isPlayerNull(target, playerData.getLastConverser())){
            return;
        }

        PlayerData targetData = playerCacheModelService.findSync(target.getUniqueId().toString());

        if (playerData.isTpm()){
            ChatUtil.toPlayer(player, "&cYou have the private messages disabled!");
            return;
        }

        if (targetData.isTpm()){
            ChatUtil.toPlayer(player, "&e" + array[0] + " &chave the private messages disabled!");
            return;
        }

        new MessageEvent(player, target, StringUtils.buildString(array, 1).toString());
    }
}
