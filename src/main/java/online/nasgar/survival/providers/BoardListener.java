package online.nasgar.survival.providers;

import fr.mrmicky.fastboard.FastBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.ModelService;
import net.cosmogrp.storage.mongo.MongoModelService;
import online.nasgar.survival.Survival;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.CC;
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

public class BoardListener implements Listener {

    private final Map<UUID, FastBoard> boards = new HashMap<>();

    private final MongoModelService<PlayerData> playerDataMongoModelService;

    private final ModelService<PlayerData> playerCacheModelService;

    MessageHandler messageHandler = Survival.getInstance().getMessageHandler();

    public BoardListener(MongoModelService<PlayerData> playerDataMongoModelService, ModelService<PlayerData> playerCacheModelService) {
        this.playerDataMongoModelService = playerDataMongoModelService;
        this.playerCacheModelService = playerCacheModelService;
        Bukkit.getScheduler().runTaskTimerAsynchronously(Survival.getInstance(), () -> this.boards.values().forEach(this::updateBoard), 20L, 20L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);

        board.updateTitle(CC.translate("&b&lSURVIVAL &8| &7&o1.18"));

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

        String rank = "%vault_prefix%";
        rank = PlaceholderAPI.setPlaceholders(player, rank);

        //TODO Change this method to another one using bungeecord messagechannel
        String survival1 = "%bungee_Survival-1%";
        survival1 = PlaceholderAPI.setPlaceholders(player, survival1);
        int survival1Int = Integer.parseInt(survival1);
        String survival2 = "%bungee_Survival-2%";
        survival2 = PlaceholderAPI.setPlaceholders(player, survival2);
        int survival2Int = Integer.parseInt(survival2);

        int survivalCountInt = survival1Int + survival2Int;

        String money = new BuildText(playerDataMongoModelService).of(player, messageHandler.get(player, "coins.have.numbered"));

        PlayerData data = playerCacheModelService.getOrFindSync(player.getUniqueId().toString());

        String rankup = (data.getRank() != null ? data.getRank().getPrefix() : "Default");

        board.updateLines(messageHandler.replacingMany(player, "scoreboard.lines",
                "%player%", player.getName(),
                "%rank%", rank,
                "%rankup%", rankup,
                "%survival_online%", survivalCountInt,
                "%money%", money));
    }
}