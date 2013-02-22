package main.java.net.endercraftbuild.inventories;

import java.io.*;

import main.java.net.endercraftbuild.Main;
import main.java.net.endercraftbuild.Utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.Inventory;

public class InvManager implements Listener {

	private Main plugin;

	public InvManager(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true) //Change inv on gm change
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		if (!plugin.getConfig().getBoolean("inventory-split.enabled", true))
			return;
		
		Player player = event.getPlayer();
		
		try {
			saveInventory(player, player.getGameMode());
			player.getInventory().clear();
			loadInventory(player, event.getNewGameMode());
		} catch (IOException e) {
			Utils.sendMessage(player, "&4Failed to load &6%s&4 inventory, staying in &6%s&4.", event.getNewGameMode(), player.getGameMode());
			event.setCancelled(true);
		}
	}
	
	// why a PlayerJoinEvent handler?
	// why a PlayerQuitEvent handler?
	// why a PlayerKickEvent handler?
	
	private void saveInventory(Player player, GameMode mode) throws IOException {
		String data = Serialization.toBase64(player.getInventory());
		File file = new File(plugin.inventoriesDirectory + "/" + player.getName() + "-" + mode.toString().toLowerCase() + "_.txt");
		
		if (!file.exists())
			file.createNewFile();
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(data);
		bufferedWriter.close();
	}
	
	private void loadInventory(Player player, GameMode mode) throws IOException {
		File file = new File(plugin.inventoriesDirectory + "/" + player.getName() + "-" + mode.toString().toLowerCase() + "_.txt");
		if (!file.exists())
			return;

		StringBuilder stringBuilder = new StringBuilder();

		FileReader fileReader = new FileReader(file);
		int charsRead = 0;
		char[] charArray = new char[8192];
		
		try {
			while ((charsRead = fileReader.read(charArray)) > 0)
				stringBuilder.append(charArray, 0, charsRead);
		} finally {
			fileReader.close();
		}
			
		Inventory inventory = Serialization.fromBase64(stringBuilder.toString());
		player.getInventory().setContents(inventory.getContents());
	}
	
}



