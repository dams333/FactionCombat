package ch.dams333.factionCombat.listener.connexion;

import ch.dams333.factionCombat.FactionCombat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {
    FactionCombat main;
    public QuitEvent(FactionCombat factionCombat) {
        this.main = factionCombat;
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        if(main.isInGanme(e.getPlayer())){
            if(main.savedPlayers.keySet().contains(e.getPlayer())){
                if(main.savedPlayers.get(e.getPlayer()) != null) {
                    main.savedPlayers.get(e.getPlayer()).apply(e.getPlayer());
                    main.savedPlayers.remove(e.getPlayer());
                    main.game.removePlayer(e.getPlayer());
                }
            }
        }
    }

}
