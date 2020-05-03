package ch.dams333.factionCombat.listener.blocks;

import ch.dams333.factionCombat.FactionCombat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEvent implements Listener {
    FactionCombat main;
    public BlockEvent(FactionCombat factionCombat) {
        this.main = factionCombat;
    }

    @EventHandler
    public void place(BlockPlaceEvent e){
        if(main.isWorldOfCombat(e.getPlayer().getWorld())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void breakB(BlockBreakEvent e){
        if(main.isWorldOfCombat(e.getPlayer().getWorld())){
            e.setCancelled(true);
        }
    }

}
