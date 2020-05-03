package ch.dams333.factionCombat.utils;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SavedPlayer
{
    private final ItemStack[] contents;
    private final ItemStack[] armor;
    private final int level;
    private final float exp;
    private final double health;
    private final int food;
    private final GameMode gameMode;
    private final Location location;

    public SavedPlayer(PlayerInventory inventory, Double health, int food, GameMode gameMode, Location location)
    {
        this(inventory.getContents(), inventory.getArmorContents(),
                inventory.getHolder() instanceof Player ? ((Player)inventory.getHolder()).getLevel() : 0,
                inventory.getHolder() instanceof Player ? ((Player)inventory.getHolder()).getExp() : 0F, health, food, gameMode, location);
    }

    public SavedPlayer(ItemStack[] contents, ItemStack[] armor, int level, float exp, double health, int food, GameMode gameMode, Location location)
    {
        this.contents = contents;
        this.armor = armor;
        this.level = level;
        this.exp = exp;
        this.health = health;
        this.food = food;
        this.gameMode = gameMode;
        this.location = location;
    }

    public ItemStack[] getContents()
    {
        return contents;
    }

    public ItemStack[] getArmor()
    {
        return armor;
    }

    public float getExp()
    {
        return exp;
    }

    public int getLevel()
    {
        return level;
    }

    public void apply(Player player)
    {
        Player p = player.getPlayer();
        PlayerInventory inv = p.getInventory();
        inv.setContents(this.contents);
        inv.setArmorContents(this.armor);
        p.setLevel(this.level);
        p.setExp(this.exp);
        p.updateInventory();
        p.setHealth(health);
        p.setFoodLevel(food);
        p.setGameMode(gameMode);
        p.teleport(location);
    }
}