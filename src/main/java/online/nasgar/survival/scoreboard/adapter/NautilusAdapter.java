package online.nasgar.survival.scoreboard.adapter;

import org.bukkit.entity.Player;

import java.util.List;

public interface NautilusAdapter {

	String getTitle(Player player);

	List<String> getContent(Player player);

}