package online.nasgar.survival.utils.hooks;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.entity.Player;

import java.util.Optional;

public class LuckPermsUtil {

    private static CachedMetaData getMetaData(Player player) {
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            throw new IllegalArgumentException("LuckPerms user for " + player.getName() + " could not be found");
        }
        Optional<QueryOptions> queryOptions = LuckPermsProvider.get().getContextManager().getQueryOptions(user);
        if (!queryOptions.isPresent()) {
            throw new IllegalArgumentException("LuckPerms context of " + player.getName() + " could not be loaded");
        }
        return user.getCachedData().getMetaData(queryOptions.get());
    }

    public static String getRankName(Player player) {
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            throw new IllegalArgumentException("LuckPerms user for " + player.getName() + " could not be found");
        }
        return user.getPrimaryGroup();
    }

    public static String getPrefix(Player player) {
        return getMetaData(player).getPrefix() == null ? "" : getMetaData(player).getPrefix();
    }

    public static String getSuffix(Player player) {
        return getMetaData(player).getSuffix() == null ? "" : getMetaData(player).getSuffix();
    }

}
