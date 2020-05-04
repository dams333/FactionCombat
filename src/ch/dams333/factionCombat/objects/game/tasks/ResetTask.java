package ch.dams333.factionCombat.objects.game.tasks;

import ch.dams333.factionCombat.FactionCombat;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class ResetTask extends BukkitRunnable {
    FactionCombat main;
    List<Player> joined;
    int timer;
    public ResetTask(FactionCombat main, List<Player> joined) {
        this.main = main;
        this.joined = joined;
        this.timer = 11;
    }

    @Override
    public void run() {
        timer--;
        if(timer == 0){
            for(Player p : main.savedPlayers.keySet()){
                main.savedPlayers.get(p).apply(p);
                cancel();
            }
            main.savedPlayers = new HashMap<>();
            main.game = null;
        }
    }
}
