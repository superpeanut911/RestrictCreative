package net.endercraftbuild;


import java.io.File;
import java.io.IOException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

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
	}

	public void onDisable(){
		getLogger().info("RestrictCreative has been disabled!"); {
		this.saveConfig();
		}
	}
}


