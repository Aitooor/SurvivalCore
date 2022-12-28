package online.nasgar.survival.utils.text;

import online.nasgar.survival.Survival;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatUtil {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> stringList) {
        List<String> arrayList = new ArrayList<>();
        for (String list : stringList) {
            arrayList.add(translate(list));
        }
        return arrayList;
    }

    public static void toPlayer(Player player, List<String> stringList) {
        stringList.forEach(message -> toPlayer(player, message));
    }

    public static void toPlayer(Player player, String message) {
        player.sendMessage(translate(message).replace("<player>", player.getName()));
    }

    public void toSender(CommandSender sender, List<String> stringList) {
        stringList.forEach(message -> toSender(sender, message));
    }

    public static void toSender(CommandSender sender, String message) {
        sender.sendMessage(translate(message));
    }

    public static void logError(String... args) {
        for (String str : args) {
            Bukkit.getServer().getConsoleSender().sendMessage(translate(getPrefix() + "[ERROR] &c" + str));
        }
    }

    public static String getPrefixGame(Player sender) {
        return Survival.getInstance().getMessageHandler().replacing(sender, "prefix-game");
    }

    public static String getPrefix() {
        return "[SurvivalCore] ";
    }

}
