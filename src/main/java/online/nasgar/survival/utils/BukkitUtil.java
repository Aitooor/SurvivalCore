package online.nasgar.survival.utils;

import java.io.*;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.*;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class BukkitUtil {

	/**
	 * Transforms an {@link ItemStack} array to a {@link String} in Base64
	 * 
	 * @param items the {@link ItemStack} array to serialize
	 * @return the item {@link String}
	 */
	public String serializeItemStackArray(ItemStack[] items) {
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

	/**
	 * Transforms a {@link String} in Base64 to an {@link ItemStack} array
	 * 
	 * @param data the {@link String} to deserialize
	 * @return a {@link ItemStack} array instance
	 */
	public ItemStack[] deserializeItemStackArray(String data) {
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


}
