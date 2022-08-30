package online.nasgar.vanilla.listeners;

import online.nasgar.vanilla.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnersListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onSpawnerBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.SPAWNER) return;

        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand() == null) return;

        ItemStack stack = player.getInventory().getItemInOffHand();
        if (stack == null) return;
        if (stack.getEnchantments() == null || stack.getEnchantments().isEmpty()) return;
        if (!stack.containsEnchantment(Enchantment.SILK_TOUCH)) return;

        CreatureSpawner creature = (CreatureSpawner) block.getState();
        EntityType spawnedEntity = creature.getSpawnedType();


        player.getInventory().addItem(new ItemCreator(Material.SPAWNER).setLore("&e" + spawnedEntity.getName().substring(0, 1).toUpperCase() + spawnedEntity.getName().substring(1).toLowerCase()).toItemStack());
    }
}
