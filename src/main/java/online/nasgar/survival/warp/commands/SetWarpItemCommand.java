package online.nasgar.survival.warp.commands;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;

public class SetWarpItemCommand extends Command {

    public SetWarpItemCommand() {
        super("setwarpitem");

        this.setPermission("setwarpitem.command");
        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /setwarpitem <warpName>"));
            return;
        }

        WarpData warpData = WarpManager.getInstance().getWarpDataByName(args[0]);

        if (warpData == null) {
            player.sendMessage(CC.translate("&cA warp with that name doesn't exists."));
            return;
        }

        if (player.getItemInHand() == null){
            player.sendMessage(CC.translate("&cNo item in hand."));
            return;
        }

        warpData.setStack(player.getItemInHand());
        player.sendMessage(CC.translate("&aWarp item updated."));
    }
}
