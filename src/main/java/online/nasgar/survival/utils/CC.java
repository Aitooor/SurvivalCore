package online.nasgar.survival.utils;

import online.nasgar.survival.Survival;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class CC {

    public static String translate(String translate) {
        return ChatColor.translateAlternateColorCodes('&', translate);
    }

    public static List<String> translate(List<String> translate) {
        return translate.stream().map(CC::translate).collect(Collectors.toList());
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
