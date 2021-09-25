package online.nasgar.survival.menu.impl;

import online.nasgar.survival.Survival;
import online.nasgar.survival.menu.Menu;
import online.nasgar.survival.menu.button.Button;
import online.nasgar.survival.shop.ShopItem;
import online.nasgar.survival.shop.ShopItemManager;
import online.nasgar.survival.shop.event.TransactionEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ShopItemMenu extends Menu {

    public ShopItemMenu() {
        super("&8➢ &eSHOP ITEM", 6);
    }

    @Override public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        ShopItemManager shopItemManager = Survival.getInstance().getShopItemManager();

        int i = 0;
        Iterator<ShopItem> iterator = shopItemManager.getItemMap().values().iterator();

        while (iterator.hasNext() && i++ < shopItemManager.getItemMap().size()){
            buttons.add(new Button(i) {

                private final ShopItem item = iterator.next();

                @Override public void onClick(InventoryClickEvent event) {
                    new TransactionEvent(player, this.item);
                }

                @Override public ItemStack getButtonItem() {
                    return this.item.getItemStack();
                }

            });
        }

        return buttons;
    }
}
