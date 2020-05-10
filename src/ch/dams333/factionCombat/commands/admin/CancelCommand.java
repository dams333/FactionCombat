package ch.dams333.factionCombat.commands.admin;

import ch.dams333.factionCombat.FactionCombat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CancelCommand implements CommandExecutor {
    FactionCombat main;
    public CancelCommand(FactionCombat factionCombat) {
        this.main = factionCombat;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(main.game != null){
                p.sendMessage(ChatColor.LIGHT_PURPLE + "L event a bien été annulé");
                main.game.stop(main);
                return true;
            }
            p.sendMessage(ChatColor.RED + "Il n'y a pas d'event en cours");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Vous devez etre connecte sur le serveur pour faire cela");
        return false;
    }
}
