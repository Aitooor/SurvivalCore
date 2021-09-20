package online.nasgar.survival.command;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamemodeCommand extends Command {

    public GamemodeCommand() {
        super("gamemode");

        this.setPermission("gamemode.command");
        this.setAliases(Arrays.asList("gm", "gmode"));
        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (array.length == 0){
            ChatUtil.toPlayer(player, "&cUsage: /gamemode <mode>");
            return;
        }

        String gameMode = this.getGamemodeName(array[0]);

        if (gameMode == null){
            ChatUtil.toPlayer(player, "&cGamemode &e" + array[0] + " &cnot found!");
            return;
        }

        player.setGameMode(GameMode.valueOf(gameMode));
        ChatUtil.toPlayer(player, "&aYour gamemode have been updated!");
    }

    private String getGamemodeName(String name){
        switch (name.toLowerCase()){

            case "s":
            case "survival":
            case "0": {
                return "SURVIVAL";
            }

            case "c":
            case "creative":
            case "1": {
                return "CREATIVE";
            }

            case "a":
            case "adventure":
            case "2": {
                return "ADVENTURE";
            }

            default: {
                return null;
            }
        }
    }

    @Override public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] array) {
        List<String> list = new ArrayList<>();

        if (sender.hasPermission("gamemode.command")){
            list.add(this.getGamemodeName(array[0]));
            return list;
        }

        return null;
    }
}
