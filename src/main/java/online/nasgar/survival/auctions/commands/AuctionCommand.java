package online.nasgar.survival.auctions.commands;

import com.google.common.primitives.Ints;
import online.nasgar.survival.auctions.AuctionData;
import online.nasgar.survival.auctions.AuctionsManager;
import online.nasgar.survival.auctions.menu.AuctionMenu;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuctionCommand extends Command {

    public AuctionCommand() {
        super("auction");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1 && args[0].equalsIgnoreCase("menu")) {
            new AuctionMenu(AuctionsManager.getInstance().getAuctions()).openMenu(player);
            return;
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("pull")) {
                if (player.getItemInHand() == null) {
                    player.sendMessage(CC.translate("&cNo item in hand."));
                    return;
                }

                Integer integer = Ints.tryParse(args[1]);

                if (integer == null) {
                    player.sendMessage(CC.translate("&cInvalid price."));
                    return;
                }

                long time = StringUtils.parse(args[2]);

                if (time <= 0L) {
                    player.sendMessage(CC.translate("&cInvalid duration."));
                    return;
                }

                AuctionData auctionData = new AuctionData();

                auctionData.setOwner(player.getUniqueId());
                auctionData.setStack(player.getItemInUse());
                auctionData.setAddedAt(System.currentTimeMillis());
                auctionData.setDuration(System.currentTimeMillis() + time);
                auctionData.setPrice(integer);

                AuctionsManager.getInstance().getAuctions().add(auctionData);

                player.sendMessage(CC.translate("&aYour item has been added successfully for sale. In case that you're offline the balance would be acredited, no matter what."));
                return;
            }
        }

        player.sendMessage(CC.translate("&b&lAuction Commands"));
        player.sendMessage(CC.translate("&7/auction menu"));
        player.sendMessage(CC.translate("&7/auction pull <price> <time>"));
    }
}
