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

public class SetArgument extends Argument {

    private final ModelService<PlayerData> playerCacheModelService;

    public SetArgument(ModelService<PlayerData> playerCacheModelService) {
        super("set");
        this.playerCacheModelService = playerCacheModelService;

        this.setPermission("coins.set.command");
    }

    @Override public void onArgument(CommandSender sender, String[] array) {
        if (array.length < 1){
            ChatUtil.toSender(sender, "&cUsage: /coins set <player> <amount>");
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

        int amount = Integer.parseInt(array[1]);

        playerCacheModelService.findSync(target.getUniqueId().toString()).setCoins(amount);
        ChatUtil.toSender(sender, "&aSuccessfully set to &e" + array[0] + " &aa amount of &e" + amount + "$&a!");
        ChatUtil.toPlayer(target, "&e" + sender.getName() + " &ahas set &e" + amount + "$ &afor you!");
    }
}
