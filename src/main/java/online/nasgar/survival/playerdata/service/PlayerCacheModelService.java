package online.nasgar.survival.playerdata.service;

import net.cosmogrp.storage.ModelService;
import online.nasgar.survival.playerdata.PlayerData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class PlayerCacheModelService implements ModelService<PlayerData> {

    private final Map<String, PlayerData> playerDataMap = new HashMap<>();

    @Override
    public @Nullable PlayerData findSync(String id) {
        return playerDataMap.get(id);
    }

    @Override
    public @Nullable PlayerData getSync(String id) {
        return findSync(id);
    }

    @Override
    public @Nullable PlayerData getOrFindSync(String id) {
        return findSync(id);
    }

    @Override
    public List<PlayerData> getAllSync() {
        return new ArrayList<>(playerDataMap.values());
    }

    @Override
    public List<PlayerData> findAllSync() {
        return getAllSync();
    }

    @Override
    public void saveSync(PlayerData model) {
        playerDataMap.put(model.getId(), model);
    }

    @Override
    public void uploadSync(PlayerData model) {
        saveSync(model);
    }

    @Override
    public void uploadAllSync(Consumer<PlayerData> preUploadAction) {}

    @Override
    public void deleteSync(PlayerData model) {
        playerDataMap.remove(model.getId());
    }

    @Override
    public PlayerData deleteSync(String id) {
        return playerDataMap.remove(id);
    }
}
