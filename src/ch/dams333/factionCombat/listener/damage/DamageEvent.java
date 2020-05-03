package ch.dams333.factionCombat.listener.damage;

import ch.dams333.factionCombat.FactionCombat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {
    FactionCombat main;
    public DamageEvent(FactionCombat factionCombat) {
        this.main = factionCombat;
    }

    @EventHandler
    public void damage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(main.isWorldOfCombat(e.getEntity().getWorld()) && !main.game.isPvp()){
                e.setCancelled(true);
            }else{
                if(main.isWorldOfCombat(e.getEntity().getWorld()) && main.game.isPvp()){
                    Player p = (Player) e.getEntity();
                    if(e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK){
                        if(p.getHealth() <= e.getFinalDamage()){
                            e.setCancelled(true);
                            main.game.killPlayer(p, main);
                        }
                    }
                }
            }
        }
    }

}
