package online.nasgar.survival.warp.commands;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;

public class SetWarpPermissionCommand extends Command {

    private final MessageHandler messageHandler;

    public SetWarpPermissionCommand(MessageHandler messageHandler) {
        super("setwarppermission", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("setwarppermission.command");
        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 2) {
            messageHandler.send(player, "warp.set-warp.permission.usage");
            return;
        }

        WarpData warpData = WarpManager.getInstance().getWarpDataByName(args[0]);

        if (warpData == null) {
            messageHandler.sendReplacing(player, "warp.not-exists", "%warp_name%", args[0]);
            return;
        }

        warpData.setPermission(args[1]);
        messageHandler.sendReplacing(player, "warp.set-warp.permission.success", "%warp_name%", args[0]);
    }
}
