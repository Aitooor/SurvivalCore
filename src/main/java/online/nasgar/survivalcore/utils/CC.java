package online.nasgar.survivalcore.utils;

import org.bukkit.ChatColor;

public class CC {

    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', "&bSURVIVAL&7: " + string);
    }

    public static String noPrefix(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
