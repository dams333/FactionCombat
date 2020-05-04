package ch.dams333.factionCombat;

import ch.dams333.factionCombat.commands.admin.CancelCommand;
import ch.dams333.factionCombat.commands.admin.OpenCommand;
import ch.dams333.factionCombat.commands.admin.StartCommand;
import ch.dams333.factionCombat.commands.player.JoinCommand;
import ch.dams333.factionCombat.listener.blocks.BlockEvent;
import ch.dams333.factionCombat.listener.connexion.QuitEvent;
import ch.dams333.factionCombat.listener.damage.DamageEntityEvent;
import ch.dams333.factionCombat.listener.damage.DamageEvent;
import ch.dams333.factionCombat.listener.move.ChangeWorldEvent;
import ch.dams333.factionCombat.objects.game.Game;
import ch.dams333.factionCombat.utils.SavedPlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class FactionCombat extends JavaPlugin {

    public Map<Player, SavedPlayer> savedPlayers;

    public Game game = null;

    @EventHandler
    public void onEnable(){

        this.savedPlayers = new HashMap<>();

        getServer().getPluginManager().registerEvents(new QuitEvent(this), this);
        getServer().getPluginManager().registerEvents(new ChangeWorldEvent(this), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(this), this);
        getServer().getPluginManager().registerEvents(new DamageEntityEvent(this), this);
        getServer().getPluginManager().registerEvents(new BlockEvent(this), this);

        getCommand("open").setExecutor(new OpenCommand(this));
        getCommand("join").setExecutor(new JoinCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("cancel").setExecutor(new CancelCommand(this));

        System.out.println("[FactionCombat] Systeme de combat par Dams33 chargé avec succès");

    }

    @EventHandler
    public void onDisable(){

        for(Player p : this.savedPlayers.keySet()){
            this.savedPlayers.get(p).apply(p);
        }

    }

    public boolean isInGanme(Player p){
        if(this.game != null){
            if(this.game.getJoined().contains(p)){
                return true;
            }
        }
        return false;
    }

    public boolean isWorldOfCombat(World world){
        if(this.game != null){
            if(this.game.getArene().getWorld().getName().equalsIgnoreCase(world.getName())){
                return true;
            }
        }
        return false;
    }

}
