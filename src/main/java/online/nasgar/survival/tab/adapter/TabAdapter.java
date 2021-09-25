package online.nasgar.survival.tab.adapter;

import online.nasgar.survival.tab.TabColumn;
import online.nasgar.survival.tab.TabLayout;
import online.nasgar.survival.tab.TabProvider;
import online.nasgar.survival.utils.LuckPermsUtil;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class TabAdapter implements TabProvider {

    @Override public Set<TabLayout> getProvider(Player player) {
        Set<TabLayout> layouts = new HashSet<>();
        Set<Player> players = new HashSet<>(Bukkit.getOnlinePlayers());

        int i = 0;
        Iterator<Player> iterator = players.iterator();

        while (iterator.hasNext() && i++ < 80){
            for (TabColumn column : TabColumn.values()) {
                int row = i / 4;
                layouts.add(new TabLayout(column, row, String.join("&f", LuckPermsUtil.getPrefix(iterator.next()) + iterator.next().getName())));
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
