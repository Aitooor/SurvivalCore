package online.nasgar.survival.menu.impl;

import online.nasgar.survival.menu.Menu;
import online.nasgar.survival.menu.button.Button;
import online.nasgar.survival.menu.type.FillType;
import online.nasgar.survival.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class MainMenu extends Menu {

    public MainMenu() {
        super("&b&lMain &7(Menu)", 5);

        this.setFillEnabled(true);

        this.setFillItemStack(new ItemCreator(Material.STAINED_GLASS_PANE, 1, (short) 4)
                .setDisplayName(" ")
                .toItemStack());

        this.setFillType(FillType.BORDERS);
    }

    @Override public void onOpen(Player player) {
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1.0f, 1.0f);
    }

    @Override public void onClose(Player player) {
        player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 1.0f, 1.0f);
    }

    @Override public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        buttons.add(new ButtonItem(
                18,
                null,
                "profile",
                new ItemCreator(Material.SKULL_ITEM)
        ));

        buttons.add(new ButtonItem(
                9,
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19",
                "discord",
                new ItemCreator(Material.SKULL_ITEM)
        ));

        buttons.add(new ButtonItem(
                27,
                "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDg5NTBiZjdkNWYwODc0OGY2NmI0MzAwNzMyNWM3MTUzNmU0MThkMzU2M2JkMDFhODNmZDhlYjUxNWQ4NjNiMCJ9fX0=",
                "store",
                new ItemCreator(Material.SKULL_ITEM)
        ));

        buttons.add(new ButtonItem(
                19,
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjFjNjNkOWI5ZmQ4NzQyZWFlYjA0YzY5MjE3MmNiOWRhNDM3ODE2OThhNTc1Y2RhYmUxYzA0ZGYxMmMzZiJ9fX0=",
                "wiki",
                new ItemCreator(Material.SKULL_ITEM)
        ));

        buttons.add(new ButtonItem(
                12,
                "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMwYzM4ZTVmNDYwNGNmYmZlOWEzNmExNWVhZDMyM2JmMGY1MzM5MWYzOTY0MGZhYjMwZWFiZDc4YjU4OTkzNyJ9fX0=",
                "lands",
                new ItemCreator(Material.SKULL_ITEM)
        ));

        buttons.add(new ButtonItem(
                14,
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzljODg4MWU0MjkxNWE5ZDI5YmI2MWExNmZiMjZkMDU5OTEzMjA0ZDI2NWRmNWI0MzliM2Q3OTJhY2Q1NiJ9fX0=",
                "warp",
                new ItemCreator(Material.SKULL_ITEM)
        ));

        buttons.add(new ButtonItem(
                16,
                "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTIzYTI3NDJjMzVlYzI3MTU4MWZhOTYwMWQwODRlZmQwZmY0YzMzZDA2YTNjNzk1NzBkYjg3MzNmZmJjMzI2NiJ9fX0=",
                "jobs",
                new ItemCreator(Material.SKULL_ITEM)
        ));

        buttons.add(new ButtonItem(
                22,
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQzYzc5Y2Q5YzJkMzE4N2VhMDMyNDVmZTIxMjhlMGQyYWJiZTc5NDUyMTRiYzU4MzRkZmE0MDNjMTM0ZTI3In19fQ==",
                "battlepass",
                new ItemCreator(Material.SKULL_ITEM)
        ));

        buttons.add(new ButtonItem(
                24,
                "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWE0MGJjZjM2ZjFjNGU1YjE5OTc2NTIxZTg1NzZmMTVhNDgxMjJmNWUxOTdhMmRmNDQ0OGVmNDAyMjJiNDFkZCJ9fX0=",
                "ah",
                new ItemCreator(Material.SKULL_ITEM)
        ));

        buttons.add(new ButtonItem(
                32,
                "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTk3ODdiNjVlZGNiODI4YTFiNTk3ZjQzNjQ5ZjJlNDE3OGVmMmFmMGIyMjBiNzRhZDFkZDZlYTBjNjRjOWU3MSJ9fX0=",
                "resources",
                new ItemCreator(Material.SKULL_ITEM)
        ));

        return buttons;
    }

    private static class ButtonItem extends Button {

        private final String command, skullTexture;
        private final ItemCreator itemCreator;

        public ButtonItem(int slot, String skullTexture, String command, ItemCreator itemCreator) {
            super(slot);

            this.command = command;
            this.skullTexture = skullTexture;
            this.itemCreator = itemCreator;
        }

        @Override public void onClick(InventoryClickEvent event) {
            Player player = (Player) event.getWhoClicked();
            player.performCommand(this.command);
        }

        @Override public ItemStack getButtonItem() {
            return this.itemCreator.setSkullTexture(this.skullTexture).toItemStack();
        }
    }

}
