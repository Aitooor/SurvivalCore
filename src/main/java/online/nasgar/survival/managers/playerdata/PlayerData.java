package online.nasgar.survival.managers.playerdata;

import net.cosmogrp.storage.model.Model;
import net.cosmogrp.storage.mongo.codec.DocumentCodec;
import net.cosmogrp.storage.mongo.codec.DocumentWriter;
import online.nasgar.survival.Survival;
import online.nasgar.survival.utils.server.BukkitUtil;
import online.nasgar.timedrankup.TimedRankup;
import online.nasgar.timedrankup.rank.Rank;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    public PlayerData(String id) {
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

    public void addCoins(int amount) {
        this.setCoins(this.coins + amount);
        Survival.getEconomy().depositPlayer(this.getAsPlayer(), amount);
    }

    public void removeCoins(int amount) {
        this.setCoins(this.coins - amount);
        Survival.getEconomy().withdrawPlayer(this.getAsPlayer(), amount);
    }

    public void setCoins(int amount) {
        this.coins = amount;
        Survival.getEconomy().withdrawPlayer(this.getAsPlayer(), Survival.getEconomy().getBalance(this.getAsPlayer()));
        Survival.getEconomy().depositPlayer(this.getAsPlayer(), amount);
    }

    public Player getAsPlayer() {
        Player player = Bukkit.getPlayer(UUID.fromString(id));

        if (player == null || !player.hasPlayedBefore()) {
            return null;
        }

        return player;
    }

    @Override
    public Document serialize() {
        return DocumentWriter.create(this)
                .writeObject("ignoredPlayers", getIgnoredPlayers())
                .write("lastConverser", getLastConverser())
                .write("coins", getCoins())
                .write("xp", getXp())
                .write("time", getTime().get())
                .write("tpm", isTpm())
                .write("food", getFoodLevel())
                .write("health", getHealth())
                .write("level", getLevel())
                .writeObject("effects", getEffects() == null ?
                        new ArrayList<>() :
                        getEffects().stream()
                                .map(potionEffect -> potionEffect
                                        .getType().getName() + ";" +
                                        potionEffect.getAmplifier() + ";" +
                                        potionEffect.getDuration())
                                .collect(Collectors.toList()))
                .write("rank", getRank().getName())
                .write("items", BukkitUtil.itemStackArrayToBase64(getItems()))
                .write("armor", BukkitUtil.itemStackArrayToBase64(getArmor()))
                .write("enderChestItems", BukkitUtil.itemStackArrayToBase64(getEnderChestItems()))
                .end();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastConverser() {
        return lastConverser;
    }

    public void setLastConverser(String lastConverser) {
        this.lastConverser = lastConverser;
    }

    public List<String> getIgnoredPlayers() {
        return ignoredPlayers;
    }

    public void setIgnoredPlayers(List<String> ignoredPlayers) {
        this.ignoredPlayers = ignoredPlayers;
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCoins() {
        return coins;
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<PotionEffect> effects) {
        this.effects = effects;
    }

    public AtomicInteger getTime() {
        return time;
    }

    public void setTime(AtomicInteger time) {
        this.time = time;
    }

    public boolean isTpm() {
        return tpm;
    }

    public void setTpm(boolean tpm) {
        this.tpm = tpm;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public ItemStack[] getEnderChestItems() {
        return enderChestItems;
    }

    public void setEnderChestItems(ItemStack[] enderChestItems) {
        this.enderChestItems = enderChestItems;
    }
}
