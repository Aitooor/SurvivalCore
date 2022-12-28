package online.nasgar.survival.command.normal.coins;

import me.yushust.message.MessageHandler;
import net.cosmogrp.storage.dist.CachedRemoteModelService;
import online.nasgar.survival.command.normal.coins.args.AddArgument;
import online.nasgar.survival.command.normal.coins.args.RemoveArgument;
import online.nasgar.survival.command.normal.coins.args.SetArgument;
import online.nasgar.survival.managers.command.Command;
import online.nasgar.survival.managers.playerdata.PlayerData;
import online.nasgar.survival.utils.text.BuildText;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.Bukkit;
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

        if (array.length < 1) {
            String text = new BuildText(modelService).of(player, messageHandler.get(player, "coins.have.formated"));

            ChatUtil.toPlayer(player, text);
            return;
        }

        Player target = Bukkit.getPlayer(array[0]);

        if (this.isPlayerNull(target, array[0])) return;

        String text = new BuildText(modelService).of(target, messageHandler.get(target, "coins.have.formated"));

        ChatUtil.toPlayer(player, text);
    }
}
