package online.nasgar.survivalcore;

import lombok.Getter;
import online.nasgar.survivalcore.listeners.GodModeListener;
import online.nasgar.survivalcore.utils.ClassRegistrationController;
import online.nasgar.survivalcore.utils.command.CommandFramework;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Core extends JavaPlugin {

    @Getter private static Core instance;
    private final CommandFramework commandFramework = new CommandFramework(this);
    private final ClassRegistrationController crc = new ClassRegistrationController();

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        registerListeners();
        crc.loadCommands("online.nasgar.survivalcore.commands");
    }

    private void registerListeners() {
        List<Listener> listeners = new ArrayList<>();

        listeners.add(new GodModeListener());

        listeners.forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }
}
