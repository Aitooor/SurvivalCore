package online.nasgar.survival.command;

import online.nasgar.survival.Survival;
import online.nasgar.survival.command.management.Command;
import online.nasgar.survival.utils.CC;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super("survivalreload");

        this.setPermission("survivalreload.command");
    }

    @Override
    public void onCommand(CommandSender sender, String[] array) {
        Survival.getInstance().getConfigFile().reload();
        Survival.getInstance().getWarpsFile().reload();

        sender.sendMessage(CC.translate("&aSurvival Configuration reloaded."));
    }
}
