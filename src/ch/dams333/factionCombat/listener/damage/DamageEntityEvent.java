package ch.dams333.factionCombat.listener.damage;

import ch.dams333.factionCombat.FactionCombat;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntityEvent implements Listener {
    FactionCombat main;
    public DamageEntityEvent(FactionCombat factionCombat) {
        this.main = factionCombat;
    }

    @EventHandler
    public void kill(EntityDamageByEntityEvent e){

        if(e.getEntity() instanceof Player){
            if(main.isWorldOfCombat(e.getEntity().getWorld()) && !main.game.isPvp()){
                e.setCancelled(true);
            }else{
                if(main.isWorldOfCombat(e.getEntity().getWorld())){
                    Player p = (Player) e.getEntity();

                    if (e.getDamager() instanceof Arrow) {
                        if (((Arrow) e.getDamager()).getShooter() instanceof Player) {
                            if (((Player) e.getEntity()).getHealth() <= e.getFinalDamage()) {
                                Player shooted = (Player) e.getEntity();
                                Player shooter = (Player) ((Arrow) e.getDamager()).getShooter();
                                e.setCancelled(true);
                                main.game.killPlayerByPlayer(shooted, shooter, main);
                            }
                        }else{
                            if (((Player) e.getEntity()).getHealth() <= e.getFinalDamage()) {
                                Player shooted = (Player) e.getEntity();
                                e.setCancelled(true);
                                main.game.killPlayer(shooted, main);
                            }
                        }
                    }else if(e.getDamager() instanceof Player){
                        if (((Player) e.getEntity()).getHealth() <= e.getFinalDamage()) {
                            Player shooted = (Player) e.getEntity();
                            Player shooter = (Player) e.getDamager();
                            e.setCancelled(true);
                            main.game.killPlayerByPlayer(shooted, shooter, main);
                        }
                    }else{
                        if (((Player) e.getEntity()).getHealth() <= e.getFinalDamage()) {
                            Player shooted = (Player) e.getEntity();
                            e.setCancelled(true);
                            main.game.killPlayer(shooted, main);
                        }
                    }


                }
            }
        }

    }

}
