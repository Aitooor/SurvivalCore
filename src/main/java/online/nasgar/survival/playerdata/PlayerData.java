package online.nasgar.survival.playerdata;

import lombok.Getter;
import lombok.Setter;
import online.nasgar.survival.rankup.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Getter @Setter
public class PlayerData {

    private UUID uuid;
    private String lastConverser;
    private List<String> ignoredPlayers;

    private int coins;
    private AtomicInteger time;
    private boolean tpm;

    private Rank rank;

    public PlayerData(UUID uuid){
        this.uuid = uuid;

        this.lastConverser = null;
        this.ignoredPlayers = new ArrayList<>();

        this.coins = 0;
        this.time = new AtomicInteger(0);

        this.tpm = false;
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
