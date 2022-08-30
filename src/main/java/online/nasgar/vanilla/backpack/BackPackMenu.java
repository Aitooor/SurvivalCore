package online.nasgar.vanilla.backpack;

import net.cosmogrp.storage.ModelService;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class BackPackMenu implements Listener {

    private final ModelService<PlayerData> playerModelCacheService;

    public BackPackMenu(ModelService<PlayerData> playerModelCacheService) {
        this.playerModelCacheService = playerModelCacheService;
    }

    public static void open(Player player, ModelService<PlayerData> modelService) {
        PlayerData playerData = modelService.findSync(player.getUniqueId().toString());
        Inventory inventory = Bukkit.createInventory(null, 27, "BackPack");

        if (playerData.getBackPackItems() != null) inventory.setContents(playerData.getBackPackItems());

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("BackPack")) return;

        Player player = (Player) event.getPlayer();
        PlayerData playerData = playerModelCacheService.findSync(player.getUniqueId().toString());

        playerData.setBackPackItems(event.getInventory().getContents());
    }
}
