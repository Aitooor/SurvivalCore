package online.nasgar.survivalcore.commands.gamemode;

import online.nasgar.survivalcore.utils.CC;
import online.nasgar.survivalcore.utils.command.BaseCommand;
import online.nasgar.survivalcore.utils.command.Command;
import online.nasgar.survivalcore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeCommand extends BaseCommand {
    @Override @Command(name = "gm", inGameOnly = true, aliases = {"gamemode", "modo"})
    public void onCommand(CommandArgs command) {

        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if(!player.hasPermission("survivalcore.gm")) {
            player.sendMessage(CC.NO_PERMISSIONS);
            return;
        } else {
            if (args.length < 1) {
                player.sendMessage(CC.noPrefix(""));
                player.sendMessage(CC.translate("&7&m---------------------------"));
                player.sendMessage(CC.translate("&eGamemode Help"));
                player.sendMessage(CC.translate(""));
                player.sendMessage(CC.translate("&f/gamemode &71 &8| &7c &8| &7creative"));
                player.sendMessage(CC.translate("&f/gamemode &70 &8| &7s &8| &7survival"));
                player.sendMessage(CC.translate("&f/gamemode &72 &8| &7a &8| &7adventure"));
                player.sendMessage(CC.translate("&f/gamemode &73 &8| &7spec &8| &7spectator"));
                player.sendMessage(CC.translate(""));
                player.sendMessage(CC.translate("&7&m---------------------------"));
                player.sendMessage(CC.noPrefix(""));
                return;
            }
        }

        if(args.length == 1) {

            if(!player.hasPermission("survivalcore.gm.0")) {
                player.sendMessage(CC.NO_PERMISSIONS);
                return;
            } else {
                if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(CC.translate("&aYour gamemode has been changed to &bSurvival"));
                    return;
                }
            }

            if(!player.hasPermission("survivalcore.gm.1")) {
                player.sendMessage(CC.NO_PERMISSIONS);
                return;
            } else {
                if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(CC.translate("&aYour gamemode has been changed to &bCreative"));
                    return;
                }
            }

            if(!player.hasPermission("survivalcore.gm.2")) {
                player.sendMessage(CC.NO_PERMISSIONS);
                return;
            } else {
                if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(CC.translate("&aYour gamemode has been changed to &bAdventure"));
                    return;
                }
            }

            if(!player.hasPermission("survivalcore.gm.3")) {
                player.sendMessage(CC.NO_PERMISSIONS);
                return;
            } else {
                if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spec") || args[0].equalsIgnoreCase("spectator")) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(CC.translate("&aYour gamemode has been changed to &bSpectator"));
                    return;
                }
            }
            return;
        }

        if(args.length == 2) {

            Player target = Bukkit.getPlayer(args[1]);

            if(!player.hasPermission("survivalcore.gm.other")) {
                player.sendMessage(CC.NO_PERMISSIONS);
                return;
            }
            
            if(target == null) {
                player.sendMessage(CC.translate("&cPlayer is offline"));
                return;
            }

            if(!player.hasPermission("survivalcore.gm.0.other")) {
                player.sendMessage(CC.NO_PERMISSIONS);
                return;
            } else {
                if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                    target.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(CC.translate("&aYour gamemode has been changed to &bSurvival"));
                }
            }

            if(!player.hasPermission("survivalcore.gm.1.other")) {
                player.sendMessage(CC.NO_PERMISSIONS);
                return;
            } else {
                if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                    target.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(CC.translate("&aYour gamemode has been changed to &bCreative"));
                }
            }

            if(!player.hasPermission("survivalcore.gm.2.other")) {
                player.sendMessage(CC.NO_PERMISSIONS);
                return;
            } else {
                if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
                    target.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(CC.translate("&aYour gamemode has been changed to &bAdventure"));
                }
            }

            if(!player.hasPermission("survivalcore.gm.0.other")) {
                player.sendMessage(CC.NO_PERMISSIONS);
                return;
            } else {
            if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spec") || args[0].equalsIgnoreCase("spectator")) {
                target.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(CC.translate("&aThe player " + target.getName() + " "));
            }

            return;
        }



    }
}

// /gamemode 1 [jugador]