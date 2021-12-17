package online.nasgar.survival.warp.menu;

import online.nasgar.survival.menu.Menu;
import online.nasgar.survival.menu.button.Button;
import online.nasgar.survival.menu.type.MenuType;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.warp.WarpData;
import online.nasgar.survival.warp.WarpManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class WarpsMenu extends Menu {

    public WarpsMenu() {
        super(CC.translate("Warps"), 45, MenuType.CHEST);
    }

    @Override
    public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        for (WarpData warp : WarpManager.getInstance().getWarps()) {
            if (warp.getStack() == null) continue;

            buttons.add(new Button(warp.getSlot()) {
                @Override
                public void onClick(InventoryClickEvent event) {
                    player.closeInventory();

                    player.performCommand("warp " + warp.getName());
                }

                @Override
                public ItemStack getButtonItem() {
                    return warp.getStack();
                }
            });
        }

        return buttons;
    }
}
