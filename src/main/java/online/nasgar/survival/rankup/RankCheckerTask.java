package online.nasgar.survival.rankup;

import lombok.AllArgsConstructor;
import online.nasgar.survival.Survival;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.playerdata.PlayerDataManager;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class RankCheckerTask extends BukkitRunnable {

    private final Survival plugin;

    @Override public void run() {
        PlayerDataManager dataManager = this.plugin.getPlayerDataManager();

        for (PlayerData data : dataManager.getDataMap().values()){
            if (data != null) {
                Player player = data.getAsPlayer();

                data.getTime().incrementAndGet();
                Rank nextRank = this.plugin.getRankManager().getApplicable(data);

                if (!nextRank.equals(data.getRank())) {

                    data.setRank(nextRank);


                    data.getRank().getCommands().forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            (command).replace("<player>", player.getName())));

                    this.plugin.getConfigFile().getStringList("ranked.own").forEach(
                            message -> ChatUtil.toPlayer(player, (message)
                                    .replace("<rankPrefix>", nextRank.getPrefix())
                                    .replace("<rankName>", nextRank.getName()))
                    );
                }
            }
        }
    }
}
