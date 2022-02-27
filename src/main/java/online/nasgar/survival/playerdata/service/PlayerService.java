package online.nasgar.survival.playerdata.service;

import net.cosmogrp.storage.mongo.MongoModelService;
import online.nasgar.survival.playerdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class PlayerService {

    private final MongoModelService<PlayerData> playerDataMongoModelService;

    public PlayerService(MongoModelService<PlayerData> playerDataMongoModelService) {
        this.playerDataMongoModelService = playerDataMongoModelService;
    }

    public void load(Player player) {
        playerDataMongoModelService.getOrFind(player.getUniqueId().toString()).thenAccept(data -> {
            PlayerInventory inventory = player.getInventory();

            inventory.setArmorContents(data.getArmor());
            inventory.setContents(data.getItems());

            if (data.getEffects() != null) {
                data.getEffects().forEach(player::addPotionEffect);
            }

            player.setHealth(data.getHealth());
            player.setFoodLevel(data.getFoodLevel());
            player.setLevel(data.getLevel());

            player.getEnderChest().setContents(data.getEnderChestItems());

            System.out.println("ola si pase por el find");
        });

    }

    public void saveAndRemove(String id) {
       saveAndRemove(id, data -> {});
    }

    public void saveAndRemove(String id, Consumer<PlayerData> beforeSave) {
        PlayerData playerData = playerDataMongoModelService.getSync("id");

        if (playerData == null) {
            throw new NullPointerException("Error from find data of player id=" + id);
        }

        beforeSave.accept(playerData);

        playerDataMongoModelService.save(playerData);
        playerDataMongoModelService.deleteInCache(playerData);

        System.out.println("si pase por el get");

    }
}
