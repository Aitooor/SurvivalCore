package online.nasgar.vanilla.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemUtils {
    public static String serialize(ItemStack itemStack) {
        StringBuilder serialized = new StringBuilder();
        serialized.append("material:").append(itemStack.getType()).append(";")
                .append("durability:").append(itemStack.getDurability()).append(";")
                .append("amount:").append(itemStack.getAmount()).append(";");
        if (itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta.hasDisplayName())
                serialized.append("name:").append(meta.getDisplayName()).append(";");
            if (meta.hasLore())
                serialized.append("lore:").append(String.join(",", meta.getLore())).append(";");
        }
        return serialized.toString();
    }

    public static ItemStack deSerialized(String string) {
        ItemCreator itemBuilder = null;
        String[] serializedSplit = string.split(";");
        for (String str : serializedSplit) {
            if (str.contains("material")) {
                String material = str.replace("material:", "");
                itemBuilder = new ItemCreator(Material.valueOf(material));
            } else if (str.contains("durability")) {
                String durabity = str.replace("durability:", "");
                itemBuilder.setDurability(Short.parseShort(durabity));
            } else if (str.contains("name")) {
                String name = str.replace("name:", "");
                itemBuilder.setDisplayName(name);
            } else if (str.contains("lore")) {
                String lore = str.replace("lore:", "");
                itemBuilder.setLore(Arrays.asList(lore.split(",")));
            } else if (str.contains("amount")) {
                itemBuilder.setAmount(Integer.parseInt(str.replace("amount:", "")));
            }
        }
        return itemBuilder.toItemStack();
    }
}
