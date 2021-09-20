package online.nasgar.survival.command.message;

import online.nasgar.survival.Survival;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.command.message.event.MessageEvent;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.playerdata.PlayerDataManager;
import online.nasgar.survival.utils.text.ChatUtil;
import online.nasgar.survival.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MessageCommand extends Command {

    public MessageCommand() {
        super("message");

        this.setAliases(Arrays.asList("msg", "m", "tell", "whisper"));

        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (array.length < 1){
            ChatUtil.toPlayer(player, "&cUsage: /message <target> <message>");
            return;
        }

        PlayerDataManager dataManager = Survival.getInstance().getPlayerDataManager();

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        PlayerData playerData = dataManager.get(player.getUniqueId());
        PlayerData targetData = dataManager.get(target.getUniqueId());

        if (playerData.isTpm()){
            ChatUtil.toPlayer(player, "&cYou have the private messages disabled!");
            return;
        }

        if (targetData.isTpm()){
            ChatUtil.toPlayer(player, "&e" + array[0] + " &chave the private messages disabled!");
            return;
        }

        playerData.setLastConverser(array[0]);
        targetData.setLastConverser(player.getName());
        new MessageEvent(player, target, StringUtils.buildString(array, 1).toString());
    }

}
