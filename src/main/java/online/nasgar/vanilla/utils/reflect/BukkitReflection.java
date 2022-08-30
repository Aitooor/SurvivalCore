package online.nasgar.vanilla.utils.reflect;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.UUID;

@UtilityClass
public class BukkitReflection {

    public Class<?> GAME_PROFILE_CLASS = Reflection
            .getUntypedClasses("net.minecraft.util.com.mojang.authlib.GameProfile", "com.mojang.authlib.GameProfile");
    public Reflection.ConstructorInvoker GAME_PROFILE_CONSTRUCTOR = Reflection.getConstructor(GAME_PROFILE_CLASS,
            UUID.class, String.class);
    public Reflection.FieldAccessor<?> GAME_PROFILE_PROPERTIES_FIELD = Reflection.getField(GAME_PROFILE_CLASS,
            "properties", Object.class);

    public Class<?> PROPERTY_CLASS = Reflection.getUntypedClasses(
            "net.minecraft.util.com.mojang.authlib.properties.Property", "com.mojang.authlib.properties.Property");
    public Reflection.ConstructorInvoker PROPERTY_CONSTRUCTOR = Reflection.getConstructor(PROPERTY_CLASS, String.class,
            String.class, String.class);

    public Class<?> PROPERTY_MAP_CLASS = Reflection.getUntypedClasses(
            "net.minecraft.util.com.mojang.authlib.properties.PropertyMap",
            "com.mojang.authlib.properties.PropertyMap");
    public Reflection.MethodInvoker PROPERTY_MAP_PUT_METHOD = Reflection.getMethod(PROPERTY_MAP_CLASS, "put",
            String.class, PROPERTY_CLASS);

    public Class<?> CRAFT_PLAYER_CLASS = Reflection.getCraftBukkitClass("entity.CraftPlayer");
    public Reflection.MethodInvoker CRAFT_PLAYER_GET_HANDLE_METHOD = Reflection.getMethod(CRAFT_PLAYER_CLASS, "getHandle", Object.class);

    public Class<?> ENTITY_PLAYER_CLASS = Reflection.getMinecraftClass("EntityPlayer");
    public Reflection.FieldAccessor<Integer> ENTITY_PLAYER_PING_FIELD = Reflection.getField(ENTITY_PLAYER_CLASS, "ping", int.class);

    public int getPing(Player player) {
        return ENTITY_PLAYER_PING_FIELD.get(CRAFT_PLAYER_GET_HANDLE_METHOD.invoke(player));
    }
}
