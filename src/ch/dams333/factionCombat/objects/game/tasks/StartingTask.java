package ch.dams333.factionCombat.objects.game.tasks;

import ch.dams333.factionCombat.FactionCombat;
import net.minecraft.server.v1_15_R1.ChatMessageType;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class StartingTask extends BukkitRunnable {
    FactionCombat main;
    List<Player> inGame;
    int timer;
    public StartingTask(FactionCombat main, List<Player> inGame) {
        this.main = main;
        this.inGame = inGame;
        timer = 11;
    }

    @Override
    public void run() {
        timer --;
        if(timer > 0){
            for(Player p : inGame){
                sendActionBar(p, ChatColor.DARK_GREEN + "PVP dans " + ChatColor.GREEN + timer + " secondes");
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            }
        }else{
            for(Player p : inGame){
                sendActionBar(p, ChatColor.RED + "Le PVP est actif");
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 10, 1);
                main.game.pvp();
                cancel();
            }
        }
    }


    private void sendActionBar(Player player, String message) {
        IChatBaseComponent chat = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");

        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(chat, ChatMessageType.GAME_INFO);

        CraftPlayer craft = (CraftPlayer) player;

        craft.getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }
}
