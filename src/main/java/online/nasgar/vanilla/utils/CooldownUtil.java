package online.nasgar.vanilla.utils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public class CooldownUtil {

    private final Table<String, String, Long> table = HashBasedTable.create();

    public long getCooldown(Player player, String cooldownName) {
        return calculateRemainder(table.get(player.getName(), cooldownName));
    }

    public void setCooldown(Player player, String cooldownName, long time) {
        calculateRemainder(table.put(player.getName(), cooldownName, System.currentTimeMillis() + time));
    }

    public boolean hasCooldown(Player player, String cooldownName) {
        if (getCooldown(player, cooldownName) <= 0) {
            setCooldown(player, cooldownName, getTimeByCooldownName(cooldownName));
            return false;
        }

        return true;
    }

    public void removeAllCooldowns(Player player) {
        table.row(player.getName()).clear();
    }

    private long calculateRemainder(Long expireTime) {
        return expireTime != null ? expireTime - System.currentTimeMillis() : Long.MIN_VALUE;
    }

    private long getTimeByCooldownName(String cooldownName) {
        for (Table.Cell<String, String, Long> cell : table.cellSet()) {
            if (cell.getColumnKey().equalsIgnoreCase(cooldownName)) {
                return cell.getValue();
            }
        }

        return 0L;
    }
}
