package online.nasgar.survival.playerdata;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import online.nasgar.survival.Survival;
import online.nasgar.timedrankup.TimedRankup;
import online.nasgar.timedrankup.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Getter @Setter
public class PlayerData {

    private UUID uuid;
    private String lastConverser;
    private List<String> ignoredPlayers;

    private double xp, foodLevel, health, level;
    private int coins;
    private List<PotionEffect> effects;
    private AtomicInteger time;
    private boolean tpm;

    private Rank rank;

    private ItemStack[] items, armor, enderChestItems;

    private ItemStack[] backPackItems;

    public PlayerData(UUID uuid){
        this.uuid = uuid;

        this.lastConverser = null;
        this.ignoredPlayers = new ArrayList<>();

        this.coins = 0;
        this.xp = 0.0f;
        this.time = new AtomicInteger(0);

        this.tpm = false;

        this.rank = TimedRankup.getPlugin(TimedRankup.class).getRankManager().get("Default");

        this.items = null;
        this.armor = null;
    }

    public void addCoins(int amount){
        this.setCoins(this.coins + amount);
    }

    public void removeCoins(int amount){
        this.setCoins(this.coins - amount);
    }

    public void setCoins(int amount){
        this.coins = amount;
    }

    public Player getAsPlayer(){
        Player player = Bukkit.getPlayer(this.uuid);

        if (player == null || !player.hasPlayedBefore()){
            return null;
        }

        return player;
    }

}
