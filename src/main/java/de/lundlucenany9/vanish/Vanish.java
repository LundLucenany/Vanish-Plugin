package de.lundlucenany9.vanish;

import de.lundlucenany9.vanish.commands.VanishCommand;
import de.lundlucenany9.vanish.listeners.OnJoinListener;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class Vanish extends JavaPlugin {
    public static String mVanish;
    public static String mUnvanish;
    public static String mNotPlayer;
    public static List<Player> vanishList;
    public static Plugin vanishPlugin;


    @Override
    public void onEnable() {
        //initialisation
        vanishPlugin = this;
        vanishList = new ArrayList<>();

        //config
        this.saveResource("config.yml", false);
        mVanish = this.getConfig().getString("messages.vanish");
        mUnvanish = this.getConfig().getString("messages.unvanish");
        mNotPlayer = this.getConfig().getString("messages.notPlayer");

        //events
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new OnJoinListener(), this);

        //commands
        getCommand("vanish").setExecutor(new VanishCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static void boardcast(BaseComponent... comp){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.spigot().sendMessage(comp);
        }
    }

}
