package online.nasgar.survival.auctions;

import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Data;
import lombok.Getter;
import online.nasgar.survival.Survival;
import online.nasgar.survival.utils.ItemUtils;
import org.bson.Document;

import java.util.List;
import java.util.UUID;

@Data
public class AuctionsManager {

    @Getter private static AuctionsManager instance;

    private List<AuctionData> auctions;

    public AuctionsManager() {
        instance = this;

        this.auctions = Lists.newArrayList();

        this.load();
    }

    public void load() {
        for (Document document : Survival.getInstance().getMongoManager().getCollection("Auctions").find()) {
            AuctionData auctionData = new AuctionData();

            auctionData.setId(UUID.fromString(document.getString("id")));
            auctionData.setOwner(UUID.fromString(document.getString("owner")));
            auctionData.setStack(ItemUtils.deSerialized(document.getString("itemStack")));
            auctionData.setPrice(document.getInteger("price"));
            auctionData.setDuration(document.getLong("duration"));
            auctionData.setAddedAt(document.getLong("added"));

            this.auctions.add(auctionData);
        }
    }
    public void save() {
        Survival.getInstance().getMongoManager().getCollection("Auctions").drop();

        this.auctions.forEach(auctionData -> {
            Document document = new Document();

            document.put("id", auctionData.getId().toString());
            document.put("owner", auctionData.getOwner().toString());
            document.put("itemStack", ItemUtils.serialize(auctionData.getStack()));
            document.put("price", auctionData.getPrice());
            document.put("duration", auctionData.getDuration());
            document.put("added", auctionData.getAddedAt());

            Survival.getInstance().getMongoManager().getCollection("Auctions").replaceOne(Filters.eq(UUID.randomUUID()), document, new ReplaceOptions().upsert(true));
        });
    }
}
