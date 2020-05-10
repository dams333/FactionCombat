package ch.dams333.factionCombat.listener.move;

import ch.dams333.factionCombat.FactionCombat;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class ChangeWorldEvent implements Listener {
    FactionCombat main;
    public ChangeWorldEvent(FactionCombat factionCombat) {
        this.main = factionCombat;
    }

    @EventHandler
    public void changeWorld(PlayerTeleportEvent e){
        if(main.isWorldOfCombat(e.getFrom().getWorld())){
            if(e.getFrom().getWorld() != e.getTo().getWorld()){
                if(main.savedPlayers.keySet().contains(e.getPlayer())){
                    if(main.savedPlayers.get(e.getPlayer()) != null) {
                        main.savedPlayers.get(e.getPlayer()).apply(e.getPlayer());
                        main.savedPlayers.remove(e.getPlayer());
                        main.game.removePlayer(e.getPlayer());
                        e.getPlayer().sendMessage(ChatColor.RED + "Vous avez quitté le monde de l'event et êtes donc désinscrit");
                    }
                }
            }
        }
    }

}
