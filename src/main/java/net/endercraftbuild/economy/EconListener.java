package main.java.net.endercraftbuild.economy;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import main.java.net.endercraftbuild.Main;
import main.java.net.endercraftbuild.Utils;

public class EconListener implements Listener {
	
	private Main plugin;
	
	public EconListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		if (!plugin.getConfig().getBoolean("economy.pay-gamemode.enabled", true))
			return;
		if (Main.economy == null) {
			plugin.getLogger().warning("economy.pay-gamemode.enabled = true but Vault is not loaded");
			return;
		}
		
		Player player = event.getPlayer();
		
		if (event.getNewGameMode() != GameMode.CREATIVE)
			return;
		if (player.hasPermission("restrictcreative.bypass.paygm"))
			return;
		
		Double amount = plugin.getConfig().getDouble("economy.pay-gamemode.amount", 0.0);		
		EconomyResponse r = Main.economy.withdrawPlayer(player.getName(), amount); 
		
		if (r.transactionSuccess()) {
			Utils.sendMessage(player, plugin.getConfig().getString("economy.pay-gamemode.success").replace("{AMOUNT}", Main.economy.format(amount)));
		} else {
			Utils.sendMessage(player, plugin.getConfig().getString("economy.pay-gamemode.failure").replace("{AMOUNT}", Main.economy.format(amount))); 
			event.setCancelled(true);
		}
	}
	
}