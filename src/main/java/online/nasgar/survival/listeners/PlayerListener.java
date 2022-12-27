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
import online.nasgar.survival.utils.text.TextUtil;
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
import java.util.List;
import java.util.UUID;

public class PlayerListener implements Listener {

    private final PlayerService playerService;

    public PlayerListener(PlayerService playerService) {
        this.playerService = playerService;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        TaskUtil.runTaskAsync(() -> Bukkit.getOnlinePlayers().forEach(player -> playerService.saveAndRemove(player.getUniqueId().toString())));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        List<String> joinMsg = new ArrayList<>();

        playerService.load(player);

        joinMsg.add(TextUtil.centerText(""));
        joinMsg.add(TextUtil.centerText("&b&lSURVIVAL 1.19"));
        joinMsg.add(TextUtil.centerText(""));
        joinMsg.add(TextUtil.centerText("&a&lWebsite &fhttps://nasgar.online"));
        joinMsg.add(TextUtil.centerText("&a&lTwitter &f@NasgarNetwork"));
        joinMsg.add(TextUtil.centerText("&aDiscord &fhttps://ds.nasgar.online"));
        joinMsg.add(TextUtil.centerText(""));
        joinMsg.add(TextUtil.centerText("&7Welcome &e&l<player>"));
        joinMsg.add(TextUtil.centerText("&aEnjoin the game"));
        joinMsg.add(TextUtil.centerText(""));
        ChatUtil.toPlayer(player, joinMsg);

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
