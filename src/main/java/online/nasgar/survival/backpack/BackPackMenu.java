package online.nasgar.survival.backpack;

import online.nasgar.survival.Survival;
import online.nasgar.survival.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class BackPackMenu implements Listener {

    public static void open(Player player) {
        PlayerData playerData = Survival.getInstance().getPlayerDataManager().get(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, 27, "BackPack");

        if (playerData.getBackPackItems() != null) inventory.setContents(playerData.getBackPackItems());

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("BackPack")) return;

        Player player = (Player) event.getPlayer();
        PlayerData playerData = Survival.getInstance().getPlayerDataManager().get(player.getUniqueId());

        playerData.setBackPackItems(event.getInventory().getContents());
    }
}
