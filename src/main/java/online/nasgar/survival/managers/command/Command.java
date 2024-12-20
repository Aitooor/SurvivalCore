package online.nasgar.survival.managers.command;

import me.yushust.message.MessageHandler;
import online.nasgar.survival.utils.text.ChatUtil;
import online.nasgar.survival.utils.CooldownUtil;
import online.nasgar.survival.utils.time.TimeFormatter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Command extends BukkitCommand implements TabCompleter {

    private final MessageHandler messageHandler;

    private String permission;

    private long cooldown;
    private boolean onlyPlayers, argumentBase;

    private Player player;

    private Set<Argument> arguments;
    private List<String> argumentBaseUsageMessage;

    public Command(String name, MessageHandler messageHandler) {
        super(name);
        this.messageHandler = messageHandler;

        this.permission = null;
        this.argumentBaseUsageMessage = new ArrayList<>();

        this.cooldown = 0L;

        this.onlyPlayers = false;
        this.argumentBase = false;

        this.arguments = new HashSet<>();
    }

    @Override public boolean execute(CommandSender sender, String label, String[] array) {
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

        if (this.argumentBase){
            if (array.length < 1){
                this.onCommand(sender, array);
            } else {

                Argument argument = this.getArgumentByName(array[0]);

                if (argument == null) {
                    ChatUtil.toSender(sender, "");
                    return true;
                }

                argument.execute(sender, this.fixedArray(array));
                return true;
            }
        }

        if (!this.onlyPlayers) {
            this.onCommand(sender, array);
        } else {
            this.onCommand((Player) sender, array);
        }

        return false;
    }

    public void onCommand(Player player, String[] array){
        this.player = player;
    }

    public void onCommand(CommandSender sender, String[] array){
    }

    public boolean isPlayerNull(Player target, String name){
        if (target == null){
            messageHandler.send(player, "player-not-found", "%target_name%", name);
            return true;
        }

        return false;
    }

    public void sendCooldownMessage(){TimeFormatter.getRemaining(this.cooldown - System.currentTimeMillis(), true);
        if (CooldownUtil.hasCooldown(this.player, this.getName() + "Command")) {
            ChatUtil.toPlayer(this.player, "");
        }
    }

    public void setCooldown(long cooldown){
        this.cooldown = cooldown;

        CooldownUtil.setCooldown(this.player, this.getName() + "Command", this.cooldown);
    }

    public void addArguments(Argument... arguments){
        Arrays.asList(arguments).forEach(this::addArgument);
    }

    public void addArgument(Argument argument) {
        this.arguments.add(argument);
    }

    public Argument getArgumentByName(String name) {
        for (Argument argument : this.arguments) {

            if (argument.getName().equalsIgnoreCase(name) || argument.getAliases().contains(name.toLowerCase())) {
                return argument;

            }
        }
        return null;
    }

    protected String[] fixedArray(String[] array) {
        String[] subArgs = new String[array.length - 1];
        System.arraycopy(array, 1, subArgs, 0, array.length - 1);
        return subArgs;
    }

    @Override public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] array) {
        if (array.length != 0) {
            return null;
        }

        List<String> list = new ArrayList<>();

        if (this.argumentBase) {
            for (Argument argument : this.arguments){

                if (!argument.getName().startsWith(array[0].toLowerCase())) {
                    continue;
                }

                if (argument.getPermission() == null || sender.hasPermission(argument.getPermission())) {
                    list.add(argument.getName());
                }
            }
        }

        return list;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    @Nullable
    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public void setPermission(String permission) {
        this.permission = permission;
    }

    public long getCooldown() {
        return cooldown;
    }

    public boolean isOnlyPlayers() {
        return onlyPlayers;
    }

    public void setOnlyPlayers(boolean onlyPlayers) {
        this.onlyPlayers = onlyPlayers;
    }

    public boolean isArgumentBase() {
        return argumentBase;
    }

    public void setArgumentBase(boolean argumentBase) {
        this.argumentBase = argumentBase;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(Set<Argument> arguments) {
        this.arguments = arguments;
    }

    public List<String> getArgumentBaseUsageMessage() {
        return argumentBaseUsageMessage;
    }

    public void setArgumentBaseUsageMessage(List<String> argumentBaseUsageMessage) {
        this.argumentBaseUsageMessage = argumentBaseUsageMessage;
    }
}
