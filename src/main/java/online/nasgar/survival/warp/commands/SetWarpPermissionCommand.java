package online.nasgar.survival.warp.commands;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;

public class SetWarpPermissionCommand extends Command {

    public SetWarpPermissionCommand() {
        super("setwarppermission");

        this.setPermission("setwarppermission.command");
        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /setwarppermission <warpName> <permission>"));
            return;
        }

        WarpData warpData = WarpManager.getInstance().getWarpDataByName(args[0]);

        if (warpData == null) {
            player.sendMessage(CC.translate("&cA warp with that name doesn't exists."));
            return;
        }

        warpData.setPermission(args[1]);
        player.sendMessage(CC.translate("&aWarp permission updated."));
    }
}
