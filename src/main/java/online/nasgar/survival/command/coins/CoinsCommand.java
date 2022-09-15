package online.nasgar.survival.command.coins;

import com.pixeldv.storage.dist.CachedRemoteModelService;
import me.yushust.message.MessageHandler;
import online.nasgar.survival.command.coins.arguments.AddArgument;
import online.nasgar.survival.command.coins.arguments.RemoveArgument;
import online.nasgar.survival.command.coins.arguments.SetArgument;
import online.nasgar.survival.managers.command.Command;
import online.nasgar.survival.managers.playerdata.PlayerData;
import online.nasgar.survival.utils.text.BuildText;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CoinsCommand extends Command {

    private final CachedRemoteModelService<PlayerData> modelService;
    private final MessageHandler messageHandler;

    public CoinsCommand(
            CachedRemoteModelService<PlayerData> modelService,
            MessageHandler messageHandler
    ) {
        super("coins", messageHandler);
        this.modelService = modelService;
        this.messageHandler = messageHandler;

        this.setAliases(Arrays.asList("economy", "balance"));
        this.setArgumentBase(true);

        this.addArguments(new AddArgument(modelService, messageHandler), new RemoveArgument(modelService, messageHandler), new SetArgument(modelService, messageHandler));
    }

    @Override
    public void onCommand(CommandSender sender, String[] array) {
        if (sender instanceof ConsoleCommandSender) {
            messageHandler.send(sender, "coins.limited-console");
            return;
        }

        Player player = (Player) sender;

        String text = new BuildText(modelService).of(player, messageHandler.get(player, "coins.have.formated"));


        ChatUtil.toPlayer(player, text);
        return;
    }
}
