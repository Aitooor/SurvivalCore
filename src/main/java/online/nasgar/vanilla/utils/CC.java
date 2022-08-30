package online.nasgar.vanilla.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class CC {

    public static String translate(String translate) {
        return ChatColor.translateAlternateColorCodes('&', translate);
    }

    public static List<String> translate(List<String> translate) {
        return translate.stream().map(CC::translate).collect(Collectors.toList());
    }
}
