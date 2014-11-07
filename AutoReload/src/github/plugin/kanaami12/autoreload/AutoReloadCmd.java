package github.plugin.kanaami12.autoreload;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class AutoReloadCmd implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        final Reload2 Rel = Reload2.plugin;
        Entity en = (Entity)p;
        for ( Entity near : en.getNearbyEntities(15.0, 15.0, 15.0) ) {
            if ( near instanceof Player ) {
                Player player = (Player)near;
                player.sendMessage("");
            }
        }
        if(args.length == 0 || args[0].equalsIgnoreCase("info")){
            p.sendMessage("§7version = §R§C4.2.0§R\n"
                        + "§7author  = §Rkanaami12§R\n");

            return true;
        }
        if(args[0].equalsIgnoreCase("on")){
            p.sendMessage("§7AutoReload Mode §Con\n");
            Reload2.reloadMode = true;

            return true;
        }
        if(args[0].equalsIgnoreCase("off")){
            p.sendMessage("§7AutoReload Mode §Coff\n");
            Reload2.OffMode = true;

            return true;
        }
        if(args[0].equalsIgnoreCase("stop")){
            p.sendMessage("§7AutoReload Mode §Cstop\n");
            Reload2.reloadMode = false;

            return true;
        }else{
            p.sendMessage("§C/autoreload\n"
                        + "/autoreload info\n"
                        + "/autoreload on/off/reload§R");
            return true;
        }
    }
}


