package online.nasgar.vanilla.utils;

import lombok.Getter;
import lombok.Setter;
import online.nasgar.vanilla.utils.text.ChatUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

@Getter
@Setter
public class ItemCreator {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    protected String displayName;
    protected List<String> lore;

    public ItemCreator(Material material) {
        this(material, 1, (short) 0);
    }

    public ItemCreator(Material material, int amount, short data) {
        this(new ItemStack(material, amount, data));
    }

    public ItemCreator(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public static ItemStack makeItem(Material material) {
        return makeItem(material, 1, (short) 0, null, (String) null);
    }

    public static ItemStack makeItem(Material material, int amount) {
        return makeItem(material, amount, (short) 0, null, (String) null);
    }

    public static ItemStack makeItem(Material material, int amount, short data) {
        return makeItem(material, amount, data, null, (String) null);
    }

    public static ItemStack makeItem(Material material, int amount, short data, String display) {
        return makeItem(material, amount, data, display, (String) null);
    }

    public static ItemStack makeItem(Material material, int amount, short data, String display, String lore) {
        List<String> realLore = new ArrayList<>();
        Collections.addAll(realLore, lore);
        return makeItem(material, amount, data, display, realLore);
    }

    public static ItemStack makeItem(Material material, int amount, short data, String display, List<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount, data);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (display != null) {
            itemMeta.setDisplayName(CC.translate(display));
        }
        if (lore != null) {
            itemMeta.setLore(CC.translate(lore));
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void setDisplayName(ItemStack itemStack, String displayName) {
        this.displayName = displayName;

        itemMeta.setDisplayName(ChatUtil.translate(displayName));
        itemStack.setItemMeta(itemMeta);
    }

    public ItemCreator setLore(String... lore) {
        List<String> list = Arrays.asList(lore);

        return setLore(list);
    }

    public ItemCreator setLore(List<String> lore) {
        this.lore = lore;

        itemMeta.setLore(ChatUtil.translate(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public void setUnbreakable(ItemStack itemStack, boolean unbreakable) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);
    }

    public static void addEnchant(ItemStack itemStack, Enchantment enchantment, int level) {
        if (itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.addEnchant(enchantment, level, true);
            itemStack.setItemMeta(itemMeta);
        }
    }

    public static ItemStack getEnchantedBook(Enchantment enchantment, int level) {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        itemMeta.addStoredEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void setColor(ItemStack itemStack, org.bukkit.Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        itemStack.setItemMeta(leatherArmorMeta);
    }

    public static void setColor(ItemStack[] itemStacks, org.bukkit.Color color) {
        for (ItemStack itemStack : itemStacks) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
            leatherArmorMeta.setColor(color);
            itemStack.setItemMeta(leatherArmorMeta);
        }
    }

    public static void setRgbColor(ItemStack[] itemStacks, int red, int green, int blue) {
        for (ItemStack itemStack : itemStacks) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
            leatherArmorMeta.setColor(org.bukkit.Color.fromRGB(red, green, blue));
            itemStack.setItemMeta(leatherArmorMeta);
        }
    }

    public static void setRgbColor(ItemStack itemStack, int red, int green, int blue) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        leatherArmorMeta.setColor(org.bukkit.Color.fromRGB(red, green, blue));
        itemStack.setItemMeta(leatherArmorMeta);
    }

    public ItemCreator setMaterial(Material material) {
        itemStack = new ItemStack(material);
        return this;
    }

    public ItemCreator setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemCreator setDisplayName(String displayName) {
        itemMeta.setDisplayName(ChatUtil.translate(displayName));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemCreator setColor(org.bukkit.Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
        leatherArmorMeta.setColor(color);
        itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }

    public ItemCreator setDurability(short durability) {
        itemStack.setDurability(durability);
        return this;
    }

    public ItemStack toItemStack() {
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
