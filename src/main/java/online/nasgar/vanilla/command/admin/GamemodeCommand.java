package online.nasgar.vanilla.command.admin;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamemodeCommand extends Command {

    private final MessageHandler messageHandler;

    public GamemodeCommand(MessageHandler messageHandler) {
        super("gamemode", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("survivalcore.gamemode");
        this.setAliases(Arrays.asList("gm", "gmode"));
        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (array.length == 0){
            messageHandler.send(player, "gamemode.usage");
            return;
        }

        String gameMode = this.getGamemodeName(array[0]);

        if (gameMode == null){
            messageHandler.sendReplacing(player, "gamemode.not-found", "%gamemode%", array[0]);
            return;
        }

        player.setGameMode(GameMode.valueOf(gameMode));
        messageHandler.sendReplacing(player, "gamemode.update", "%gamemode%", gameMode);
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

            case "spec":
            case "spectator":
            case "3": {
                return "SPECTATOR";
            }

            default: {
                return null;
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] array) {
        List<String> list = new ArrayList<>();

        if (sender.hasPermission("survivalcore.gamemode")){
            list.add(this.getGamemodeName(array[0]));
            return list;
        }

        return null;
    }
}
