package online.nasgar.survival.auctions.menu;

import com.google.common.collect.Lists;
import online.nasgar.survival.auctions.AuctionData;
import online.nasgar.survival.auctions.AuctionsManager;
import online.nasgar.survival.menu.Menu;
import online.nasgar.survival.menu.button.Button;
import online.nasgar.survival.menu.type.MenuType;
import online.nasgar.survival.utils.ItemCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuctionMenu extends Menu {

    private List<AuctionData> notAddedAuctions;

    public AuctionMenu(List<AuctionData> auctionData) {
        super("Auction", 54, MenuType.CHEST);

        this.notAddedAuctions = auctionData;
    }

    @Override
    public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        List<AuctionData> datas = new ArrayList<>(AuctionsManager.getInstance().getAuctions());
        int id = 0;

        for (AuctionData data : datas) {
            if (id >= this.getInventory().getSize()) {
                notAddedAuctions.add(data);
                continue;
            }

            int finalId = id;
            buttons.add(new Button(finalId) {
                @Override
                public void onClick(InventoryClickEvent event) {

                }

                @Override
                public ItemStack getButtonItem() {
                    return new ItemCreator(data.getStack().getType()).setDisplayName("&b&lAuction #" + (finalId + 1)).setLore("  ", "&ePrice: &a$" + data.getPrice(), "  ", "&aClick here to buy this item.").toItemStack();
                }
            });

            ++id;
        }


        return buttons;
    }
}
