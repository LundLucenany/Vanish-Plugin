package de.lundlucenany9.vanish.listeners;

import de.lundlucenany9.vanish.Vanish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoinListener implements Listener {
    @EventHandler
    void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(p.hasPermission("vanish.see"))
            return;
        for(Player vanished : Vanish.vanishList){
            p.hidePlayer(Vanish.vanishPlugin, vanished);
        }
    }
}
