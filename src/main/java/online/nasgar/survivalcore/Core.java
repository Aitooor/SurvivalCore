package online.nasgar.survivalcore;

import lombok.Getter;
import online.nasgar.survivalcore.utils.command.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Core extends JavaPlugin {

    @Getter private static Core instance;
    private CommandFramework commandFramework;

    @Override
    public void onEnable() {
        instance = this;
    }

    // Heal, Godmode, Feed, Gamemode, Fly, Vanish, Freeze

    // Especialitos: Tpa, Tp, Home, Warp, Spawn, SetSpawn, Back

}
