package online.nasgar.survival.managers.command;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Argument  {

    private final MessageHandler messageHandler;

    private Command command;

    private String name, permission;

    private List<String> aliases;

    private CommandSender sender;
    private Player player;

    private boolean onlyPlayers;

    public Argument(MessageHandler messageHandler, String name) {
        this.messageHandler = messageHandler;
        this.name = name;
        this.permission = null;

        this.aliases = new ArrayList<>();

        this.onlyPlayers = false;
    }

    public boolean execute(CommandSender sender, String[] array){
        this.sender = sender;

        if (sender instanceof ConsoleCommandSender){
            if (this.onlyPlayers){
                ChatUtil.toSender(sender, "");
                return true;
            }
        }
        if (this.permission != null && !this.permission.equals("")){
            if (!sender.hasPermission(this.permission)){
                ChatUtil.toSender(sender, "");
                return true;
            }
        }

        if (!this.onlyPlayers) {
            this.onArgument(sender, array);
        } else {
            this.onArgument((Player) sender, array);
        }
        return false;
    }

    public void onArgument(Player player, String[] array){
        this.player = player;
    }

    public void onArgument(CommandSender sender, String[] array){
    }

    public boolean isPlayerNull(Player target, String name){
        if (target == null){
            messageHandler.send(player, "player-not-found", "%target_name%", name);
            return true;
        }

        return false;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isOnlyPlayers() {
        return onlyPlayers;
    }

    public void setOnlyPlayers(boolean onlyPlayers) {
        this.onlyPlayers = onlyPlayers;
    }
}
