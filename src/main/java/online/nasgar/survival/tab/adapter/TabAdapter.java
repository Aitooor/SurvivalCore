package online.nasgar.survival.tab.adapter;

import online.nasgar.survival.tab.TabColumn;
import online.nasgar.survival.tab.TabLayout;
import online.nasgar.survival.tab.TabProvider;
import online.nasgar.survival.utils.LuckPermsUtil;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TabAdapter implements TabProvider {

    @Override public Set<TabLayout> getProvider(Player player) {
        Set<TabLayout> layouts = new HashSet<>();
        Set<String> strings = new HashSet<>();

        Bukkit.getOnlinePlayers().stream().filter(OfflinePlayer::isOnline)
                .forEach(players -> strings.add(LuckPermsUtil.getPrefix(players) + players.getName()));

        for (TabColumn column : TabColumn.values()) {
            for (int i = 0; i < 80; i++) {
                int row = i / 4;
                layouts.add(new TabLayout(column, row, String.join("&f", strings)));
            }
        }

        return layouts;
    }

    @Override public List<String> getFooter(Player player) {
        return ChatUtil.translate(Arrays.asList("", "&7Web: &fnasgar.online", "&7Tienda: &f&otienda.nasgar.online", ""));
    }

    @Override public List<String> getHeader(Player player) {
        return ChatUtil.translate(Arrays.asList("&b&lNASGAR NETWORK", "&7Servidor: &aSurvival &7| Jugadores: &f" + Bukkit.getOnlinePlayers().size()));
    }
}
