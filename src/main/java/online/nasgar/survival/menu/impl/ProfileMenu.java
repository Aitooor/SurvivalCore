package online.nasgar.survival.menu.impl;

import online.nasgar.survival.Survival;
import online.nasgar.survival.menu.Menu;
import online.nasgar.survival.menu.button.Button;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class ProfileMenu extends Menu {

    public ProfileMenu() {
        super("&8➢ &ePERFIL", 3);
    }

    @Override public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        PlayerData data = Survival.getInstance().getPlayerDataManager().get(player.getUniqueId());

        buttons.add(new ButtonItem(12,
                new ItemCreator(Material.BOOKSHELF)
                        .setDisplayName("&8➢ &eAjustes")
                        .setLore("",
                                "&fMira los ajustes generales",
                                "&fAjustalos&f, &eInfo &fy &aRankUP",
                                "",
                                "&fClick para abrir",
                                "")));

        buttons.add(new ButtonItem(14,
                new ItemCreator(Material.BOOKSHELF)
                        .setDisplayName("&8➢ &aInfo")
                        .setLore(
                                "",
                                "&eMonedas",
                                "&fTienes " + data.getCoins() + " &fmonedas",
                                "")));

        buttons.add(new ButtonItem(16,
                new ItemCreator(Material.BOOKSHELF)
                        .setDisplayName("&8➢ &bRankup")
                        .setLore(
                                "&aRango Acual",
                                data.getRank().getPrefix(),
                                "",
                                "&cSiguiente Rango",
                                Survival.getInstance().getRankManager().getNextApplicable(data).getPrefix(),
                                "",
                                "&fTodos los Rangos",
                                "&fClick para abrir",
                                "")
        ));

        return buttons;
    }


    private static class ButtonItem extends Button {

        private final ItemCreator itemCreator;

        public ButtonItem(int slot, ItemCreator itemCreator) {
            super(slot);
            this.itemCreator = itemCreator;
        }

        @Override public void onClick(InventoryClickEvent event) {
        }

        @Override public ItemStack getButtonItem() {
            return this.itemCreator.toItemStack();
        }
    }
}
