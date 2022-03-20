package online.nasgar.survival.menu.impl;

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

    public MainMenu(String title) {
        super(title, 5);

        this.setFillEnabled(true);

        this.setFillItemStack(new ItemCreator(Material.BLACK_STAINED_GLASS, 1, (short) 4)
                .setDisplayName(" ")
                .toItemStack());

        this.setFillType(FillType.BORDERS);
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
                player.getName(),
                "profile",
                new ItemCreator(Material.PLAYER_HEAD)
                        .setDisplayName("&8➢ &fMenu Perfil")
                        .setLore(
                                "&fAccede a esta opcion para ",
                                "&fmirar tus estadisticas y ",
                                "&Fconfiguraciones ",
                                "",
                                "&cClick para abrir")
        ));

        buttons.add(new ButtonItem(
                10,
                "",
                "discord",
                new ItemCreator(Material.SKULL_BANNER_PATTERN)
                        .setDisplayName("&8➢ &3Discord")
                        .setLore(
                                "&fEntra a nuestro server ",
                                "&fde Discord. ",
                                "&fY conoce a toda nuesta comunidad. ",
                                "",
                                "&cClick para ver")
        ));

        buttons.add(new ButtonItem(
                28,
                "",
                "store",
                new ItemCreator(Material.SKULL_BANNER_PATTERN)
                        .setDisplayName("&8➢ &aTienda")
                        .setLore(
                                "&fVisita nuestra tienda ",
                                "&fy compra un monton ",
                                "&fde cosas nuevas. ",
                                "",
                                "&aClick para ver")
        ));

        buttons.add(new ButtonItem(
                20,
                "",
                "wiki",
                new ItemCreator(Material.LEGACY_SKULL_ITEM)
                        .setDisplayName("&8➢ &eAYUDA")
                        .setLore(
                                "&f¿Tienes preguntas sobre el server? "
                                , "&fentra a esta opcion e informate"
                                , "&Fde todo nuestros comandos y opciones"
                                , ""
                                , "&aClick para abrir")
        ));

        buttons.add(new ButtonItem(
                13,
                "",
                "lands",
                new ItemCreator(Material.LEGACY_SKULL_ITEM)
        ));

        buttons.add(new ButtonItem(
                15,
                "",
                "warps",
                new ItemCreator(Material.LEGACY_SKULL_ITEM)
                        .setDisplayName("&8➢ &dWarps de Jugadores")
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
        ));

        buttons.add(new ButtonItem(
                17,
                "",
                "jobs",
                new ItemCreator(Material.LEGACY_SKULL_ITEM)
                        .setDisplayName("&8➢ &dTrabajos")
                        .setLore(
                                "&fRecolecta dinero",
                                "&fminando, talando, pescando, ...",
                                "&fPor absolutamente todo",
                                "",
                                "&aSin necesidad de entrar a ningun trabajo",
                                "",
                                "&aClick para mas informacion")
        ));

        buttons.add(new ButtonItem(
                23,
                "",
                "battlepass",
                new ItemCreator(Material.LEGACY_SKULL_ITEM)
                        .setDisplayName("&8➢ &dPase de Batalla")
                        .setLore(
                                "&fCompleta los desafios y retos",
                                "&fy consigue las recompensas",
                                "&fpuedes comprar el &epremium &fen la &atienda&f.",
                                "&fO te puede tocar en las &ecajas&f.",
                                "",
                                "&aClick para abrir")
        ));

        buttons.add(new ButtonItem(
                25,
                "",
                "ah",
                new ItemCreator(Material.LEGACY_SKULL_ITEM)
                        .setDisplayName("&8➢ &dCasa de Subastas")
                        .setLore(
                                "&c¿Quieres vender sin ser estafado?",
                                "&fEsta es una opcion muy recomendable",
                                "&fvende, compra y mucho mas de forma segura",
                                "&fEsta es una tienda comunitaria para todos",
                                "",
                                "&aClick para mas informacion")
        ));

        buttons.add(new ButtonItem(
                33,
                "",
                "resources",
                new ItemCreator(Material.LEGACY_SKULL_ITEM)
        ));

        return buttons;
    }

    private static class ButtonItem extends Button {

        private final String command, skullOwner;
        private final ItemCreator itemCreator;

        public ButtonItem(int slot, String skullOwner, String command, ItemCreator itemCreator) {
            super(slot);

            this.command = command;
            this.skullOwner = skullOwner;
            this.itemCreator = itemCreator;
        }

        @Override
        public void onClick(InventoryClickEvent event) {
            Player player = (Player) event.getWhoClicked();
            player.performCommand(this.command);
        }

        @Override
        public ItemStack getButtonItem() {
            if (this.skullOwner.isEmpty()) return this.itemCreator.toItemStack();

            return SkullBuilder.ofItemCreator(this.itemCreator).setOwner(this.skullOwner).setDurability((short) 3).toItemStack();
        }
    }

}
