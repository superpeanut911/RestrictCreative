package main.java.net.endercraftbuild;


import java.io.File;
import java.io.IOException;

import main.java.net.endercraftbuild.commands.RCcommandexecutor;
import main.java.net.endercraftbuild.listeners.PlayerListener;
import main.java.net.endercraftbuild.metrics.Metrics;
import main.java.net.endercraftbuild.updater.Updater;
import main.java.net.endercraftbuild.inventories.InvManager;
import main.java.net.endercraftbuild.economy.EconListener;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener  {
	
	Updater updater = new Updater(this, "nocreativepvp", this.getFile(), Updater.UpdateType.DEFAULT, false);
	
	public static Economy economy = null;
	
	public void onEnable(){
		
		File configFile = new File(this.getDataFolder() + "/config.yml");{
			if(!configFile.exists())
			{
				this.saveDefaultConfig(); }
			try {
				Metrics metrics = new Metrics(this);
				metrics.start();
			} catch (IOException e) {
				// Failed to submit the stats :-(
			}
		}
		
		getLogger().info("RestrictCreative by superpeanut911 has been enabled!");
		getCommand("rc").setExecutor(new RCcommandexecutor(this));
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new InvManager(this), this);
		pm.registerEvents(new EconListener(this), this);
		getConfig().options().copyDefaults(true);
		if(setupEconomy()) 
			System.out.println("[RestrictCreative] Hooked into Vault!"); 
			else
				if (!setupEconomy()) { System.out.println("[RestrictCreative] Warning no economy plugin found!"); }
		
	}

	public void onDisable(){
		getLogger().info("RestrictCreative has been disabled!"); {
			}
		}
	{
}

private boolean setupEconomy()
{
	RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	if (economyProvider != null) {
		economy = economyProvider.getProvider();
	}

	return (economy != null);
}

public static Economy getEconomy() {
	return economy;
}
}