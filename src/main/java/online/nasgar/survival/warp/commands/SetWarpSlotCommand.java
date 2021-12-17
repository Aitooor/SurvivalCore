package online.nasgar.survival.warp.commands;

import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;

public class SetWarpSlotCommand extends Command {

    public SetWarpSlotCommand() {
        super("setwarpslot");

        this.setPermission("setwarpslot.command");
        this.setOnlyPlayers(true);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /setwarpslot <warpName> <slot>"));
            return;
        }

        WarpData warpData = WarpManager.getInstance().getWarpDataByName(args[0]);

        if (warpData == null) {
            player.sendMessage(CC.translate("&cA warp with that name doesn't exists."));
            return;
        }

        warpData.setSlot(Integer.parseInt(args[1]));
        player.sendMessage(CC.translate("&aWarp slot updated."));
    }
}
