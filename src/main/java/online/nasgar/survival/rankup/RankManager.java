package online.nasgar.survival.rankup;

import lombok.Getter;
import online.nasgar.survival.Survival;
import online.nasgar.survival.playerdata.PlayerData;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

@Getter
public class RankManager {

    private final List<Rank> ranks, ranksInverted;
    private final Map<String, Rank> ranksMap;

    public RankManager() {
        this.ranks = new ArrayList<>();
        this.ranksInverted = new ArrayList<>();
        this.ranksMap = new HashMap<>();

        this.setup();
    }

    private void setup(){
        ConfigurationSection section = Survival.getInstance().getConfigFile().getConfigurationSection("rank-ups");

        for (String key : section.getKeys(false)){
            Rank rank = new Rank(key,
                    section.getString(key + ".prefix"),
                    section.getInt(key + ".time") * 86400,
                    section.getStringList(key + ".commands"));

            this.ranks.add(rank);
            this.ranksMap.put(key, rank);
            this.ranksInverted.addAll(this.ranks);
            Collections.reverse(this.ranksInverted);
        }

    }

    public Rank get(String name) {
        return this.ranksMap.get(name);
    }

    public Rank getApplicable(PlayerData data) {
        return this.ranks.stream()
                        .filter(rank -> data.getTime().get() > rank.getTime()) // Checking if the player has the time.
                        .findFirst() // Getting the first element, may be the rank that the player will have.
                        .orElse(null); // Else, returning null, this means that player just doesn't have any rankUp pending.
    }

    public Rank getNextApplicable(PlayerData data) {
        return this.ranksInverted.stream()
                        .filter(rank -> !data.getRank().equals(rank) && rank.getTime()> data.getTime().get()) // Checking if the player has the time and checking if the player doesn't have that rank.
                        .findFirst() // Getting the first element, may be the rank that the player will have.
                        .orElse(null); // Else, returning null, this means that player just doesn't have any rankUp pending.
    }

}
