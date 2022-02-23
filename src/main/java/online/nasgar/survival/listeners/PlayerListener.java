package online.nasgar.survival.listeners;

import net.cosmogrp.storage.ModelService;
import net.cosmogrp.storage.redis.connection.Redis;
import online.nasgar.survival.command.GodCommand;
import online.nasgar.survival.command.message.event.MessageEvent;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.playerdata.service.PlayerService;
import online.nasgar.survival.redis.ChatChannelListener;
import online.nasgar.survival.redis.data.MessageData;
import online.nasgar.survival.shop.ShopItem;
import online.nasgar.survival.shop.event.TransactionEvent;
import online.nasgar.survival.utils.TaskUtil;
import online.nasgar.survival.utils.text.BuildText;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerListener implements Listener {

    private final PlayerService playerService;
    private final ModelService<PlayerData> playerCacheModelService;
    private final Redis redis;

    public PlayerListener(PlayerService playerService, ModelService<PlayerData> playerCacheModelService, Redis redis) {
        this.playerService = playerService;
        this.playerCacheModelService = playerCacheModelService;
        this.redis = redis;
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
                "              Welcome &b&l<player>&f!>",
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

        ChatUtil.toPlayer(player, new BuildText(playerCacheModelService).of(target, "&7(To <prefix><player>) " + message));
        ChatUtil.toPlayer(target, new BuildText(playerCacheModelService).of(player, "&7(From <prefix><player>) " + message));
    }

    @EventHandler
    public void onTransaction(TransactionEvent event) {
        Player player = event.getPlayer();
        ShopItem shopItem = event.getShopItem();

        PlayerData data = this.playerCacheModelService.findSync(player.getUniqueId().toString());

        ItemStack itemStack = shopItem.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        String displayName = null;

        int priceCoins = shopItem.getPrice();


        if (itemStack.hasItemMeta() && itemMeta.hasDisplayName()) {
            displayName = itemMeta.getDisplayName();
        }

        if (data.getCoins() < priceCoins) {
            ChatUtil.toPlayer(player, "&cYou do not have sufficient coins!");
            return;
        }

        data.removeCoins(priceCoins);
        shopItem.getCommands().forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (command).replace("<player>", player.getName())));
        ChatUtil.toPlayer(player, "&aSuccessfully purchased " + displayName + " &afor &e" + priceCoins + "$");
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

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        redis.getMessenger().getChannel(ChatChannelListener.CHANNEL_NAME, MessageData.class)
                .sendMessage(
                        new MessageData(
                                player.getUniqueId().toString(),
                                player.getName() + "&7: &f" + event.getMessage()
                        )
                );

        event.setCancelled(true);
    }

}
