package online.nasgar.survival.warp.commands;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;

public class WarpCommand extends Command {

    private final MessageHandler messageHandler;

    public WarpCommand(MessageHandler messageHandler) {
        super("warp", messageHandler);
        this.messageHandler = messageHandler;

        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 1) {
            messageHandler.send(player, "warp.usage");
            return;
        }

        WarpData warpData = WarpManager.getInstance().getWarpDataByName(args[0]);

        if (warpData == null) {
            messageHandler.sendReplacing(player, "warp.not-exists", "%warp_name%", args[0]);
            return;
        }

        if (warpData.getPermission() != null && warpData.getPermission().isEmpty()) {
            if (!player.hasPermission(warpData.getPermission())) {
                messageHandler.sendReplacing(player, "warp.no-permission", "%warp_name%", args[0]);
                return;
            }
        }

        player.teleport(warpData.getLocation());
        messageHandler.sendReplacing(player, "warp.teleport", "%warp_name%", args[0]);
    }
}
