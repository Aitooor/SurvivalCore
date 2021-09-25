package online.nasgar.survival.tab.versions.impl;

import online.nasgar.survival.tab.versions.module.IPlayerVersion;
import online.nasgar.survival.tab.versions.module.PlayerVersion;
import org.bukkit.entity.Player;
import us.myles.ViaVersion.api.Via;

/**
 * Created By LeandroSSJ
 * Created on 22/09/2021
 */
public class PlayerVersionViaVersionImpl implements IPlayerVersion
{
    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(Via.getAPI().getPlayerVersion(player));
    }
}
