package online.nasgar.survival.playerdata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor
public class PlayerData {

    private UUID uuid;
    private String lastConverser;
    private List<String> ignoredPlayers;

    private int coins;
    private boolean tpm;

    public PlayerData(UUID uuid){
        this.uuid = uuid;

        this.lastConverser = null;
        this.ignoredPlayers = new ArrayList<>();

        this.coins = 0;
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

}
