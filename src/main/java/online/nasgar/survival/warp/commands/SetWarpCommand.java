package online.nasgar.survival.warp.commands;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;

public class SetWarpCommand extends Command {

    public SetWarpCommand() {
        super("setwarp");

        this.setPermission("setwarp.command");
        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /setwarp <warpName>"));
            return;
        }

        WarpData warpData = WarpManager.getInstance().getWarpDataByName(args[0]);

        if (warpData != null) {
            player.sendMessage(CC.translate("&cA warp with that name already exists."));
            return;
        }

        warpData = new WarpData();

        warpData.setName(args[0]);
        warpData.setLocation(player.getLocation());

        WarpManager.getInstance().getWarps().add(warpData);

        player.sendMessage(CC.translate("&aWarp created."));
    }
}
