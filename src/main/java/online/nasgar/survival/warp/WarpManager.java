package online.nasgar.survival.warp;

import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Data;
import lombok.Getter;
import online.nasgar.survival.Survival;
import online.nasgar.survival.utils.LocationUtil;
import org.bson.Document;

import java.util.List;

@Data
public class WarpManager {

    @Getter private static WarpManager instance;

    private List<WarpData> warps;

    public WarpManager() {
        instance = this;

        this.warps = Lists.newArrayList();

        this.load();
    }

    public void load() {
        for (Document document : Survival.getInstance().getMongoManager().getCollection("warps").find()) {
            WarpData warp = new WarpData();

            warp.setName(document.getString("name"));
            warp.setLocation(LocationUtil.convertLocation(document.getString("location")));
            warp.setPermission(document.get("permission", ""));

            this.warps.add(warp);
        }
    }

    public void save() {
        Survival.getInstance().getMongoManager().getCollection("warps").drop();

        for (WarpData warp : this.warps) {
            Document document = new Document();

            document.put("name", warp.getName());
            document.put("location", LocationUtil.parseLocation(warp.getLocation()));
            document.put("permission", warp.getPermission());

            Survival.getInstance().getMongoManager().getCollection("warps").replaceOne(Filters.eq(warp.getName()), document, new ReplaceOptions().upsert(true));
        }
    }
    public WarpData getWarpDataByName(String name) {
        return this.warps.stream().filter(warpData -> warpData.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
