package online.nasgar.survival.command.management;

import lombok.Getter;
import lombok.Setter;
import online.nasgar.survival.utils.text.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Argument  {

    private Command command;

    private String name, permission;

    private List<String> aliases;

    private CommandSender sender;
    private Player player;

    private boolean onlyPlayers;

    public Argument(String name) {
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
            ChatUtil.toSender(this.sender, "&cPlayer " + name + " &cnot found");
            return true;
        }

        return false;
    }

}
