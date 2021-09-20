package online.nasgar.survival.utils.text;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import online.nasgar.survival.utils.LuckPermsUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter @Setter @UtilityClass
public class BuildText {

    private Map<String, String> map = new HashMap<>();
    private String staticText;
    private Matcher matcher;

    public List<String> of(Player player, List<String> list) {
        return list.stream().map(string -> of(player, string)).collect(Collectors.toList());
    }

    public String of(Player player, String text){
        staticText = text;

        put("player", player.getName());

        put("prefix", LuckPermsUtil.getPrefix(player));
        put("suffix", LuckPermsUtil.getSuffix(player));
        put("rank", LuckPermsUtil.getRankName(player));

        put("center:", null, TextUtil.centerText(matcher.group(2).trim()));

        for (Map.Entry<String, String> entry : map.entrySet()){
            if (text.contains(entry.getKey())){
                text = text.replace(entry.getKey(), entry.getValue());
            }
        }

        return text;
    }

    public void put(String key, String value){
        put(key, value, null);
    }

    public void put(String key, String value, String trim){
        if (key.contains(":")){

            matcher = Pattern.compile("(\\<" + Pattern.quote(key.split(":")[0]) + ":)(.+?)(\\>)").matcher(staticText);

            if (matcher.find()){
                map.put("<" + key + matcher.group(2).trim() + ">", trim);
            }

            return;
        }

        map.put("<" + key.trim() + ">", value);
    }

}
