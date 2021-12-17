package online.nasgar.survival.shop;

import lombok.Getter;
import online.nasgar.survival.Survival;
import online.nasgar.survival.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ShopItemManager {

    private final Map<String, ShopItem> itemMap;

    public ShopItemManager() {
        this.itemMap = new HashMap<>();

        this.setup();
    }

    private void setup() {
        this.itemMap.clear();

        ConfigurationSection section = Survival.getInstance().getConfigFile().getConfigurationSection("shop-items");

        for (String key : section.getKeys(false)) {
            this.itemMap.put(key, new ShopItem(
                    new ItemCreator(Material.getMaterial(section.getString(key + ".item.material")),
                            section.getInt(key + ".item.amount"),
                            (short) section.getInt(key + "item.data"))

                            .setDisplayName(section.getString(key + ".item.display-name"))
                            .setLore(section.getStringList(key + ".item.description")).toItemStack(),

                    section.getInt(key + ".price"),
                    section.getStringList(key + ".commands")));
        }
    }

    public ShopItem getByName(String itemName) {
        return this.itemMap.get(itemName);
    }

}
