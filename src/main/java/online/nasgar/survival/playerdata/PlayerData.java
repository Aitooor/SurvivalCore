package online.nasgar.survival.playerdata;

import lombok.Getter;
import lombok.Setter;
import net.cosmogrp.storage.model.Model;
import net.cosmogrp.storage.mongo.DocumentBuilder;
import net.cosmogrp.storage.mongo.DocumentCodec;
import online.nasgar.survival.utils.BukkitUtil;
import online.nasgar.timedrankup.TimedRankup;
import online.nasgar.timedrankup.rank.Rank;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter @Setter
public class PlayerData implements DocumentCodec, Model {

    private String id;
    private String lastConverser;
    private List<String> ignoredPlayers;

    private double xp;
    private double health;
    private int foodLevel;
    private int level;
    private int coins;
    private List<PotionEffect> effects;
    private AtomicInteger time;
    private boolean tpm;

    private Rank rank;

    private ItemStack[] items;
    private ItemStack[] armor;
    private ItemStack[] enderChestItems;

    private ItemStack[] backPackItems;

    public PlayerData(String id){
        this.id = id;

        this.lastConverser = null;
        this.ignoredPlayers = new ArrayList<>();

        this.coins = 0;
        this.xp = 0.0f;
        this.time = new AtomicInteger(0);

        this.tpm = false;

        this.rank = TimedRankup.getPlugin(TimedRankup.class).getRankManager().get("default");

        this.items = null;
        this.armor = null;
    }

    public void addCoins(int amount){
        this.setCoins(this.coins + amount);
    }

    public void removeCoins(int amount){
        this.setCoins(this.coins - amount);
    }

    public void setCoins(int amount){
        this.coins = amount;
    }

    public Player getAsPlayer(){
        Player player = Bukkit.getPlayer(UUID.fromString(id));

        if (player == null || !player.hasPlayedBefore()){
            return null;
        }

        return player;
    }

    @Override
    public Document toDocument() {
        DocumentBuilder documentBuilder = DocumentBuilder.create(this);

        documentBuilder.write("lastConverser", getLastConverser());
        documentBuilder.write("ignoredPlayers", getIgnoredPlayers());
        documentBuilder.write("coins", getCoins());
        documentBuilder.write("xp", getXp());
        documentBuilder.write("time", getTime().get());
        documentBuilder.write("tpm", isTpm());
        documentBuilder.write("food", getFoodLevel());
        documentBuilder.write("health", getHealth());
        documentBuilder.write("level", getLevel());
        documentBuilder.write("effects", getEffects() == null ? new ArrayList<>() : getEffects().stream().map(potionEffect -> potionEffect.getType().getName() + ";" + potionEffect.getAmplifier() + ";" + potionEffect.getDuration()).collect(Collectors.toList()));
        documentBuilder.write("rank", getRank().getName());

        documentBuilder.write("items", BukkitUtil.itemStackArrayToBase64(getItems()));
        documentBuilder.write("armor", BukkitUtil.itemStackArrayToBase64(getArmor()));
        documentBuilder.write("enderChestItems", BukkitUtil.itemStackArrayToBase64(getEnderChestItems()));
        documentBuilder.write("backPackItems", BukkitUtil.itemStackArrayToBase64(getBackPackItems()));

        return documentBuilder.build();
    }

    @Override
    public String getId() {
        return id;
    }
}
