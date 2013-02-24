package net.endercraftbuild;

import java.io.File;
import java.io.IOException;

import net.endercraftbuild.commands.RCcommandexecutor;
import net.endercraftbuild.listeners.PlayerListener;
import net.endercraftbuild.metrics.Metrics;
import net.endercraftbuild.updater.Updater;
import net.endercraftbuild.inventories.InvManager;
import net.endercraftbuild.economy.EconListener;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public Updater updater = new Updater(this, "nocreativepvp", this.getFile(), Updater.UpdateType.DEFAULT, false);
	public static Economy economy = null;
	public File inventoriesDirectory = null;
	
	@Override
	public void onEnable() {
		File configFile = new File(getDataFolder() + "/config.yml");
		if (!configFile.exists())
			this.saveDefaultConfig();
		
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}
		
		inventoriesDirectory = new File(getDataFolder() + "/Inventories");
		if (!inventoriesDirectory.exists())
			inventoriesDirectory.mkdir();
		
		getLogger().info("RestrictCreative by superpeanut911 has been enabled!");
		
		getCommand("rc").setExecutor(new RCcommandexecutor(this));
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new InvManager(this), this);
		pm.registerEvents(new EconListener(this), this);
//		getConfig().options().copyDefaults(true);
		
		if(setupEconomy()) 
			getLogger().info("Hooked into Vault!"); 
		else
			getLogger().warning("Warning no economy plugin found!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("RestrictCreative has been disabled!");
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null)
			economy = economyProvider.getProvider();
		return (economy != null);
	}
	
	public static Economy getEconomy() {
		return economy;
	}
	
}