package online.nasgar.survival.menu.impl;

import net.cosmogrp.storage.ModelService;
import online.nasgar.survival.Survival;
import online.nasgar.survival.menu.Menu;
import online.nasgar.survival.menu.button.Button;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.ItemCreator;
import online.nasgar.timedrankup.TimedRankup;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class ProfileMenu extends Menu {

    private final ModelService<PlayerData> playerCacheModelService;

    public ProfileMenu(String title, ModelService<PlayerData> playerCacheModelService) {
        super(title, 3);
        this.playerCacheModelService = playerCacheModelService;
    }

    @Override
    public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        PlayerData data = playerCacheModelService.findSync(player.getUniqueId().toString());

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
                                (data.getRank() != null ? data.getRank().getPrefix() : "Default"),
                                "",
                                "&cSiguiente Rango",
                                TimedRankup.getPlugin(TimedRankup.class).getRankManager().getNextApplicable(TimedRankup.getPlugin(TimedRankup.class).getUserManager().get(player.getUniqueId())).getPrefix(),
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

        @Override
        public void onClick(InventoryClickEvent event) {
        }

        @Override
        public ItemStack getButtonItem() {
            return this.itemCreator.toItemStack();
        }
    }
}
