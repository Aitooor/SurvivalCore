package online.nasgar.survival.listeners;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.survival.command.admin.GodCommand;
import online.nasgar.survival.command.message.event.MessageEvent;
import online.nasgar.survival.managers.playerdata.PlayerData;
import online.nasgar.survival.services.playerdata.PlayerService;
import online.nasgar.survival.utils.TaskUtil;
import online.nasgar.survival.utils.text.BuildText;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerListener implements Listener {

    private final PlayerService playerService;
    private final CachedRemoteModelService<PlayerData> modelService;
    private final MessageHandler messageHandler;

    public PlayerListener(
            PlayerService playerService,
            MessageHandler messageHandler,
            CachedRemoteModelService<PlayerData> modelService
    ) {
        this.playerService = playerService;
        this.modelService = modelService;
        this.messageHandler = messageHandler;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        TaskUtil.runTaskAsync(() -> Bukkit.getOnlinePlayers().forEach(player -> playerService.saveAndRemove(player.getUniqueId().toString())));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        playerService.load(player);

        ChatUtil.toPlayer(player,

                "&7&m-------------------------------------------------------",
                "              &b&lNasgar Online&8┃&fSurvival",
                "",
                "              &b&lWebsite&7: &fwww.nasgar.online",
                "              &b&lTwitter&7: &f@NasgarNetwork",
                "              &b&lDiscord&7: &fds.nasgar.online",
                "",
                "              Welcome &b&l<player>&f",
                "&7&m-------------------------------------------------------");
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = event.getPlayer().getUniqueId();

        playerService.saveAndRemove(uuid.toString(), data -> {
            data.setItems(player.getInventory().getContents());
            data.setArmor(player.getInventory().getArmorContents());
            data.setHealth(player.getHealth());
            data.setFoodLevel(player.getFoodLevel());
            data.setLevel(player.getLevel());
            data.setEnderChestItems(player.getEnderChest().getContents());
            data.setEffects(new ArrayList<>(player.getActivePotionEffects()));
        });

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

        event.setQuitMessage(null);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        playerService.saveAndRemove(uuid.toString());
    }

    @EventHandler
    public void onMessage(MessageEvent event) {
        Player player = event.getPlayer();
        Player target = event.getTarget();
        String message = event.getMessage();

        ChatUtil.toPlayer(player, new BuildText(modelService).of(target, messageHandler.get(player, "message.prefix.to") + message));
        ChatUtil.toPlayer(target, new BuildText(modelService).of(player, messageHandler.get(player, "message.prefix.from") + message));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (GodCommand.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

}
