package online.nasgar.survival.tab.versions.impl;

import com.comphenix.protocol.ProtocolLibrary;
import online.nasgar.survival.tab.versions.module.IPlayerVersion;
import online.nasgar.survival.tab.versions.module.PlayerVersion;
import org.bukkit.entity.Player;

/**
 * Created By LeandroSSJ
 * Created on 22/09/2021
 */

public class PlayerVersionProtocolLibImpl implements IPlayerVersion
{
    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(ProtocolLibrary.getProtocolManager().getProtocolVersion(player));
    }
}
