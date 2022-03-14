package online.nasgar.survival.warp.commands;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;

public class DeleteWarpCommand extends Command {

    private final MessageHandler messageHandler;

    public DeleteWarpCommand(MessageHandler messageHandler) {
        super("deletewarp", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("deletewarp.command");
        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 1) {
            messageHandler.send(player, "warp.delete.usage");
            return;
        }

        WarpData warpData = WarpManager.getInstance().getWarpDataByName(args[0]);

        if (warpData == null) {
            messageHandler.sendReplacing(player, "warp.not-exists", "%warp_name%", args[0]);
            return;
        }

        WarpManager.getInstance().getWarps().removeIf(warp -> warp.getName().equalsIgnoreCase(args[0]));

        messageHandler.sendReplacing(player, "warp.delete.success", "%warp_name%", args[0]);
    }
}
