package online.nasgar.vanilla.managers.playerdata.parser;

import net.cosmogrp.storage.codec.ModelReader;
import net.cosmogrp.storage.mongo.codec.MongoModelParser;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import online.nasgar.vanilla.utils.BukkitUtil;
import online.nasgar.timedrankup.TimedRankup;
import org.bson.Document;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PlayerMongoModelParser implements MongoModelParser<PlayerData> {

    @Override
    public PlayerData parse(ModelReader<Document> reader) {
        PlayerData data = new PlayerData(reader.readString("_id"));

        data.setLastConverser(reader.readString("lastConverser"));
        data.setIgnoredPlayers(reader.readList("ignoredPlayers", String.class));
        data.setCoins(reader.readInt("coins"));
        data.setXp(reader.readDouble("xp"));
        data.getTime().set(reader.readInt("time"));
        data.setTpm(reader.readBoolean("tpm"));
        data.setFoodLevel(reader.readInt("food"));
        data.setHealth(reader.readDouble("health"));
        data.setLevel(reader.readInt("level"));

        try {
            data.setBackPackItems(BukkitUtil.itemStackArrayFromBase64(reader.readString("backPackItems")));
        } catch (Exception ignored) {
        }
        try {
            data.setEnderChestItems(BukkitUtil.itemStackArrayFromBase64(reader.readString("enderChestItems")));
        } catch (Exception ignored) {
        }

        String rank = reader.readString("rank");

        if (rank != null) {
            data.setRank(TimedRankup.getPlugin(TimedRankup.class).getRankManager().get(rank));
        }

        try {
            data.setItems(BukkitUtil.itemStackArrayFromBase64(reader.readString("items")));
            data.setArmor(BukkitUtil.itemStackArrayFromBase64(reader.readString("armor")));
        } catch (Exception ignored) {
        }

        try {
            data.setEffects(reader.readList("effects", String.class) == null ? new ArrayList<>() : reader.readList("effects", String.class).stream().map(line -> new PotionEffect(PotionEffectType.getByName(line.split(";")[0]), Integer.parseInt(line.split(";")[2]), Integer.parseInt(line.split(";")[1]))).collect(Collectors.toList()));
        } catch (Exception ignored) {
        }

        return data;
    }
}
