package online.nasgar.survival.warp.commands;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;

public class WarpCommand extends Command {

    public WarpCommand() {
        super("warp", messageHandler);

        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /warp <warpName>"));
            return;
        }

        WarpData warpData = WarpManager.getInstance().getWarpDataByName(args[0]);

        if (warpData == null) {
            player.sendMessage(CC.translate("&cA warp with that name doesn't exists."));
            return;
        }

        if (warpData.getPermission() != null && warpData.getPermission().isEmpty()) {
            if (!player.hasPermission(warpData.getPermission())) {
                player.sendMessage(CC.translate("&cYou can't use this warp."));
                return;
            }
        }

        player.teleport(warpData.getLocation());
        player.sendMessage(CC.translate("&aSuccessfully teleported to warp."));
    }
}
