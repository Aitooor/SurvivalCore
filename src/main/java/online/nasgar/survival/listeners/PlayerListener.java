package online.nasgar.survival.listeners;

import online.nasgar.survival.Survival;
import online.nasgar.survival.command.GodCommand;
import online.nasgar.survival.command.message.event.MessageEvent;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.playerdata.PlayerDataManager;
import online.nasgar.survival.transaction.TransactionPrice;
import online.nasgar.survival.transaction.event.TransactionEvent;
import online.nasgar.survival.utils.text.ChatUtil;
import online.nasgar.survival.utils.text.BuildText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final PlayerDataManager playerDataManager = Survival.getInstance().getPlayerDataManager();

    @EventHandler public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (this.playerDataManager.get(uuid) == null){
            this.playerDataManager.create(uuid);
        } else {
            this.playerDataManager.load(uuid);
        }

        ChatUtil.toPlayer(player,

                "&7&m-------------------------------------------------------",
                "&b&lNasgar Online&8┃&fSurvival",
                "",
                "&b&lWebsite&7: &fwww.nasgar.online",
                "&b&lTwitter&7: &f@NasgarNetwork",
                "&b&lDiscord&7: &fds.nasgar.online",
                "",
                BuildText.of(player, "<center:Welcome &b&l<player>&f!>"),
                "&7&m-------------------------------------------------------"

        );

        event.setJoinMessage(null);
    }

    @EventHandler public void onPlayerQuit(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId();

        if (this.playerDataManager.get(uuid) != null){
            this.playerDataManager.save(uuid);
        }

        event.setQuitMessage(null);
    }

    @EventHandler public void onMessage(MessageEvent event){
        Player player = event.getPlayer();
        Player target = event.getTarget();
        String message = event.getMessage();

        ChatUtil.toPlayer(player, BuildText.of(target, "&7(To <prefix><player>) " + message));
        ChatUtil.toPlayer(target, BuildText.of(player, "&7(From <prefix><player>) " + message));
    }

    @EventHandler public void onTransaction(TransactionEvent event){
        Player player = event.getPlayer();
        TransactionPrice price = event.getPrice();

        int priceCoins = price.getPrice();

        PlayerData data = this.playerDataManager.get(player.getUniqueId());

        if (data.getCoins() < priceCoins){
            ChatUtil.toPlayer(player, "&cYou do not have sufficient coins!");
            return;
        }

        data.removeCoins(priceCoins);
        price.getCommands().forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (command).replace("<player>", player.getName())));
        ChatUtil.toPlayer(player, "&aSuccessfully purchased " + price.getName() + " &afor &e" + priceCoins + "$");
    }

    @EventHandler public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (GodCommand.contains(player.getUniqueId())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler public void onAsyncPlayerChat(AsyncPlayerChatEvent event){
        event.setFormat(BuildText.of(event.getPlayer(), "&f<prefix><player>&f: %2$s"));
    }

}
