package online.nasgar.vanilla.command.message;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.ModelService;
import online.nasgar.vanilla.managers.command.Command;
import online.nasgar.vanilla.command.message.event.MessageEvent;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import online.nasgar.vanilla.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MessageCommand extends Command {

    private final ModelService<PlayerData> playerModelCacheService;
    private final MessageHandler messageHandler;

    public MessageCommand(ModelService<PlayerData> playerModelCacheService, MessageHandler messageHandler) {
        super("message", messageHandler);
        this.playerModelCacheService = playerModelCacheService;
        this.messageHandler = messageHandler;

        this.setAliases(Arrays.asList("msg", "m", "tell", "whisper"));

        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (array.length < 1){
            messageHandler.send(player, "message.usage");
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        PlayerData playerData = playerModelCacheService.findSync(player.getUniqueId().toString());
        PlayerData targetData = playerModelCacheService.findSync(target.getUniqueId().toString());

        if (playerData.isTpm()){
            messageHandler.send(player, "message.tpm.player");
            return;
        }

        if (targetData.isTpm()){
            messageHandler.sendReplacing(player, "message.tpm.target", "%target_name%", target.getName());
            return;
        }

        playerData.setLastConverser(array[0]);
        targetData.setLastConverser(player.getName());
        new MessageEvent(player, target, StringUtils.buildString(array, 1).toString());
    }

}
