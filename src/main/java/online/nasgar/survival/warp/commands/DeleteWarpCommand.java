package online.nasgar.survival.warp.commands;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;

public class DeleteWarpCommand extends Command {

    public DeleteWarpCommand() {
        super("deletewarp", messageHandler);

        this.setPermission("deletewarp.command");
        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /deletewarp <warpName>"));
            return;
        }

        WarpData warpData = WarpManager.getInstance().getWarpDataByName(args[0]);

        if (warpData == null) {
            player.sendMessage(CC.translate("&cA warp with that name doesn't exists."));
            return;
        }

        WarpManager.getInstance().getWarps().removeIf(warp -> warp.getName().equalsIgnoreCase(args[0]));

        player.sendMessage(CC.translate("&aWarp deleted."));
    }
}
