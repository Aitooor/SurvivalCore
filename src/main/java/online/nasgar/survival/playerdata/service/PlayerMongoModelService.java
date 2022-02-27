package online.nasgar.survival.playerdata.service;

import com.mongodb.client.MongoDatabase;
import net.cosmogrp.storage.ModelService;
import net.cosmogrp.storage.model.meta.ModelMeta;
import net.cosmogrp.storage.mongo.MongoModelService;
import online.nasgar.survival.playerdata.PlayerData;
import online.nasgar.survival.playerdata.parser.PlayerMongoModelParser;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.Executor;

public class PlayerMongoModelService extends MongoModelService<PlayerData> {

    public PlayerMongoModelService(Executor executor, MongoDatabase database, ModelService<PlayerData> cacheModelService) {
        super(executor, new ModelMeta<>(PlayerData.class).addProperty("collection", "users"), cacheModelService, database, new PlayerMongoModelParser());
    }

    @Override
    public @Nullable PlayerData getOrFindSync(String id) {
        PlayerData playerData = super.getOrFindSync(id);

        if (playerData == null) {
            playerData = new PlayerData(
                    UUID.fromString(id)
            );

            cacheModelService.saveSync(playerData);
        }

        return playerData;
    }
}
