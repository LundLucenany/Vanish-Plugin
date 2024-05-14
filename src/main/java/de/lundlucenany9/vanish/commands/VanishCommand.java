package de.lundlucenany9.vanish.commands;

import de.lundlucenany9.vanish.Vanish;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class VanishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Vanish.mNotPlayer);
            return false;
        }
        Player p = (Player) sender;
        if(Vanish.vanishList.contains(p)){
            Vanish.vanishList.remove(p);
            for(Player target : Bukkit.getOnlinePlayers()){
                target.showPlayer(Vanish.vanishPlugin, p);
            }
            p.sendMessage(Vanish.mUnvanish);
            /*
            TranslatableComponent joinedComp = new TranslatableComponent();
            joinedComp.setTranslate("multiplayer.player.joined");
            joinedComp.addWith(p.getCustomName());
            joinedComp.setColor(ChatColor.YELLOW);
            joinedComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new ComponentBuilder("{name:\"" + p.getName() + "\", type:\"Player\", id:\"" + p.getUniqueId() + "\"}").create()));
            */
            new BukkitRunnable() {@Override public void run() {Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw @a [{\"selector\":\"@s\",\"color\":\"yellow\"},{\"translate\":\"multiplayer.player.joined\",\"color\":\"yellow\"}]".replace("@s", p.getName()));}}.runTask(Vanish.vanishPlugin);
            //Vanish.boardcast(joinedComp);
        }else {
            Vanish.vanishList.add(p);
            for(Player target : Bukkit.getOnlinePlayers()){
                if(target.hasPermission("vanish.see"))
                    continue;
                target.hidePlayer(Vanish.vanishPlugin, p);
            }

            TranslatableComponent leftComp = new TranslatableComponent();
            leftComp.setTranslate("multiplayer.player.left");
            leftComp.setColor(ChatColor.YELLOW);
            //leftComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new ComponentBuilder("{name:\"" + p.getName() + "\", type:\"Player\", id:\"" + p.getUniqueId() + "\"}").create()));
            Vanish.boardcast(leftComp);

            //new BukkitRunnable() {@Override public void run() {Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw @a [{\"selector\":\"@s\",\"color\":\"yellow\"},{\"translate\":\"multiplayer.player.left\",\"color\":\"yellow\"}]".replace("@s", p.getName()));}}.runTask(Vanish.vanishPlugin);
            p.sendMessage(Vanish.mVanish);
        }
        return true;
    }
}
