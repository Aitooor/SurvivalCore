package online.nasgar.survival.tab.versions.impl;

import online.nasgar.survival.tab.versions.module.IPlayerVersion;
import online.nasgar.survival.tab.versions.module.PlayerVersion;
import org.bukkit.entity.Player;
import protocolsupport.api.ProtocolSupportAPI;

/**
 * Created By LeandroSSJ
 * Created on 22/09/2021
 */
public class PlayerVersionProtocolSupportImpl implements IPlayerVersion
{
    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(ProtocolSupportAPI.getProtocolVersion(player).getId());
    }
}
