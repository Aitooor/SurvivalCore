package online.nasgar.survival.command.coins.arguments;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.mongo.MongoModelService;
import online.nasgar.survival.command.management.Argument;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetArgument extends Argument {

    private final MongoModelService<PlayerData> playerDataMongoModelService;
    private final MessageHandler messageHandler;

    public SetArgument(MongoModelService<PlayerData> playerDataMongoModelService, MessageHandler messageHandler) {
        super(messageHandler, "set");
        this.playerDataMongoModelService = playerDataMongoModelService;
        this.messageHandler = messageHandler;

        this.setPermission("coins.set.command");
    }

    @Override public void onArgument(CommandSender sender, String[] array) {
        if (array.length < 1){
            messageHandler.send(sender, "coins.set.usage");
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

        int amount = Integer.parseInt(array[1]);

        playerDataMongoModelService.findSync(target.getUniqueId().toString()).setCoins(amount);
        messageHandler.sendReplacing(sender, "coins.set.success.sender", "%target_name%", target.getName(), "%amount%", amount);
        messageHandler.sendReplacing(sender, "coins.set.success.target", "%amount%", amount , "%staff_name%", sender.getName());
    }
}
