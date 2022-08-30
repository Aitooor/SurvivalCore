package online.nasgar.vanilla.command;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FeedCommand extends Command {

    private final MessageHandler messageHandler;

    public FeedCommand(MessageHandler messageHandler) {
        super("feed", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("survivalcore.feed");

        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (player.getFoodLevel() == 20){
            messageHandler.send(player, "feed.max.player");
            return;
        }

        if (array.length < 1) {
            player.setFoodLevel(20);
            messageHandler.send(player, "feed.restored.player");
            return;
        }

        if (!player.hasPermission("survivalcore.feed.others")){
            return;
        }


        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])){
            return;
        }

        if (target.getFoodLevel() == 20){
            messageHandler.sendReplacing(player, "feed.max.target", "%target_name%", array[0]);
            return;
        }

        target.setFoodLevel(20);

        messageHandler.sendReplacing(player, "feed.restored.target.you", "%target_name%", array[0]);
        messageHandler.sendReplacing(target, "feed.restored.target.other", "%staff_name%", player.getName());
    }
}
