package online.nasgar.vanilla.command.admin;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.managers.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GodCommand extends Command {

    private static Set<UUID> uuids;

    private final MessageHandler messageHandler;

    public GodCommand(MessageHandler messageHandler) {
        super("god", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("survivalcore.god");
        this.setOnlyPlayers(true);

        uuids = new HashSet<>();
    }

    @Override public void onCommand(Player player, String[] array) {
        if (array.length == 0){
            UUID uuid = player.getUniqueId();

            if (!contains(uuid)){
                this.add(uuid);

                messageHandler.send(player, "god.enabled.player");
            } else {
                this.remove(uuid);

                messageHandler.send(player, "god.disabled.player");
            }
            return;
        }

        if (!player.hasPermission("survivalcore.god.others")) {
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])) {
            return;
        }

        UUID uuid = target.getUniqueId();

        if (!contains(uuid)) {
            this.add(uuid);

            messageHandler.sendReplacing(player, "god.enabled.target.you", "%target_name%", array[0]);

            messageHandler.sendReplacing(target, "god.enabled.target.other", "%staff_name%", player.getName());

        } else {
            this.remove(uuid);

            messageHandler.sendReplacing(player, "god.disabled.target.you", "%target_name%", array[0]);

            messageHandler.sendReplacing(target, "god.disabled.target.other", "%staff_name%", player.getName());

        }
    }

    private void add(UUID uuid){
        uuids.add(uuid);
    }

    private void remove(UUID uuid){
        uuids.remove(uuid);
    }

    public static boolean contains(UUID uuid){
        return uuids.contains(uuid);
    }
}
