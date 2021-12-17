package online.nasgar.survival.randomtp.menu;

import online.nasgar.survival.menu.Menu;
import online.nasgar.survival.menu.button.Button;
import online.nasgar.survival.menu.type.MenuType;
import online.nasgar.survival.randomtp.RandomTPManager;
import online.nasgar.survival.utils.CC;
import online.nasgar.survival.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class RandomTPMenu extends Menu {

    public RandomTPMenu() {
        super("RandomTP", 27, MenuType.CHEST);
    }

    @Override
    public String getTitle() {
        return CC.translate("RandomTP");
    }

    @Override
    public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        buttons.add(new Button(13) {
            @Override
            public void onClick(InventoryClickEvent event) {
                event.setCancelled(true);

                RandomTPManager.getInstance().teleportToRandomLocation(player);
            }

            @Override
            public ItemStack getButtonItem() {
                return new ItemCreator(Material.ENDER_EYE).setDisplayName(CC.translate("&c&kiii &b&lRandomTP &c&kiii")).setLore("  ", "&7Click here to begin a teleport to a random location!").toItemStack();
            }
        });

        return buttons;
    }
}
