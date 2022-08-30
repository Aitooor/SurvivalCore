package online.nasgar.vanilla.command.social;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ShopCommand extends Command {

    private final MessageHandler messageHandler;

    public ShopCommand(MessageHandler messageHandler) {
        super("shop", messageHandler);
        this.messageHandler = messageHandler;

        this.setAliases(Arrays.asList("tienda", "comprar", "buy", "store"));

        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] array) {
        if (array.length == 0){
                messageHandler.send(player, "shop.player");
                return;
        }

        if(player.hasPermission("survivalcore.shop.other")){
            Player target = Bukkit.getPlayer(array[0]);

            if(this.isPlayerNull(target, array[0])){
                return;
            }

            messageHandler.sendReplacing(player, "shop.target.you", "%target%", array[0]);
            messageHandler.sendReplacing(target, "shop.target.other", "%staff%", player.getName());
        } else {
            messageHandler.send(player, "no-permission");
        }
    }
}
