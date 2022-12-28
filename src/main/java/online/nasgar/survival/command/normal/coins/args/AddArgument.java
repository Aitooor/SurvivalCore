package online.nasgar.survival.command.normal.coins.args;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.survival.managers.command.Argument;
import online.nasgar.survival.managers.playerdata.PlayerData;
import online.nasgar.survival.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddArgument extends Argument {

    private final CachedRemoteModelService<PlayerData> modelService;
    private final MessageHandler messageHandler;

    public AddArgument(CachedRemoteModelService<PlayerData> modelService, MessageHandler messageHandler) {
        super(messageHandler, "add");
        this.modelService = modelService;
        this.messageHandler = messageHandler;

        this.setPermission("survivalcore.coins.add");
    }

    @Override
    public void onArgument(CommandSender sender, String[] array) {
        if (array.length < 1) {
            messageHandler.send(sender, "coins.add.usage");
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])) {
            return;
        }

        if (!StringUtils.isInteger(array[1])) {
            messageHandler.sendReplacing(sender, "coins.invalid.number", "%number%", array[1]);
            return;
        }

        PlayerData data = modelService.getOrFindSync(target.getUniqueId().toString());
        int amount = Integer.parseInt(array[1]);

        data.addCoins(amount);
        messageHandler.sendReplacing(sender, "coins.add.success.sender", "%target_name%", target.getName(), "%amount%", amount);
        messageHandler.sendReplacing(sender, "coins.add.success.target", "%amount%", amount, "%staff_name%", sender.getName());
    }
}
