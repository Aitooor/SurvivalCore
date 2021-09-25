package online.nasgar.survival.scoreboard;

import online.nasgar.survival.Survival;
import online.nasgar.survival.scoreboard.adapter.NautilusAdapter;
import online.nasgar.survival.utils.text.BuildText;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NautilusScoreboardAdapter implements NautilusAdapter {

    @Override public String getTitle(Player player) {
        return ChatUtil.translate("&b&lSURVIVAL");
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
