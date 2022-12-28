package online.nasgar.survival.command.normal;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.managers.command.Command;
import online.nasgar.survival.utils.server.BungeeUtil;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SwitchCommand extends Command {

    private final MessageHandler messageHandler;

    public SwitchCommand(MessageHandler messageHandler) {
        super("switch", messageHandler);
        this.messageHandler = messageHandler;
        List<String> aliases = new ArrayList<>();
        aliases.add("cambiar");
        aliases.add("mundo");
        aliases.add("dimension");

        this.setAliases(aliases);
        this.setOnlyPlayers(true);
    }

    @Override public void onCommand(Player player, String[] array) {
        if (array.length == 0){
            if(Objects.equals(BungeeUtil.getServer(), "Survival-1")) {
                BungeeUtil.sendToServer(player, "Survival-2");
                ChatUtil.toPlayer(player, "&fHas sido enviado al &bRecursos");
                return;
            }
            else if(Objects.equals(BungeeUtil.getServer(), "Survival-2")) {
                BungeeUtil.sendToServer(player, "Survival-1");
                ChatUtil.toPlayer(player, "&fHas sido enviado al &bSpawn");
                return;
            }
        }

        if (!player.hasPermission("survivalcore.resources.others")) {
            return;
        }

        Player target = Bukkit.getPlayer(array[1]);

        if (this.isPlayerNull(target, array[1])){
            return;
        }

        if(Objects.equals(BungeeUtil.getServer(), "Survival-1")) {
            BungeeUtil.sendToServer(target, "Survival-2");
            ChatUtil.toPlayer(player, "&fHas sido enviado al &bRecursos");
        }
        else if(Objects.equals(BungeeUtil.getServer(), "Survival-2")) {
            BungeeUtil.sendToServer(target, "Survival-1");
            ChatUtil.toPlayer(player, "&fHas sido enviado al &bSpawn");
        }
    }

}
