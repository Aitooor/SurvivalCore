package online.nasgar.survival.scoreboard;

import online.nasgar.survival.Survival;
import online.nasgar.survival.scoreboard.adapter.NautilusAdapter;
import online.nasgar.survival.utils.TaskUtil;
import online.nasgar.survival.utils.text.BuildText;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NautilusScoreboardAdapter implements NautilusAdapter {

    private String title;

    @Override public String getTitle(Player player) {
        List<String> titleList = new ArrayList<>();

        titleList.add("&b&lS");
        titleList.add("&b&lSu");
        titleList.add("&b&lSur");
        titleList.add("&b&lSurv");
        titleList.add("&b&lSurvi");
        titleList.add("&b&lSurviv");
        titleList.add("&b&lSurviva");
        titleList.add("&b&lSurvival");

        TaskUtil.runTaskTimer(() -> {

            int i = 0;

            if (++i >= titleList.size()){
                i = -1;
            }

            this.title = titleList.get(i);

        }, 0L, 5L);

        return ChatUtil.translate(this.title);
    }

    @Override public List<String> getContent(Player player) {
        List<String> list = new ArrayList<>();

        String nCoin = Survival.getInstance().getPlayerDataManager().get(player.getUniqueId()).getCoins() == 1 ? "&eNCoin" : "&eNCoins";

        list.add(ChatUtil.translate("&bRank"));
        list.add(ChatUtil.translate(BuildText.of(player, " <suffix>")));
        list.add("");
        list.add(ChatUtil.translate("&bBalance"));
        list.add(ChatUtil.translate(BuildText.of(player, "&f<coins> " + nCoin)));
        list.add("");
        list.add(ChatUtil.translate("&bOnline&7: &f" + Bukkit.getOnlinePlayers().size()));
        list.add("");
        list.add(ChatUtil.translate("<center:&7nasgar.online>"));

        return list;
    }
}
