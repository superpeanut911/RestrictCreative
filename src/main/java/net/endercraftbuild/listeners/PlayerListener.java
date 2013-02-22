package main.java.net.endercraftbuild.listeners;

import java.util.List;

import main.java.net.endercraftbuild.Main;
import main.java.net.endercraftbuild.Utils;


import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Creature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerListener implements Listener {
	
	private Main plugin;

	public PlayerListener(Main plugin) {
		this.plugin = plugin;
	}

	//Creative PvP check
	@EventHandler(ignoreCancelled=true)
	public void onPlayerDamageByCreativePlayer(EntityDamageByEntityEvent event) {
		if (!plugin.getConfig().getBoolean("block-pvp.enabled", true))
			return;
		if (event.getDamager().getType() != EntityType.PLAYER || event.getEntity().getType() != EntityType.PLAYER)
			return;
		
		Player damager = (Player) event.getDamager();
		
		if (damager.getGameMode() != GameMode.CREATIVE)
			return;
		if (damager.hasPermission("restrictcreative.bypass.pvp"))
			return;
		
		Utils.sendMessage(damager, plugin.getConfig().getString("block-pvp.message"));
		event.setCancelled(true);
	}
	
	//Creative Item drop check
	@EventHandler
	public void onCreativePlayerDropItem(PlayerDropItemEvent event) {
		if (!plugin.getConfig().getBoolean("block-item-drop.enabled", true))
			return;
		
		Player player = event.getPlayer();
		
		if (player.getGameMode() != GameMode.CREATIVE)
			return;
		if (player.hasPermission("restrictcreative.bypass.drop"))
			return;
		
		Utils.sendMessage(player, plugin.getConfig().getString("block-item-drop.message"));
		event.setCancelled(true);
	}

	//Block place blacklist
	@EventHandler
	public void onCreativeBlockPlace(BlockPlaceEvent event) {
		if (!plugin.getConfig().getBoolean("place-blacklist.enabled", true))
			return;
		
		Player player = event.getPlayer();
		
		if (player.getGameMode() != GameMode.CREATIVE)
			return;
		if (player.hasPermission("restrictcreative.bypass.placeblacklist"))
			return;
		
		List<Integer> blacklistIdList = plugin.getConfig().getIntegerList("place-blacklist.blacklist");
		Block placedBlock = event.getBlock();
		
		if (!blacklistIdList.contains(placedBlock.getTypeId()))
			return;
		
		Utils.sendMessage(player, plugin.getConfig().getString("place-blacklist.message").replace("{BLOCK}", placedBlock.getType().toString()));
		event.setCancelled(true);
	}
	
	//Creative Chest block check
	@EventHandler
	public void onCreativeChestAccess(InventoryOpenEvent event) {
		if (!plugin.getConfig().getBoolean("chest-block.enabled", true))
			return;
		if (!(event.getInventory().getHolder() instanceof Chest) &&
				!(event.getInventory().getHolder() instanceof DoubleChest))
			return;
		
		Player player = (Player) event.getPlayer();
		
		if (player.getGameMode() != GameMode.CREATIVE)
			return;
		if (player.hasPermission("restrictcreative.bypass.chest"))
			return;
		
		Utils.sendMessage(player, plugin.getConfig().getString("chest-block.message"));
		event.setCancelled(true);
	}
	
	//Command blacklist check
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreativePlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
		if (!plugin.getConfig().getBoolean("command-blacklist.enabled", true))
			return;

		Player player = event.getPlayer();

		if (player.getGameMode() != GameMode.CREATIVE)
			return;
		if (player.hasPermission("restrictcreative.bypass.commands"))
			return;
		
		String[] cmdArg = event.getMessage().split(" ");
		Command command = plugin.getServer().getPluginCommand(cmdArg[0].trim().replaceFirst("/", ""));
		
		if (command == null)
			return;
		
		List<String> commandBlacklistList = plugin.getConfig().getStringList("command-blacklist.blacklist");
		
		if (!commandBlacklistList.contains(command.getName().toLowerCase()))
			return;
		
		Utils.sendMessage(player, plugin.getConfig().getString("command-blacklist.message"));
		event.setCancelled(true);
	}
	
	@EventHandler //Anti-Creative item pick up
	public void onCreativePlayerPickupItem(PlayerPickupItemEvent event) {
		if (!plugin.getConfig().getBoolean("block-pickup.enabled", true))
			return;
		
		Player player = event.getPlayer();
		
		if (player.getGameMode() != GameMode.CREATIVE)
			return;
		if (player.hasPermission("restrictcreative.bypass.pickup"))
			return;
		
		event.setCancelled(true);
	}
	
	//Block break blacklist
	@EventHandler
	public void onCreativeBlockBreak(BlockBreakEvent event) {
		if (!plugin.getConfig().getBoolean("break-blacklist.enabled", true))
			return;
		
		Player player = event.getPlayer();
		
		if (player.getGameMode() != GameMode.CREATIVE)
			return;
		if (player.hasPermission("restrictcreative.bypass.breakblacklist"))
			return;
		
		List<Integer> breakBlacklistIdList = plugin.getConfig().getIntegerList("break-blacklist.blacklist");
		Block brokenBlock = event.getBlock();
		
		if (!breakBlacklistIdList.contains(brokenBlock.getTypeId()))
			return;
		
		Utils.sendMessage(player, plugin.getConfig().getString("break-blacklist.message").replace("{BLOCK}", brokenBlock.getType().toString()));
		event.setCancelled(true);
	}

	@EventHandler//Block dealing damage to mobs
	public void onMobyDamageByCreativePlayer(EntityDamageByEntityEvent event) {
		if (!plugin.getConfig().getBoolean("block-mob-harming.enabled", true))
			return;
		if (event.getDamager().getType() != EntityType.PLAYER || !(event.getEntity() instanceof Creature))
			return;
		
		Player player = (Player) event.getDamager();

		if (player.getGameMode() != GameMode.CREATIVE)
			return;
		if (player.hasPermission("restrictcreative.bypass.mobs"))
			return;
		
		Utils.sendMessage(player, plugin.getConfig().getString("block-mob-harming.message"));
		event.setCancelled(true);
	}

	@EventHandler//Block spawneggs
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (!plugin.getConfig().getBoolean("spawnegg.enabled", true))
			return;
		
		if (event.getSpawnReason() != SpawnReason.SPAWNER_EGG)
			return;
		
		event.setCancelled(true);
	}
	
}




