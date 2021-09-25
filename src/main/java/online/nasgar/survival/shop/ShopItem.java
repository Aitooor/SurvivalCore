package online.nasgar.survival.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter @AllArgsConstructor
public class ShopItem {

    private final ItemStack itemStack;
    private final int price;
    private final List<String> commands;

}
