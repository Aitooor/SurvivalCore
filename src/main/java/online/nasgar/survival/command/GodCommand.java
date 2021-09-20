package online.nasgar.survival.command;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GodCommand extends Command {

    private static Set<UUID> uuids;

    public GodCommand() {
        super("god");

        this.setPermission("god.command");
        this.setOnlyPlayers(true);

        uuids = new HashSet<>();
    }

    @Override public void onCommand(Player player, String[] array) {
        if (array.length == 0){
            UUID uuid = player.getUniqueId();

            if (!contains(uuid)){
                this.add(uuid);

                ChatUtil.toPlayer(player, "&aGod enabled");
            } else {
                this.remove(uuid);

                ChatUtil.toPlayer(player, "&cGod disabled");
            }
            return;
        }

        if (!player.hasPermission("god.others.command")) {
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])) {
            return;
        }

        UUID uuid = target.getUniqueId();

        if (!contains(uuid)) {
            this.add(uuid);

            ChatUtil.toPlayer(player, "&aGod for &e" + array[0] + " &aenabled!");
            ChatUtil.toPlayer(target, "&aGod enabled by &e"+ player.getName());
        } else {
            this.remove(uuid);

            ChatUtil.toPlayer(player, "&cGod for &e" + array[0] + " &cdisabled!");
            ChatUtil.toPlayer(target, "&cGod disabled by &e"+ player.getName());
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
