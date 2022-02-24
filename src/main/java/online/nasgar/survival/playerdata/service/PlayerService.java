package online.nasgar.survival.playerdata.service;

import net.cosmogrp.storage.ModelService;
import net.cosmogrp.storage.mongo.MongoModelService;
import online.nasgar.survival.playerdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.function.Consumer;

public class PlayerService {

    private final ModelService<PlayerData> playerCacheModelService;
    private final MongoModelService<PlayerData> playerDataMongoModelService;

    public PlayerService(ModelService<PlayerData> playerCacheModelService, MongoModelService<PlayerData> playerDataMongoModelService) {
        this.playerCacheModelService = playerCacheModelService;
        this.playerDataMongoModelService = playerDataMongoModelService;
    }

    public void load(Player player) {
        PlayerData data = playerCacheModelService.findSync(player.getUniqueId().toString());

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

    }

    public void saveAndRemove(String id) {
       saveAndRemove(id, data -> {});
    }

    public void saveAndRemove(String id, Consumer<PlayerData> beforeSave) {
        PlayerData playerData = playerCacheModelService.findSync(id);

        if (playerData == null) {
            throw new NullPointerException("Error from find data of player id=" + id);
        }

        beforeSave.accept(playerData);

        playerDataMongoModelService.save(playerData);
        playerCacheModelService.deleteSync(id);
    }
}