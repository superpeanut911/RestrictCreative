package net.endercraftbuild.inventories;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.ItemStack;

public class InvSplit implements Listener {

	public HashMap<Player, ItemStack[]> creativeinventories = new HashMap<Player, ItemStack[]>();
	public HashMap<Player, ItemStack[]> survivalinventories = new HashMap<Player, ItemStack[]>();
/*
 * RestrictCreative Inv Split
 * TODO
 * Acutally make it work
 * Save hashmap to a file
 * A lot of stuff....
 * Just getting an idea of what to do here...
 */
	@EventHandler
	public void Split(PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		ItemStack[] inv = player.getInventory().getContents();
		if(event.getNewGameMode() == GameMode.CREATIVE);
		{
			survivalinventories.put(player,inv);
			player.getInventory().clear();
			//If null create a new blank creative inventory file
			player.getInventory().setContents(creativeinventories.get(player));
		}
		if(event.getNewGameMode() == GameMode.SURVIVAL);
		creativeinventories.put(player,inv); //Acutally save the hashmap to a file
		player.getInventory().clear();
		//If null create a new blank survival inventory file
		player.getInventory().setContents(survivalinventories.get(player));//Load from a file
	}
	{
	}
	{
	}
}