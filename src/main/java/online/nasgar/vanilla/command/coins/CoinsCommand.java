package online.nasgar.vanilla.command.coins;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.vanilla.command.coins.arguments.AddArgument;
import online.nasgar.vanilla.command.coins.arguments.RemoveArgument;
import online.nasgar.vanilla.command.coins.arguments.SetArgument;
import online.nasgar.vanilla.managers.command.Command;
import online.nasgar.vanilla.managers.playerdata.PlayerData;
import online.nasgar.vanilla.utils.text.BuildText;
import online.nasgar.vanilla.utils.text.ChatUtil;
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
