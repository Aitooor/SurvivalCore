package online.nasgar.survival.auctions.commands;

import com.google.common.primitives.Ints;
import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.ModelService;
import online.nasgar.survival.auctions.AuctionData;
import online.nasgar.survival.auctions.AuctionsManager;
import online.nasgar.survival.auctions.menu.AuctionMenu;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class AuctionCommand extends Command {

    private final ModelService<PlayerData> playerCacheModelService;
    private final MessageHandler messageHandler;

    public AuctionCommand(ModelService<PlayerData> playerCacheModelService, MessageHandler messageHandler) {
        super("auction", messageHandler);
        this.playerCacheModelService = playerCacheModelService;
        this.messageHandler = messageHandler;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1 && args[0].equalsIgnoreCase("menu")) {
            new AuctionMenu(AuctionsManager.getInstance().getAuctions().stream().filter(auctionData -> !auctionData.isRemoved()).collect(Collectors.toList()), playerCacheModelService).openMenu(player);
            return;
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("pull")) {
                if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
                    messageHandler.send(player, "auction.pull.no-item");
                    return;
                }

                Integer integer = Ints.tryParse(args[1]);

                if (integer == null) {
                    messageHandler.send(player, "auction.invalid.price");
                    return;
                }

                long time = StringUtils.parse(args[2]);

                if (time <= 0L) {
                    messageHandler.send(player, "auction.invalid.duration");
                    return;
                }

                AuctionData auctionData = new AuctionData();

                auctionData.setOwner(player.getUniqueId());
                auctionData.setStack(player.getItemInHand());
                auctionData.setAddedAt(System.currentTimeMillis());
                auctionData.setDuration(System.currentTimeMillis() + time);
                auctionData.setPrice(integer);

                AuctionsManager.getInstance().getAuctions().add(auctionData);

                player.setItemInHand(null);
                messageHandler.send(player, "auction.success");
                return;
            }
        }

        messageHandler.send(player, "auction.help");
    }
}
