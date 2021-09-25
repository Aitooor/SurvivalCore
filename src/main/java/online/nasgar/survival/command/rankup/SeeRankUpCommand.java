package online.nasgar.survival.command.rankup;

import online.nasgar.survival.Survival;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.text.BuildText;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.entity.Player;

public class SeeRankUpCommand extends Command {

    private final Survival plugin;

    public SeeRankUpCommand(Survival plugin) {
        super("seerankup");
        this.plugin = plugin;
    }

    @Override public void onCommand(Player player, String[] array) {
        PlayerData data = this.plugin.getPlayerDataManager().get(player.getUniqueId());

        this.plugin.getConfigFile().getStringList("commands.listed.send").forEach(s -> {

            if (s.equalsIgnoreCase("<ranks>")) {
                this.plugin.getRankManager().getRanksInverted().forEach(rank -> {

                    if (data.getTime().get() > rank.getTime()) {
                        ChatUtil.toPlayer(player, BuildText.of(player, this.plugin.getConfigFile().getString("commands.listed.format", "")));
                    } else {
                        ChatUtil.toPlayer(player, BuildText.of(player, s));
                    }
                });
            } else {
                ChatUtil.toPlayer(player, BuildText.of(player, s));
            }

        });
    }
}
