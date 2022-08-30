package online.nasgar.vanilla.menus;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.vanilla.Vanilla;
import online.nasgar.vanilla.managers.menus.Menu;
import online.nasgar.vanilla.managers.menus.button.Button;
import online.nasgar.vanilla.managers.menus.type.FillType;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import online.nasgar.vanilla.utils.skull.SkullBuilder;
import online.nasgar.vanilla.utils.ItemCreator;
import online.nasgar.timedrankup.TimedRankup;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class WikiMenu extends Menu {

    private final CachedRemoteModelService<PlayerData> modelService;

    MessageHandler messageHandler = Vanilla.getInstance().getMessageHandler();

    public WikiMenu(String title, CachedRemoteModelService<PlayerData> modelService) {
        super(title, 6);
        this.modelService = modelService;

        this.setFillEnabled(true);

        this.setFillItemStack(new ItemCreator(Material.GRAY_STAINED_GLASS_PANE, 1, (short) 4)
                .setDisplayName("&8Nasgar")
                .toItemStack());

        this.setFillType(FillType.ALL);
    }

    @Override
    public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        PlayerData data = modelService.getOrFindSync(player.getUniqueId().toString());

        buttons.add(new ButtonItem(
                12,
                "settings",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.profile.settings.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.profile.settings.lore"))
                        .setTexture("7873c12bffb5251a0b88d5ae75c7247cb39a75ff1a81cbe4c8a39b311ddeda")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                14,
                "",
                SkullBuilder.newBuilder()
                        .setDisplayName("&8➢ &bInfo")
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
                "",
                SkullBuilder.newBuilder()
                        .setDisplayName("&8➢ &bRankup")
                        .setLore(
                                "&aRango Acual",
                                (data.getRank() != null ? data.getRank().getPrefix() : "default"),
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

        buttons.add(new ButtonItem(
                50,
                "menu",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.back"))
                        .setTexture("86e145e71295bcc0488e9bb7e6d6895b7f969a3b5bb7eb34a52e932bc84df5b")
                        .toItemStack()
        ));

        return buttons;
    }


    private static class ButtonItem extends Button {

        private final String command;
        private final ItemStack item;

        public ButtonItem(int slot, String command, ItemStack item) {
            super(slot);

            this.command = command;
            this.item = item;
        }

        @Override
        public void onClick(InventoryClickEvent event) {
            Player player = (Player) event.getWhoClicked();
            if(this.command == null) {
                return;
            }
            player.performCommand(this.command);
        }

        @Override
        public ItemStack getButtonItem() {
            return this.item;
        }
    }
}
