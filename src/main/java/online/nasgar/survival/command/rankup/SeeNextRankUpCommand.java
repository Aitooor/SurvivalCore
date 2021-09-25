package online.nasgar.survival.command.rankup;

import online.nasgar.survival.Survival;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.rankup.Rank;
import online.nasgar.survival.utils.TimeUtils;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.entity.Player;

import java.time.Duration;

public class SeeNextRankUpCommand extends Command {

    private final Survival plugin;

    public SeeNextRankUpCommand(Survival plugin) {
        super("seenextrankup");
        this.plugin = plugin;
    }

    @Override public void onCommand(Player player, String[] array) {
        PlayerData data = this.plugin.getPlayerDataManager().get(player.getUniqueId());

        Rank rank = this.plugin.getRankManager().getNextApplicable(data);

        if (rank != null) {
            this.plugin.getConfigFile().getStringList("commands.see.next").forEach(message -> {
                ChatUtil.toPlayer(player, (message)
                        .replace("<newRankPrefix>", rank.getPrefix())
                        .replace("<parsedTime>", TimeUtils.formatTime(Duration.ofSeconds(rank.getTime() - data.getTime().get()))));
            });
        } else {
            this.plugin.getConfigFile().getStringList("commands.see.no-more").forEach(message -> ChatUtil.toPlayer(player, message));

        }
    }
}
