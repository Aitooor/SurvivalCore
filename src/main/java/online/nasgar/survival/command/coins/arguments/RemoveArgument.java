package online.nasgar.survival.command.coins.arguments;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.ModelService;
import online.nasgar.survival.command.management.Argument;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveArgument extends Argument {

    private final ModelService<PlayerData> playerCacheModelService;
    private final MessageHandler messageHandler;

    public RemoveArgument(ModelService<PlayerData> playerCacheModelService, MessageHandler messageHandler) {
        super(messageHandler, "remove");
        this.playerCacheModelService = playerCacheModelService;
        this.messageHandler = messageHandler;

        this.setPermission("coins.remove.command");
    }

    @Override public void onArgument(CommandSender sender, String[] array) {
        if (array.length < 1){
            messageHandler.send(sender, "coins.remove.usage");
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        if (!StringUtils.isInteger(array[1])){
            messageHandler.sendReplacing(sender, "coins.invalid.number", "%number%", array[1]);
            return;
        }

        PlayerData data = playerCacheModelService.findSync(target.getUniqueId().toString());
        int amount = Integer.parseInt(array[1]);

        if (amount > data.getCoins()){
            messageHandler.sendReplacing(sender, "coins.invalid-amount", "%target_name%", target.getName());
            return;
        }

        data.removeCoins(amount);
        messageHandler.sendReplacing(sender, "coins.remove.success.sender", "%target_name%", target.getName(), "%amount%", amount);
        messageHandler.sendReplacing(sender, "coins.remove.success.target", "%amount%", amount , "%staff_name%", sender.getName());
    }
}
