package online.nasgar.survival.menu.impl;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.Survival;
import online.nasgar.survival.menu.Menu;
import online.nasgar.survival.menu.button.Button;
import online.nasgar.survival.menu.type.FillType;
import online.nasgar.survival.skull.SkullBuilder;
import online.nasgar.survival.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class MainMenu extends Menu {

    MessageHandler messageHandler = Survival.getInstance().getMessageHandler();

    public MainMenu(String title) {
        super(title, 5);

        this.setFillEnabled(true);

        this.setFillItemStack(new ItemCreator(Material.BLACK_STAINED_GLASS, 1, (short) 4)
                        .setDisplayName("&8Nasgar")
                .toItemStack());

        this.setFillType(FillType.ALL);
    }

    @Override
    public void onOpen(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
    }

    @Override
    public void onClose(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 1.0f);
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
                "store",
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
                "warps",
                SkullBuilder.newBuilder()
                        .setDisplayName("&8➢ &bWarps")
                        .setLore(
                                "&c¿Que es esto?",
                                "&fCualquier jugador puede",
                                "&fcrear su propio warp.",
                                "&fPara poder ser visitado por toda la comunidad",
                                "",
                                "&c¿Como puedo colocar uno?",
                                "&fPuedes crear tu propio warp",
                                "&fUsado el comando &b/pwarp set &7(nombre)",
                                "",
                                "&aClick para mas informacion")
                        .setTexture("77400ea19dbd84f75c39ad6823ac4ef786f39f48fc6f84602366ac29b837422")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                17,
                "jobs",
                SkullBuilder.newBuilder()
                        .setDisplayName("&8➢ &bTrabajos")
                        .setLore(
                                "&fRecolecta dinero",
                                "&fminando, talando, pescando, ...",
                                "&fPor absolutamente todo",
                                "",
                                "&aSin necesidad de entrar a ningun trabajo",
                                "",
                                "&aClick para mas informacion")
                        .setTexture("69a600ab0a83097065b95ae284f8059961774609adb3dbd3a4ca269d44409551")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                23,
                "battlepass",
                SkullBuilder.newBuilder()
                        .setDisplayName("&8➢ &bPase de Batalla")
                        .setLore(
                                "&fCompleta los desafios y retos",
                                "&fy consigue las recompensas",
                                "&fpuedes comprar el &epremium &fen la &atienda&f.",
                                "&fO te puede tocar en las &ecajas&f.",
                                "",
                                "&aClick para abrir")
                        .setTexture("ea62b9de6a26b86869ca22ea40f1bde80a0430a54547becce8fda8707777258f")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                25,
                "auction menu",
                SkullBuilder.newBuilder()
                        .setDisplayName("&8➢ &bCasa de Subastas")
                        .setLore(
                                "&c¿Quieres comprar algo?",
                                "&fEsta es una opcion muy recomendable",
                                "&fvende, compra y mucho mas de forma segura",
                                "&fEsta es una tienda comunitaria para todos",
                                "",
                                "&aClick para mas informacion")
                        .setTexture("ef835b8941fe319931749b87fe8e84c5d1f4a271b5fbce5e700a60004d881f79")
                        .toItemStack()
        ));

        buttons.add(new ButtonItem(
                33,
                "resources",
                SkullBuilder.newBuilder()
                        .setDisplayName("&8➢ &bRecursos")
                        .setTexture("27957f895d7bc53423a35aac59d584b41cc30e040269c955e451fe680a1cc049")
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
            player.performCommand(this.command);
        }

        @Override
        public ItemStack getButtonItem() {
            return this.item;
        }
    }

}
