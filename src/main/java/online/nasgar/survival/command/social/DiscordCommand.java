package online.nasgar.survival.command.social;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.command.management.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class DiscordCommand extends Command {

    private final MessageHandler messageHandler;

    public DiscordCommand(MessageHandler messageHandler) {
        super("discord", messageHandler);
        this.messageHandler = messageHandler;
        
        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] array) {
        if (array.length == 0){
                messageHandler.send(player, "discord.player");
                return;
        }

        if(player.hasPermission("discord.other")){
            Player target = Bukkit.getPlayer(array[0]);

            if(this.isPlayerNull(target, array[0])){
                return;
            }

            messageHandler.sendReplacing(player, "discord.target.you", "%target%", array[0]);
            messageHandler.sendReplacing(target, "discord.target.other", "%staff%", player.getName());
        } else {
            messageHandler.send(player, "no-permission");
        }
    }
}