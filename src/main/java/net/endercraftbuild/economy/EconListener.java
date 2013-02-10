package main.java.net.endercraftbuild.economy;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import main.java.net.endercraftbuild.Main;

public class EconListener implements Listener {

private String prefix = ChatColor.RED + "[" + ChatColor.GOLD + "RestrictCreative" + ChatColor.RED + "] ";
	
	
private Main plugin;

public EconListener(Main plugin) {
	this.plugin = plugin;
	}

@EventHandler
public void PayGm(PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		if(event.getNewGameMode() == GameMode.CREATIVE) {
			if(plugin.getConfig().getBoolean("economy.pay-gamemode.enabled") == true) {
			if(!player.hasPermission("restrictcreative.bypass.paygm")) {
		
		Double amount = plugin.getConfig().getDouble("economy.pay-gamemode.amount");		
		EconomyResponse r = Main.economy.withdrawPlayer(player.getName(), amount);
		if(r.transactionSuccess()) {
			String configmsg = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("economy.pay-gamemode.message"));
		
			player.sendMessage(prefix + configmsg );
			}
			else if(!r.transactionSuccess()) {
				event.setCancelled(true);
				player.sendMessage(prefix + ChatColor.RED + "You need: $" + amount + ChatColor.RED + " to go into creative!"); 
				}
			}	
		}
	}
}
}