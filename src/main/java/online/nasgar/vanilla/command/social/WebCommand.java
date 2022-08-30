package online.nasgar.vanilla.command.social;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class WebCommand extends Command {

    private final MessageHandler messageHandler;

    public WebCommand(MessageHandler messageHandler) {
        super("web", messageHandler);
        this.messageHandler = messageHandler;

        this.setAliases(Arrays.asList("website", "paginaweb"));

        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] array) {
        if (array.length == 0){
                messageHandler.send(player, "web.player");
                return;
        }

        if (player.hasPermission("survivalcore.web.other")){
            Player target = Bukkit.getPlayer(array[0]);

            if (this.isPlayerNull(target, array[0])){
                return;
            }

            messageHandler.sendReplacing(player, "web.target.you", "%target%", array[0]);
            messageHandler.sendReplacing(target, "web.target.other", "%staff%", player.getName());
        } else {
            messageHandler.send(player, "no-permission");
        }
    }
}
