package online.nasgar.survival.warp.commands;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;

public class SetWarpCommand extends Command {

    private final MessageHandler messageHandler;

    public SetWarpCommand(MessageHandler messageHandler) {
        super("setwarp", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("setwarp.command");
        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 1) {
            messageHandler.send(player, "warp.create.usage");
            return;
        }

        WarpData warpData = WarpManager.getInstance().getWarpDataByName(args[0]);

        if (warpData != null) {
            messageHandler.sendReplacing(player, "warp.exists", "%warp_name%", args[0]);
            return;
        }

        warpData = new WarpData();

        warpData.setName(args[0]);
        warpData.setLocation(player.getLocation());

        WarpManager.getInstance().getWarps().add(warpData);

        messageHandler.sendReplacing(player, "warp.create.success", "%warp_name%", args[0]);
    }
}
