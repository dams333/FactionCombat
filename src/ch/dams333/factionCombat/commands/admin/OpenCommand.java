package ch.dams333.factionCombat.commands.admin;

import ch.dams333.factionCombat.FactionCombat;
import ch.dams333.factionCombat.objects.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenCommand implements CommandExecutor {
    FactionCombat main;
    public OpenCommand(FactionCombat factionCombat) {
        this.main = factionCombat;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(main.game == null) {
                if (args.length == 9) {
                    String worldName = args[0];
                    new WorldCreator(worldName).createWorld();
                    if (Bukkit.getWorld(worldName) != null) {
                        if (isInt(args[1])) {
                            int limit = Integer.parseInt(args[1]);
                            if (limit >= 0) {
                                if (args[2].equalsIgnoreCase("faction") || args[2].equalsIgnoreCase("1vs1")) {
                                    if (isDouble(args[3]) && isDouble(args[4]) && isDouble(args[5]) && isDouble(args[6]) && isDouble(args[7]) && isDouble(args[8])) {
                                        Location spec = new Location(Bukkit.getWorld(worldName), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]));
                                        Location arene = new Location(Bukkit.getWorld(worldName), Double.parseDouble(args[6]), Double.parseDouble(args[7]), Double.parseDouble(args[8]));
                                        main.game = new Game(args[2], limit, spec, arene);
                                        p.teleport(spec);
                                        p.sendMessage(ChatColor.DARK_GREEN + "La game est ouverte");

                                        for(Player online : Bukkit.getOnlinePlayers()){
                                            online.sendMessage(ChatColor.LIGHT_PURPLE + "L'event PvP est à présent ouvert. Faîtes " + ChatColor.BOLD + "/join" + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + " pour y participer !");
                                        }

                                        return true;
                                    }
                                    p.sendMessage(ChatColor.RED + "Veuillez entrer des coordonées valides");
                                    return true;
                                }
                                p.sendMessage(ChatColor.RED + "/open " + args[0] + " " + args[1] + " <faction/1vs1>");
                                return true;
                            }
                        }
                        p.sendMessage(ChatColor.RED + "Veuillez entre un nombre valide pour la limite de joueurs");
                        return true;
                    }
                    p.sendMessage(ChatColor.RED + "Ce monde n'existe pas");
                    return true;
                }
                p.sendMessage(ChatColor.RED + "/open <world name> <limit of player> <type> <X gradin> <Y gradin> <Z gradin> <X arene> <Y arene> <Z arene>");
                return true;
            }
            p.sendMessage(ChatColor.RED + "Une partie est déjà en cours");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Vous devez etre connecte sur le serveur pour faire cela");
        return false;
    }




    public boolean isInt(String strNum) {
        boolean ret = true;
        try {

            Integer.parseInt(strNum);

        }catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }

    public boolean isDouble(String strNum) {
        boolean ret = true;
        try {

            Double.parseDouble(strNum);

        }catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }
}
