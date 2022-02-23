package online.nasgar.survival.command.coins.arguments;

import net.cosmogrp.storage.ModelService;
import online.nasgar.survival.Survival;
import online.nasgar.survival.command.management.Argument;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.StringUtils;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveArgument extends Argument {

    private final ModelService<PlayerData> playerCacheModelService;

    public RemoveArgument(ModelService<PlayerData> playerCacheModelService) {
        super("remove");
        this.playerCacheModelService = playerCacheModelService;

        this.setPermission("coins.remove.command");
    }

    @Override public void onArgument(CommandSender sender, String[] array) {
        if (array.length < 1){
            ChatUtil.toSender(sender, "&cUsage: /coins remove <player> <amount>");
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        if (!StringUtils.isInteger(array[1])){
            ChatUtil.toSender(sender, "&e" + array[1] + " &cis a invalid number!");
            return;
        }

        PlayerData data = playerCacheModelService.findSync(target.getUniqueId().toString());
        int amount = Integer.parseInt(array[1]);

        if (amount > data.getCoins()){
            ChatUtil.toSender(sender, "&cThe player &e" + array[0] + " &cno have that quantity!");
            return;
        }

        data.removeCoins(amount);
        ChatUtil.toSender(sender, "&aSuccessfully removed to &e" + array[0] + " &aa amount of &e" + amount + "$&a!");
        ChatUtil.toPlayer(target, "&e" + sender.getName() + " &ahas removed &e" + amount + "$ &afor you!");
    }
}
