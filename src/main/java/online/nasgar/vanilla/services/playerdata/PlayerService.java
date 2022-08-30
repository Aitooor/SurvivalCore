package online.nasgar.vanilla.services.playerdata;

import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.function.Consumer;

public class PlayerService {

    private final CachedRemoteModelService<PlayerData> modelService;

    public PlayerService(CachedRemoteModelService<PlayerData> modelService) {
        this.modelService = modelService;
    }

    public void load(Player player) {
        String playerId = player.getUniqueId().toString();
        modelService.getOrFind(playerId)
                .thenApply(data -> {
                    if (data == null) {
                        data = new PlayerData(playerId);
                        modelService.saveSync(data);
                    }

                    return data;
                })
                .thenAccept(data -> {
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
        saveAndRemove(id, data -> {
        });
    }

    public void saveAndRemove(String id, Consumer<PlayerData> beforeSave) {
        PlayerData playerData = modelService.getSync(id);

        if (playerData == null) {
            throw new NullPointerException("Error from find data of player id=" + id);
        }

        beforeSave.accept(playerData);

        modelService.upload(playerData).whenComplete((ignored, error) -> {
            if (error != null) {
                error.printStackTrace(System.err);
            }
        });
    }
}
