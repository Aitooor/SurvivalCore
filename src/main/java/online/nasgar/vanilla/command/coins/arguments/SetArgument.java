package online.nasgar.vanilla.command.coins.arguments;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.vanilla.managers.command.Argument;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import online.nasgar.vanilla.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetArgument extends Argument {

    private final CachedRemoteModelService<PlayerData> modelService;
    private final MessageHandler messageHandler;

    public SetArgument(CachedRemoteModelService<PlayerData> modelService, MessageHandler messageHandler) {
        super(messageHandler, "set");
        this.modelService = modelService;
        this.messageHandler = messageHandler;

        this.setPermission("survivalcore.coins.set");
    }

    @Override
    public void onArgument(CommandSender sender, String[] array) {
        if (array.length < 1) {
            messageHandler.send(sender, "coins.set.usage");
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

        int amount = Integer.parseInt(array[1]);

        modelService.findSync(target.getUniqueId().toString()).setCoins(amount);
        messageHandler.sendReplacing(sender, "coins.set.success.sender", "%target_name%", target.getName(), "%amount%", amount);
        messageHandler.sendReplacing(sender, "coins.set.success.target", "%amount%", amount, "%staff_name%", sender.getName());
    }
}
