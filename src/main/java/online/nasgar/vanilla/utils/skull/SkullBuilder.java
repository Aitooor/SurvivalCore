package online.nasgar.vanilla.utils.skull;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import online.nasgar.vanilla.utils.CC;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class SkullBuilder {

    private final static String MINECRAFT_TEXTURE_URL = "http://textures.minecraft.net/texture/%s";

    private String owner;
    private String url;
    private String displayName;
    private List<String> lore;

    public SkullBuilder setDisplayName(String displayName) {
        this.displayName = CC.translate(displayName);

        return this;
    }

    public SkullBuilder setLore(List<String> lore) {
        this.lore = CC.translate(lore);

        return this;
    }

    public SkullBuilder setLore(String... lore) {
        return setLore(
                Arrays.asList(lore)
        );
    }



    public SkullBuilder setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public SkullBuilder setUrl(String url) {
        this.url = url;

        return this;
    }

    public SkullBuilder setTexture(String texture) {
        return setUrl(String.format(MINECRAFT_TEXTURE_URL, texture));
    }

    public ItemStack toItemStack() {
        ItemStack itemStack =  new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();


        if (displayName != null) {
            skullMeta.setDisplayName(CC.translate(displayName));
        }

        if (lore != null) {
            skullMeta.setLore(CC.translate(lore));
        }

        if (owner != null) {
            skullMeta.setOwner(owner);
        } else if (url != null) {
            Field profileField;

            try {
                profileField = skullMeta.getClass().getDeclaredField("profile");
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("SkullMeta.profile field not found", e);
            }

            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
            byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());

            gameProfile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            boolean accessible = profileField.isAccessible();
            profileField.setAccessible(true);

            try {
                profileField.set(skullMeta, gameProfile);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                profileField.setAccessible(accessible);
            }

        }

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public static SkullBuilder newBuilder() {
        return new SkullBuilder();
    }
}
