package online.nasgar.survival.utils;

import lombok.Getter;
import lombok.Setter;
import online.nasgar.survival.utils.reflect.BukkitReflection;
import online.nasgar.survival.utils.reflect.Reflection;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.*;

@Getter
@Setter
public class ItemCreator {

    private static ItemStack itemStack;
    private static ItemMeta itemMeta;

    public ItemCreator(Material material) {
        this(material, 1, (short) 0);
    }

    public ItemCreator(Material material, int amount, short data) {
        this(new ItemStack(material, amount, data));
    }

    public ItemCreator(ItemStack itemStack) {
        ItemCreator.itemStack = itemStack;
        itemMeta = itemStack.getItemMeta();
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
        itemStack = new ItemStack(material, amount, data);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (display != null) {
            itemMeta.setDisplayName(display);
        }
        if (lore != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void setDisplayName(ItemStack itemStack, String displayName) {
        itemMeta.setDisplayName(ChatUtil.translate(displayName));
        itemStack.setItemMeta(itemMeta);
    }

    public static ItemStack setLore(ItemStack itemStack, String... lore) {
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, lore);
        return setLore(itemStack, list);
    }

    public static ItemStack setLore(ItemStack itemStack, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(ChatUtil.translate(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void setUnbreakable(ItemStack itemStack, boolean unbreakable) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);
    }

    public static void setSkullOwner(ItemStack itemStack, String skullOwner) {
        if (itemStack.getType() == Material.SKULL_BANNER_PATTERN) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setOwner(skullOwner);
            itemStack.setItemMeta(skullMeta);
        }
    }

    public static void addUnbreaking(Player player) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null) {
                if (itemStack.getType() != Material.AIR) {
                    Material type = itemStack.getType();
                    if (type == Material.WOODEN_SWORD || type == Material.STONE_SWORD || type == Material.GOLDEN_SWORD || type == Material.IRON_SWORD || type == Material.DIAMOND_SWORD || type == Material.BOW || type == Material.DIAMOND_HELMET || type == Material.DIAMOND_CHESTPLATE || type == Material.DIAMOND_LEGGINGS || type == Material.DIAMOND_BOOTS) {
                        setUnbreakable(itemStack, true);
                    }
                }
            }
        }
    }

    public static void removeUnbreaking(Player player) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null) {
                if (itemStack.getType() != Material.AIR) {
                    Material type = itemStack.getType();
                    if (type == Material.WOODEN_SWORD || type == Material.STONE_SWORD || type == Material.GOLDEN_SWORD || type == Material.IRON_SWORD || type == Material.DIAMOND_SWORD || type == Material.BOW) {
                        setUnbreakable(itemStack, false);
                    }
                }
            }
        }
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
        ItemCreator.itemStack = new ItemStack(material);
        return this;
    }

    public ItemCreator setAmount(int amount) {
        ItemCreator.itemStack.setAmount(amount);
        return this;
    }

    public ItemCreator setDisplayName(String displayName) {
        itemMeta.setDisplayName(ChatUtil.translate(displayName));
        ItemCreator.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemCreator setLore(List<String> lore) {
        ItemMeta itemMeta = ItemCreator.itemStack.getItemMeta();
        itemMeta.setLore(ChatUtil.translate(lore));
        ItemCreator.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemCreator setLore(String... lore) {
        List<String> realLore = new ArrayList<>();
        Collections.addAll(realLore, lore);
        setLore(ItemCreator.itemStack, realLore);
        return this;
    }

    public ItemCreator setColor(org.bukkit.Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
        leatherArmorMeta.setColor(color);
        itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }

    public ItemCreator setSkullOwner(String skullOwner) {
        if (itemStack.getType() == Material.SKULL_BANNER_PATTERN) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setOwner(skullOwner);
            itemStack.setItemMeta(skullMeta);
        }
        return this;
    }

    public ItemCreator setSkullTexture(String texture) {
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        if (texture == null) {
            texture = "ewogICJ0aW1lc3RhbXAiIDogMTYxODI4Nzc3MDYxNSwKICAicHJvZmlsZUlkIiA6ICI4NjY3YmE3MWI4NWE0MDA0YWY1NDQ1N2E5NzM0ZWVkNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdGV2ZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82MGE1YmQwMTZiM2M5YTFiOTI3MmU0OTI5ZTMwODI3YTY3YmU0ZWJiMjE5MDE3YWRiYmM0YTRkMjJlYmQ1YjEiCiAgICB9LAogICAgIkNBUEUiIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzk1M2NhYzhiNzc5ZmU0MTM4M2U2NzVlZTJiODYwNzFhNzE2NThmMjE4MGY1NmZiY2U4YWEzMTVlYTcwZTJlZDYiCiAgICB9CiAgfQp9";
        }

        if (!Reflection.VERSION.contains("v1_7")) {
            if (itemStack.getType().equals(Material.SKULL_BANNER_PATTERN)) {
                texture = "http://textures.minecraft.net/texture/" + texture;
                byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes());

                Object profile = BukkitReflection.GAME_PROFILE_CONSTRUCTOR.invoke(UUID.randomUUID(), "MHF_HEAD");
                BukkitReflection.PROPERTY_MAP_PUT_METHOD.invoke(BukkitReflection.GAME_PROFILE_PROPERTIES_FIELD.get(profile),
                        "textures", BukkitReflection.PROPERTY_CONSTRUCTOR.invoke("textures", new String(encodedData)));

                Reflection.FieldAccessor<?> profileField = Reflection.getField(skullMeta.getClass(), "profile",
                        BukkitReflection.GAME_PROFILE_CLASS);

                profileField.set(skullMeta, profile);
            }
        }

        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemCreator setDurability(ItemStack itemStack, short durability) {
        itemStack.setDurability(durability);
        return this;
    }

    public ItemStack toItemStack() {
        return itemStack;
    }
}
