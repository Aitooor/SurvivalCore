package online.nasgar.survival.scoreboard;

import lombok.Getter;
import online.nasgar.survival.scoreboard.adapter.NautilusAdapter;
import online.nasgar.survival.scoreboard.listener.NautilusListener;
import online.nasgar.survival.scoreboard.thread.NautilusThread;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Nautilus | ScoreboardAPI
 *
 * Author: @OldVnco
 * Data: ScoreboardAPI Forked
 *
 */

@Getter
public class NautilusManager implements Listener {

	private final JavaPlugin plugin;

	private final Nautilus nautilus;
	private final NautilusAdapter adapter;
	private final Map<UUID, Nautilus> nautilusMap;

	private NautilusListener nautilusListener;
	private NautilusThread nautilusThread;

	public NautilusManager(JavaPlugin plugin, NautilusAdapter adapter, long ticks) {
		this.plugin = plugin;
		this.adapter = adapter;

		this.nautilus = new Nautilus(adapter);
		this.nautilusMap = new HashMap<>();
		this.nautilusThread = new NautilusThread(this, ticks);

		this.start();
	}

	private void start(){
		this.nautilusListener = new NautilusListener(this);

		Bukkit.getOnlinePlayers().forEach(player -> this.add(player.getUniqueId()));
		Bukkit.getPluginManager().registerEvents(this.nautilusListener, this.plugin);
	}

	public void stop(){
		this.killTask();

		if (this.nautilusListener != null){
			HandlerList.unregisterAll(this.nautilusListener);
		}

		this.forEach(player -> this.remove(player.getUniqueId()));

		this.nautilus.clear();
	}

	@SuppressWarnings("deprecation")
	private void killTask(){
		if (this.nautilusThread != null) {
			this.nautilusThread.stop();
		}

		this.nautilusThread = null;
	}

	public void add(UUID uuid){
		if (this.contains(uuid)){
			return;
		}

		this.nautilusMap.put(uuid, this.nautilus);
	}

	public void remove(UUID uuid){
		if (!this.contains(uuid)){
			return;
		}

		this.nautilusMap.remove(uuid);
	}

	public boolean contains(UUID uuid){
		return this.nautilusMap.containsKey(uuid);
	}

	public void forEach(Consumer<Player> consumer){
		for (UUID uuid : this.nautilusMap.keySet()){
			Player player = Bukkit.getPlayer(uuid);

			if (player != null){
				consumer.accept(player);
			}
		}
	}

}