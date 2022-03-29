package online.nasgar.survival.skull;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.cosmogrp.commons.bukkit.server.ServerVersion;
import online.nasgar.survival.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public class SkullBuilder extends ItemCreator {

    private final static String MINECRAFT_TEXTURE_URL = "http://textures.minecraft.net/texture/%s";

    private String owner;
    private String url;

    private SkullBuilder() {
        super(Material.PLAYER_HEAD, 0, (short) 0);
    }

    private SkullBuilder(ItemCreator itemCreator) {
        super(itemCreator.toItemStack());
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

    public static SkullBuilder ofItemCreator(ItemCreator itemCreator) {
        return new SkullBuilder(itemCreator);
    }
}
