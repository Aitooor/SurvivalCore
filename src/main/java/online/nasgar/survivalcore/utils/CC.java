package online.nasgar.survivalcore.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Sound;

public class CC {

    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', "&bSURVIVAL&7: " + string);
    }

    public static String chat(String string) {
        return ChatColor.translateAlternateColorCodes('&',   string);
    }

    public static String NO_PERMISSIONS = translate("&cYout don't have permissions");

    public static String noPrefix(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
