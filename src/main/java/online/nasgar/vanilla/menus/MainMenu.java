package online.nasgar.vanilla.menus;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.Vanilla;
import online.nasgar.vanilla.managers.menus.Menu;
import online.nasgar.vanilla.managers.menus.button.Button;
import online.nasgar.vanilla.managers.menus.type.FillType;
import online.nasgar.vanilla.utils.skull.SkullBuilder;
import online.nasgar.vanilla.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class MainMenu extends Menu {

    MessageHandler messageHandler = Vanilla.getInstance().getMessageHandler();

    public MainMenu(String title) {
        super(title, 6);

        this.setFillEnabled(true);

        this.setFillItemStack(new ItemCreator(Material.GRAY_STAINED_GLASS_PANE, 1, (short) 4)
                        .setDisplayName("&8Nasgar")
                .toItemStack());

        this.setFillType(FillType.ALL);
    }

    @Override
    public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        buttons.add(new ButtonItem(
                19,
                "profile",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.profile.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.profile.lore"))
                        .setOwner(player.getName())
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                10,
                "discord",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.discord.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.discord.lore"))
                        .setTexture("7873c12bffb5251a0b88d5ae75c7247cb39a75ff1a81cbe4c8a39b311ddeda")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                28,
                "shop",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.shop.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.shop.lore"))
                        .setTexture("7406e45318e9a4a6bfe132f202fe3ceac15d11eaedbef1eb06a376db433090a8")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                20,
                "wiki",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.wiki.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.wiki.lore"))
                        .setTexture("7dc985a7a68c574f683c0b859521feb3fc3d2ffa05fa09db0bae44b8ac29b385")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                13,
                "lands",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.protections.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.protections.lore"))
                        .setTexture("63d02cdc075bb1cc5f6fe3c7711ae4977e38b910d50ed6023df73913e5e7fcff")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                15,
                "pwarps",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.player-warps.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.player-warps.lore"))
                        .setTexture("77400ea19dbd84f75c39ad6823ac4ef786f39f48fc6f84602366ac29b837422")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                17,
                "jobs browse",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.jobs.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.jobs.lore"))
                        .setTexture("69a600ab0a83097065b95ae284f8059961774609adb3dbd3a4ca269d44409551")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                23,
                "pass",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.battlepass.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.battlepass.lore"))
                        .setTexture("ea62b9de6a26b86869ca22ea40f1bde80a0430a54547becce8fda8707777258f")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                25,
                "auction menu",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.auction.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.auction.lore"))
                        .setTexture("ef835b8941fe319931749b87fe8e84c5d1f4a271b5fbce5e700a60004d881f79")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                31,
                "warps",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.warps.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.warps.lore"))
                        .setTexture("5cb7c21cc43dc17678ee6f16591ffaab1f637c37f4f6bbd8cea497451d76db6d")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                33,
                "resources",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.resources.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.resources.lore"))
                        .setTexture("1289d5b178626ea23d0b0c3d2df5c085e8375056bf685b5ed5bb477fe8472d94")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                35,
                "koths",
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.main.koths.title"))
                        .setLore(messageHandler.replacingMany(player, "guis.main.koths.lore"))
                        .setTexture("46fbf6b97c3bdab7062beb62793c672e65b8b11ee56a3d52387497b07449504b")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                50,
                null,
                SkullBuilder.newBuilder()
                        .setDisplayName(messageHandler.replacing(player, "guis.exit"))
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
            player.closeInventory();
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
