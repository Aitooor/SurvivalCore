package online.nasgar.survivalcore.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class CC {

    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', "&bSURVIVAL&7: " + string);
    }

    public static String chat(String string) {
        return ChatColor.translateAlternateColorCodes('&',   string);
    }

    public static String NO_PERMISSIONS = translate("&cYout don't have permissions");

    public static String noPrefix(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }


    public static String serializeItemStackArray(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    public static ItemStack[] deserializeItemStackArray(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ItemStack[0];
    }


    public static String serializeLocation(Location location) {
        StringBuilder serializedData = new StringBuilder();

        serializedData.append(location.getWorld().getName()).append(", ");
        serializedData.append(location.getX()).append(", ");
        serializedData.append(location.getY()).append(", ");
        serializedData.append(location.getZ()).append(", ");
        serializedData.append(location.getYaw()).append(", ");
        serializedData.append(location.getPitch());

        return serializedData.toString();
    }


    public static Location deserializeLocation(String data) {
        String[] splittedData = data.split(", ");

        if (splittedData.length < 6) {
            return null;
        }

        try {
            World world = Bukkit.getWorld(splittedData[0]);
            double x = Double.parseDouble(splittedData[1]);
            double y = Double.parseDouble(splittedData[2]);
            double z = Double.parseDouble(splittedData[3]);
            float yaw = Float.parseFloat(splittedData[4]);
            float pitch = Float.parseFloat(splittedData[5]);

            return new Location(world, x, y, z, yaw, pitch);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
