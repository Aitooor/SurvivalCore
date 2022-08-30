package online.nasgar.vanilla.utils.text;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class ChatUtil {

    public String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String translate(String... longMessage) {
        for (String message : longMessage) {
            return message;
        }
        return null;
    }

    public static List<String> translate(List<String> stringList) {
        List<String> arrayList = new ArrayList<>();
        for (String list : stringList) {
            arrayList.add(translate(list));
        }
        return arrayList;
    }

    public void print(String... message) {
        Arrays.asList(message).forEach(log -> Bukkit.getConsoleSender().sendMessage(translate(log)));
    }

    public void toPlayer(Player player, List<String> stringList) {
        stringList.forEach(message -> toPlayer(player, message));
    }

    public void toPlayer(Player player, String... longMessage) {
        Arrays.asList(longMessage).forEach(message -> toPlayer(player, message));
    }

    public void toPlayer(Player player, String message) {
        player.sendMessage(translate(message).replace("<player>", player.getName()));
    }

    public void toSender(CommandSender sender, List<String> stringList) {
        stringList.forEach(message -> toSender(sender, message));
    }

    public void toSender(CommandSender sender, String... longMessage) {
        Arrays.asList(longMessage).forEach(message -> toSender(sender, message));
    }

    public void toSender(CommandSender sender, String message) {
        sender.sendMessage(translate(message));
    }


}
