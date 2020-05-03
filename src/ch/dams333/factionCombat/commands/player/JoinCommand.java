package ch.dams333.factionCombat.commands.player;

import ch.dams333.factionCombat.FactionCombat;
import ch.dams333.factionCombat.utils.SavedPlayer;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandExecutor {
    FactionCombat main;
    public JoinCommand(FactionCombat factionCombat) {
        this.main = factionCombat;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!main.game.isStarted()){
                if(main.game.isPlace()){
                    if(main.game.getType().equalsIgnoreCase("faction")){
                        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(p);
                        Faction faction = fPlayer.getFaction();
                        if(faction == null){
                            p.sendMessage(ChatColor.RED + "Ce combat est un combat de faction, vous devez donc en rejoindre une !");
                            return true;
                        }
                    }
                    main.savedPlayers.put(p, new SavedPlayer(p.getInventory(), p.getHealth(), p.getFoodLevel(), p.getGameMode(), p.getLocation()));
                    main.game.join(p);
                    return true;
                }
                p.sendMessage(ChatColor.RED + "Il n'y a plus de place disponible dans ce combat");
                return true;
            }
            p.sendMessage(ChatColor.RED + "Le combat a déjà démarré");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Il faut etre conencte sur le serveur pour faire cela");
        return false;
    }
}
