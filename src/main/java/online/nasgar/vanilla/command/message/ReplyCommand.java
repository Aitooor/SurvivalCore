package online.nasgar.vanilla.command.message;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.ModelService;
import online.nasgar.vanilla.managers.command.Command;
import online.nasgar.vanilla.command.message.event.MessageEvent;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import online.nasgar.vanilla.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReplyCommand extends Command {

    private final ModelService<PlayerData> playerCacheModelService;
    private final MessageHandler messageHandler;

    public ReplyCommand(ModelService<PlayerData> playerCacheModelService, MessageHandler messageHandler) {
        super("reply", messageHandler);
        this.playerCacheModelService = playerCacheModelService;
        this.messageHandler = messageHandler;

        this.setPermission("reply.command");
        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (array.length == 0){
            messageHandler.send(player, "reply.usage");
            return;
        }

        PlayerData playerData = playerCacheModelService.findSync(player.getUniqueId().toString());

        Player target = Bukkit.getPlayer(playerData.getLastConverser());

        if (this.isPlayerNull(target, playerData.getLastConverser())){
            return;
        }

        PlayerData targetData = playerCacheModelService.findSync(target.getUniqueId().toString());

        if (playerData.isTpm()){
            messageHandler.send(player, "message.tpm.player");
            return;
        }

        if (targetData.isTpm()){
            messageHandler.sendReplacing(player, "message.tpm.target", "%target_name%", target.getName());
            return;
        }

        new MessageEvent(player, target, StringUtils.buildString(array, 1).toString());
    }
}
