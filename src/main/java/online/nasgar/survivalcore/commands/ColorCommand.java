package online.nasgar.survivalcore.commands;

import online.nasgar.survivalcore.utils.CC;
import online.nasgar.survivalcore.utils.command.BaseCommand;
import online.nasgar.survivalcore.utils.command.Command;
import online.nasgar.survivalcore.utils.command.CommandArgs;
import org.bukkit.entity.Player;


public class ColorCommand extends BaseCommand {
    @Override @Command(name = "color", inGameOnly = true, aliases = {"colores", "colors"})
    public void onCommand(CommandArgs command) {

        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if(!player.hasPermission("survivalcore.color")) {
            player.sendMessage(CC.NO_PERMISSIONS);
            return;
        }

        if(args.length < 1) {
            CC.translate("         &a&lCOLORES DISPONIBLES");
            CC.translate("");
            CC.translate(" &7+" + " &f0 &f=  &0Negro");
            CC.translate(" &7+" + " &f1 &f=  &1Azul Oscuro");
            CC.translate(" &7+" + " &f2 &f= &2Verde Oscuro");
            CC.translate(" &7+" + " &f3 &f= &3Aqua Oscuro");
            CC.translate(" &7+" + " &f4 &f= &4Rojo Oscuro");
            CC.translate(" &7+" + " &f5 &f= &5Purpura Oscuro");
            CC.translate(" &7+" + " &f6 &f= &6Oro");
            CC.translate(" &7+" + " &f7 &f= &7Gris");
            CC.translate(" &7+" + " &f8 &f= &8Gris Oscuro");
            CC.translate(" &7+" + " &f9 &f= &9Azul");
            CC.translate(" &7+" + " &fa &f= &aVerde");
            CC.translate(" &7+" + " &fb &f= &bAqua");
            CC.translate(" &7+" + " &fc &f= &cRojo");
            CC.translate(" &7+" + " &fd &f= &dPurpura Ligero");
            CC.translate(" &7+" + " &fe &f= &eAmarillo");
            CC.translate(" &7+" + " &ff &f= &fBlanco");
            CC.translate("");
            CC.translate("");
        }
    }
}
