package online.nasgar.survival.menu.impl;

import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.survival.menu.Menu;
import online.nasgar.survival.menu.button.Button;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.skull.SkullBuilder;
import online.nasgar.timedrankup.TimedRankup;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class ProfileMenu extends Menu {

    private final CachedRemoteModelService<PlayerData> modelService;

    public ProfileMenu(String title, CachedRemoteModelService<PlayerData> modelService) {
        super(title, 3);
        this.modelService = modelService;
    }

    @Override
    public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        PlayerData data = modelService.getOrFindSync(player.getUniqueId().toString());

        buttons.add(new ButtonItem(12,
                SkullBuilder.newBuilder()
                        .setDisplayName("&8➢ &eAjustes")
                        .setLore("",
                                "&fMira los ajustes generales",
                                "&fAjustalos&f, &eInfo &fy &aRankUP",
                                "",
                                "&fClick para abrir",
                                "")
                        .setTexture("7873c12bffb5251a0b88d5ae75c7247cb39a75ff1a81cbe4c8a39b311ddeda")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(14,
                SkullBuilder.newBuilder()
                        .setDisplayName("&8➢ &aInfo")
                        .setLore(
                                "",
                                "&eMonedas",
                                "&fTienes " + data.getCoins() + " &fmonedas",
                                "")
                        .setTexture("7873c12bffb5251a0b88d5ae75c7247cb39a75ff1a81cbe4c8a39b311ddeda")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                16,
                SkullBuilder.newBuilder()
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
                        .setTexture("7873c12bffb5251a0b88d5ae75c7247cb39a75ff1a81cbe4c8a39b311ddeda")
                        .toItemStack()
        ));

        return buttons;
    }


    private static class ButtonItem extends Button {

        private final ItemStack item;

        public ButtonItem(int slot, ItemStack item) {
            super(slot);
            this.item = item;
        }

        @Override
        public void onClick(InventoryClickEvent event) {
        }

        @Override
        public ItemStack getButtonItem() {
            return this.item;
        }
    }
}
