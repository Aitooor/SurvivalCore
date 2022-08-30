package online.nasgar.vanilla.command.admin;

import me.yushust.message.MessageHandler;
import online.nasgar.vanilla.Vanilla;
import online.nasgar.vanilla.managers.command.Command;
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
        Vanilla.getInstance().getConfigFile().reload();
        Vanilla.getInstance().getMessageHandler().getSource().load("en");
        Vanilla.getInstance().getMessageHandler().getSource().load("es");

        messageHandler.send(sender, "reload");
    }
}
