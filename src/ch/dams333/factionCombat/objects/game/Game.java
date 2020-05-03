package ch.dams333.factionCombat.objects.game;


import ch.dams333.factionCombat.FactionCombat;
import ch.dams333.factionCombat.objects.game.tasks.ResetTask;
import ch.dams333.factionCombat.objects.game.tasks.StartingTask;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String type;
    private int limit;
    private Location spec;
    private Location arene;
    private boolean pvp;

    private List<Player> joined;
    private List<Player> inGame;

    private boolean started;

    public String getType() {
        return type;
    }

    public int getLimit() {
        return limit;
    }

    public Location getSpec() {
        return spec;
    }

    public Location getArene() {
        return arene;
    }

    public boolean isPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    public Game(String type, int limit, Location spec, Location arene) {
        this.type = type;
        this.limit = limit;
        this.spec = spec;
        this.arene = arene;
        joined = new ArrayList<>();
        inGame = new ArrayList<>();
        started = false;
        pvp = false;

    }

    public boolean isStarted() {
        return started;
    }

    public boolean isPlace() {
        if(limit > 0){
            if(this.joined.size() >= limit){
                return false;
            }
        }
        return true;
    }

    public void join(Player p) {
        p.teleport(spec);
        p.sendMessage(ChatColor.GOLD + "Vous avez bien rejoint le combat, veuillez attendre que celui-ci démarre");
        p.setGameMode(GameMode.ADVENTURE);
        this.joined.add(p);
    }

    public List<Player> getJoined() {
        return joined;
    }

    public void removePlayer(Player player) {
        this.joined.remove(player);
        this.limit = this.limit - 1;
        if(isStarted()){
            this.inGame.remove(player);
            for(Player p : this.joined){
                p.sendMessage(player.getDisplayName() + ChatColor.RED + " est mort car il s'est déconnecté");
            }
        }
    }

    public void start(FactionCombat main) {
        this.started = true;
        for(Player p : this.joined){
            p.teleport(arene);
            this.inGame.add(p);

            ItemStack he = new ItemStack(Material.DIAMOND_HELMET);
            he.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            ItemStack ch = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ch.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            ItemStack le = new ItemStack(Material.DIAMOND_LEGGINGS);
            le.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            ItemStack bo = new ItemStack(Material.DIAMOND_BOOTS);
            bo.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

            ItemStack sw = new ItemStack(Material.DIAMOND_SWORD);
            sw.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
            sw.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);

            p.getInventory().setHelmet(he);
            p.getInventory().setChestplate(ch);
            p.getInventory().setLeggings(le);
            p.getInventory().setBoots(bo);

            p.getInventory().addItem(sw);
            p.getInventory().addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
            p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));

            ItemStack healthPotion = new ItemStack(Material.SPLASH_POTION, 1);
            PotionMeta healthM = (PotionMeta) healthPotion.getItemMeta();
            healthM.setColor(Color.RED);
            healthM.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1,1, true), true);
            healthPotion.setItemMeta(healthM);

            for(int i = 0; i < 17; i++){
                p.getInventory().addItem(healthPotion);
            }

        }
        StartingTask startingTask = new StartingTask(main, this.inGame);
        startingTask.runTaskTimer(main, 20, 20);
    }

    public void pvp() {
        this.pvp = true;
    }

    public void killPlayer(Player p, FactionCombat main) {
        for(Player inGame : joined){
            inGame.sendMessage(p.getDisplayName() + ChatColor.RED + " est mort !");
        }
        this.inGame.remove(p);
        p.getInventory().clear();
        p.setGameMode(GameMode.SPECTATOR);
        checkWin(main);
    }

    private void checkWin(FactionCombat main) {
        if(this.type.equalsIgnoreCase("1vs1")){
            if(this.inGame.size() == 1){
                for(Player join : joined){
                    join.sendMessage(this.inGame.get(0).getDisplayName() + ChatColor.GOLD + " remporte le combat !");
                    join.sendMessage(ChatColor.LIGHT_PURPLE + "Retour à votre position d'avant le combat dans 10 secondes");
                    ResetTask resetTask = new ResetTask(main, this.joined);
                    resetTask.runTaskTimer(main, 20, 20);
                }
            }
        }
        if(this.type.equalsIgnoreCase("faction")){
            Faction preFac = null;
            boolean win = true;
            for(Player game : this.inGame){
                FPlayer fPlayer = FPlayers.getInstance().getByPlayer(game);
                Faction faction = fPlayer.getFaction();
                if(preFac == null) preFac = faction;
                if(preFac != faction){
                    win = false;
                }
            }
            if(win){
                for(Player join : joined){
                    join.sendMessage(ChatColor.GOLD + "La faction " + preFac.getTag() + ChatColor.GOLD + " remporte le combat !");
                    join.sendMessage(ChatColor.LIGHT_PURPLE + "Retour à votre position d'avant le combat dans 10 secondes");
                    ResetTask resetTask = new ResetTask(main, this.joined);
                    resetTask.runTaskTimer(main, 20, 20);
                }
            }
        }
    }

    public void killPlayerByPlayer(Player p, Player damager, FactionCombat main) {
        for(Player inGame : joined){
            inGame.sendMessage(p.getDisplayName() + ChatColor.RED + " est mort de la main de " + damager.getDisplayName() + ChatColor.RED + " !");
        }
        this.inGame.remove(p);
        p.getInventory().clear();
        p.setGameMode(GameMode.SPECTATOR);
        checkWin(main);
    }
}
