package online.nasgar.survival.playerdata.service;

import net.cosmogrp.storage.mongo.MongoModelService;
import online.nasgar.survival.playerdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;


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

        });

    }

    public void saveAndRemove(String id) {
       saveAndRemove(id, data -> {});
    }

    public void saveAndRemove(String id, Consumer<PlayerData> beforeSave) {
        PlayerData playerData = playerDataMongoModelService.getSync(id);

        if (playerData == null) {
            throw new NullPointerException("Error from find data of player id=" + id);
        }

        beforeSave.accept(playerData);

        playerDataMongoModelService.upload(playerData).whenComplete((ignored, error) -> {
            if (error != null) {
                error.printStackTrace(System.err);
            }
        });

    }
}
