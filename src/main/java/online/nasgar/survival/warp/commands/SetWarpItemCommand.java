package online.nasgar.survival.warp.commands;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;

public class SetWarpItemCommand extends Command {

    private final MessageHandler messageHandler;

    public SetWarpItemCommand(MessageHandler messageHandler) {
        super("setwarpitem", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("setwarpitem.command");
        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 1) {
            messageHandler.send(player, "warp.set-warp.items.usage");
            return;
        }

        WarpData warpData = WarpManager.getInstance().getWarpDataByName(args[0]);

        if (warpData == null) {
            messageHandler.sendReplacing(player, "warp.not-exists", "%warp_name%", args[0]);
            return;
        }

        if (player.getItemInHand() == null){
            messageHandler.send(player, "warp.set-warp.items.error");
            return;
        }

        warpData.setStack(player.getItemInHand());
        messageHandler.send(player, "warp.set-warp.items.success");
    }
}
