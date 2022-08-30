package online.nasgar.vanilla.auctions.menu;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.ModelService;
import online.nasgar.vanilla.auctions.AuctionData;
import online.nasgar.vanilla.auctions.AuctionsManager;
import online.nasgar.vanilla.managers.menus.Menu;
import online.nasgar.vanilla.managers.menus.button.Button;
import online.nasgar.vanilla.managers.menus.type.MenuType;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import online.nasgar.vanilla.utils.ItemCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuctionMenu extends Menu {

    private List<AuctionData> notAddedAuctions;

    private final ModelService<PlayerData> playerCacheModelService;

    private final MessageHandler messageHandler;

    public AuctionMenu(List<AuctionData> auctionData, ModelService<PlayerData> playerCacheModelService, MessageHandler messageHandler) {
        super("Auctions", 54, MenuType.CHEST);

        this.notAddedAuctions = auctionData;
        this.playerCacheModelService = playerCacheModelService;

        this.messageHandler = messageHandler;
    }

    @Override
    public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        int id = 1;

        for (AuctionData data : notAddedAuctions) {
            if (id >= this.getInventory().getSize()) {
                notAddedAuctions.add(data);
                continue;
            }

            int finalId = id;
            buttons.add(new Button(finalId) {
                @Override
                public void onClick(InventoryClickEvent event) {
                    player.closeInventory();

                    PlayerData playerData = playerCacheModelService.findSync(player.getUniqueId().toString());

                    if (playerData.getCoins() < data.getPrice()) {
                        messageHandler.send(player, "auction.gui.no-money");
                        return;
                    }

                    playerData.setCoins((int) (playerData.getCoins() - data.getPrice()));
                    playerCacheModelService.saveSync(playerData);

                    AuctionsManager.getInstance().getAuctions().removeIf(auctionData -> auctionData.getId().equals(data.getId()));

                    if (player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItem(player.getLocation(), data.getStack());
                        return;
                    }

                    player.getInventory().addItem(data.getStack());
                }

                @Override
                public ItemStack getButtonItem() {
                    return new ItemCreator(data.getStack().getType()).setDisplayName(messageHandler.replacing(player, "auction.gui.product-name") + (finalId + 1)).setLore("  ", messageHandler.replacing(player, "auction.gui.lore-price") + data.getPrice() + "&6$", "  ", messageHandler.replacing(player, "auction.gui.lore-click")).toItemStack();
                }
            });

            ++id;
        }


        return buttons;
    }
}
