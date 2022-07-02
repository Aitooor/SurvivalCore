package online.nasgar.survival.command.admin;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.Survival;
import online.nasgar.survival.managers.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends Command {

    private final MessageHandler messageHandler;

    public ReloadCommand(MessageHandler messageHandler) {
        super("survivalreload", messageHandler);
        this.messageHandler = messageHandler;

        this.setPermission("survivalcore.reload");
    }

    @Override
    public void onCommand(CommandSender sender, String[] array) {
        Survival.getInstance().getConfigFile().reload();

        messageHandler.send(sender, "reload");
    }
}