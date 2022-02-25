package online.nasgar.survival.utils.text;

import lombok.Getter;
import lombok.Setter;
import net.cosmogrp.storage.mongo.MongoModelService;
import online.nasgar.survival.Survival;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.LuckPermsUtil;
import online.nasgar.survival.utils.TimeUtils;
import online.nasgar.timedrankup.TimedRankup;
import online.nasgar.timedrankup.rank.Rank;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@Setter
public class BuildText {

    private Survival plugin = Survival.getInstance();

    private final MongoModelService<PlayerData> playerDataMongoModelService;

    private Map<String, String> map = new HashMap<>();
    private String staticText;
    private Matcher matcher;

    public BuildText(MongoModelService<PlayerData> playerDataMongoModelService) {
        this.playerDataMongoModelService = playerDataMongoModelService;
    }


    public List<String> of(Player player, List<String> list) {
        return list.stream().map(string -> of(player, string)).collect(Collectors.toList());
    }

    public String of(Player player, String text) {
        staticText = text;

        PlayerData data = playerDataMongoModelService.getOrFindSync(player.getUniqueId().toString());

        put("player", player.getName());

        put("prefix", LuckPermsUtil.getPrefix(player));
        put("suffix", LuckPermsUtil.getSuffix(player));
        put("rank", LuckPermsUtil.getRankName(player));
        put("coins", Integer.toString(data.getCoins()));

        Rank rank = TimedRankup.getPlugin(TimedRankup.class).getRankManager().getNextApplicable(TimedRankup.getPlugin(TimedRankup.class).getUserManager().get(data.getUuid()));

        String finalText = text;

        TimedRankup.getPlugin(TimedRankup.class).getRankManager().getRanksInverted().forEach(r -> {
            put("rankPrefix", r.getPrefix());

            if (finalText.equalsIgnoreCase("<ranks>")) {
                put("rankPendingTime", TimeUtils.formatTime(Duration.ofSeconds(rank.getTime() - data.getTime().get())));
            }

            put("rankNeededTime", TimeUtils.formatTime(Duration.ofSeconds(rank.getTime())));

        });

        put("newRankPrefix", rank.getPrefix());
        put("parsedTime", TimeUtils.formatTime(Duration.ofSeconds(rank.getTime() - data.getTime().get())));

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (text.contains(entry.getKey())) {
                text = text.replace(entry.getKey(), entry.getValue());
            }
        }

        return text;
    }

    public void put(String key, String value) {
        put(key, value, null);
    }

    public void put(String key, String value, String trim) {
        if (key.contains(":")) {

            matcher = Pattern.compile("(\\<" + Pattern.quote(key.split(":")[0]) + ":)(.+?)(\\>)").matcher(staticText);

            if (matcher.find()) {
                map.put("<" + key + matcher.group(2).trim() + ">", trim);
            }

            return;
        }

        map.put("<" + key.trim() + ">", value);
    }

}
