package online.nasgar.survival.playerdata.parser;

import net.cosmogrp.storage.mongo.DocumentReader;
import net.cosmogrp.storage.mongo.MongoModelParser;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.utils.BukkitUtil;
import online.nasgar.timedrankup.TimedRankup;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerMongoModelParser implements MongoModelParser<PlayerData> {

    @Override
    public PlayerData parse(DocumentReader reader) {
        PlayerData data = new PlayerData(UUID.fromString(reader.readString("_id")));

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
        } catch (Exception ignored) {}
        try {
            data.setEnderChestItems(BukkitUtil.itemStackArrayFromBase64(reader.readString("enderChestItems")));
        } catch (Exception ignored) {}

        String rank = reader.readString("rank");

        if (rank != null) {
            data.setRank(TimedRankup.getPlugin(TimedRankup.class).getRankManager().get(rank));
        }

        try {
            data.setItems(BukkitUtil.itemStackArrayFromBase64(reader.readString("items")));
            data.setArmor(BukkitUtil.itemStackArrayFromBase64(reader.readString("armor")));
        } catch (Exception ignored) {}

        try {
            data.setEffects(reader.readList("effects", String.class) == null ? new ArrayList<>() : reader.readList("effects", String.class).stream().map(line -> new PotionEffect(PotionEffectType.getByName(line.split(";")[0]), Integer.parseInt(line.split(";")[2]), Integer.parseInt(line.split(";")[1]))).collect(Collectors.toList()));
        } catch (Exception ignored) {}

        return data;
    }
}
