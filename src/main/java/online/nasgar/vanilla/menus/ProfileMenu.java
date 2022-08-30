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

public class ProfileMenu extends Menu {

    private final CachedRemoteModelService<PlayerData> modelService;

    MessageHandler messageHandler = Vanilla.getInstance().getMessageHandler();

    public ProfileMenu(String title, CachedRemoteModelService<PlayerData> modelService) {
        super(title, 5);
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
                        .setTexture("2bcb59d149c0aa4b28796885464c722183b2db774263787970b4b7b138953df5")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                14,
                null,
                SkullBuilder.newBuilder()
                        .setDisplayName("&8➢ &bInfo")
                        .setLore(
                                "",
                                "&eMonedas",
                                "&fTienes " + data.getCoins() + " &fmonedas",
                                "")
                        .setTexture("d01afe973c5482fdc71e6aa10698833c79c437f21308ea9a1a095746ec274a0f")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                16,
                null,
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
                        .setTexture("45c0cd717ca9228849652a8ccdf5281a860cb2e79646b56a22bc59110f361da")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                32,
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
