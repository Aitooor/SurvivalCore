package online.nasgar.survival.listeners.providers;

import online.nasgar.survival.managers.scoreboard.FastBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.survival.Survival;
import online.nasgar.survival.managers.playerdata.PlayerData;
import online.nasgar.survival.utils.text.BuildText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardListener implements Listener {

    private final Map<UUID, FastBoard> boards = new HashMap<>();

    private final CachedRemoteModelService<PlayerData> modelService;

    MessageHandler messageHandler = Survival.getInstance().getMessageHandler();

    public ScoreboardListener(CachedRemoteModelService<PlayerData> modelService) {
        this.modelService = modelService;
        Bukkit.getScheduler().runTaskTimerAsynchronously(Survival.getInstance(), () -> this.boards.values().forEach(this::updateBoard), 20L, 20L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);

        board.updateTitle(messageHandler.replacing(player, "scoreboard.title"));

        this.boards.put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        FastBoard board = this.boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    private void updateBoard(FastBoard board) {

        Player player = board.getPlayer();

        String rank = PlaceholderAPI.setPlaceholders(player, "%vault_prefix%");
        rank = PlaceholderAPI.setPlaceholders(player, rank);

        String money = new BuildText(modelService).of(player, messageHandler.get(player, "coins.have.numbered"));
        PlayerData data = modelService.getOrFindSync(player.getUniqueId().toString());
        String rankup = (data.getRank() != null ? data.getRank().getPrefix() : "default");

        board.updateLines(PlaceholderAPI.setPlaceholders(player, messageHandler.replacingMany(player, "scoreboard.lines",
                "%player%", player.getName(),
                "%prefix%", rank,
                "%rankup%", rankup,
                "%money%", money)));
    }
}