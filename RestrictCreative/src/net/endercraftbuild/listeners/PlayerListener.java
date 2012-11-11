package net.endercraftbuild.listeners;

import java.util.List;

import net.endercraftbuild.Main;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerListener implements Listener {
	private Main plugin;

	public PlayerListener(Main instance) {

		plugin = instance;

	}

	//Creative PvP check
	@EventHandler(ignoreCancelled=true)
	public void onHit(EntityDamageByEntityEvent event)
	{
		if (event.getDamager().getType().equals(EntityType.PLAYER))
		{
			if (((Player)event.getDamager()).getGameMode().equals(GameMode.CREATIVE))
			{
				if (!((Player)event.getDamager()).hasPermission("restrictcreative.bypass.pvp"))
				{
					if (event.getEntity().getType().equals(EntityType.PLAYER)) {
						if (plugin.getConfig().getBoolean("restrictions.can_pvp") == false) 
							event.setCancelled(true);
						((Player)event.getDamager()).sendMessage(ChatColor.AQUA + "[RestrictCreative]" + ChatColor.RED + " PvP in Creative mode is not permitted!");
					}
				}
			}
		}
	}
	//Creative Item drop check
	@EventHandler
	public void Share(PlayerDropItemEvent e) {
		Player pl = e.getPlayer();
		if(plugin.getConfig().getBoolean("restrictions.can_dropitems") == false)
		{
			if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			{
				if(!(e.getPlayer().hasPermission("restrictcreative.bypass.drop")))
				{
					e.setCancelled(true);
					{
						if(plugin.getConfig().getBoolean("restrictions.can_dropitems") == false)
						{
							if(!(e.getPlayer().hasPermission("restrictcreative.bypass.drop")))
							{
								if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
								{
									pl.sendMessage(ChatColor.AQUA + "[RestrictCreative]" + ChatColor.RED + " You are not allowed to drop items while in creative mode!");
								}
							}
						}
					}
				}
			}
		}
	}
	//Block place blacklist
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		{
			if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			{
				if(!(event.getPlayer().hasPermission("restrictcreative.bypass.blacklist")))
				{
					if (plugin.getConfig().getBoolean("restrictions.use_block_place_blacklist") == true) {
						List<Integer> blacklistID = plugin.getConfig().getIntegerList("restrictions.blocked_blocks");
						Block placedBlock = event.getBlock();
						{
							if (blacklistID.contains(Integer.valueOf(placedBlock.getTypeId()))) {
								event.setCancelled(true);
								player.sendMessage(ChatColor.AQUA + "[RestrictCreative]" + ChatColor.RED + " You are not allowed to place " +  Material.getMaterial(placedBlock.getTypeId()) + "!");
							}
						}
					}
				}
			}
		}
	}
	//Creative Chest block check
	@EventHandler
	public void ChestBlock(InventoryOpenEvent e){
		Player p = (Player) e.getPlayer();
		{
			if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			{
				if(!(e.getPlayer().hasPermission("restrictcreative.bypass.chest")))
				{
					if (plugin.getConfig().getBoolean("restrictions.can_access_chests") == false) {
						{
							if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest);
							e.setCancelled(true);
							p.sendMessage(ChatColor.AQUA + "[RestrictCreative]" + ChatColor.RED + " You are not allowed to access chests while in creative mode!"); }
					}
				}
			}
		}
	}
	//Command blacklist check
	@EventHandler(priority=EventPriority.MONITOR)
	public void CommandBlacklist(PlayerCommandPreprocessEvent e) {
		{
			Player player = e.getPlayer();
			String[] cmdArg = e.getMessage().split(" ");
			Command command = plugin.getServer().getPluginCommand(cmdArg[0].trim().replaceFirst("/", ""));
			List<String> CommandBlacklist = plugin.getConfig().getStringList("restrictions.blocked_commands");
			{
				if (command == null) 
				{
					return;
				}
				if (!CommandBlacklist.contains(command.getName().toLowerCase())) 
				{
					return;
				}   
				else {
					if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
					{
						if(!(e.getPlayer().hasPermission("restrictcreative.bypass.commands")))
						{
							if (plugin.getConfig().getBoolean("restrictions.use_command_blacklist") == true) {
								{
									if (CommandBlacklist.contains(command.getName().toLowerCase())); {
										e.setCancelled(true);
										player.sendMessage(ChatColor.AQUA + "[RestrictCreative]" + ChatColor.RED + " That command is not permitted in creative mode!"); }
								}	
							}
						}
					}
				}
			}
		}
	}
	@EventHandler //Anti-Creative item pick up
	public void creativepickup(PlayerPickupItemEvent event) {
		if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
		{
			if(!(event.getPlayer().hasPermission("restrictcreative.bypass.pickup")))
			{
				if (plugin.getConfig().getBoolean("restrictions.allow_pickup") == false) {
					event.setCancelled(true);
				}
			}

		}
	}
	//Block break blacklist. untested!
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		{
			if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			{
				if(!(event.getPlayer().hasPermission("restrictcreative.bypass.breakblacklist")))
				{
					if (plugin.getConfig().getBoolean("restrictions.use_block_break_blacklist") == true) {
						List<Integer> breakblacklistID = plugin.getConfig().getIntegerList("restrictions.blockbreak_blacklist");
						Block brokeBlock = event.getBlock();
						{
							if (breakblacklistID.contains(Integer.valueOf(brokeBlock.getTypeId()))) {
								event.setCancelled(true);
								player.sendMessage(ChatColor.AQUA + "[RestrictCreative]" + ChatColor.RED + " You are not allowed to break " +  Material.getMaterial(brokeBlock.getTypeId()) + "!");
							}
						}
					}
				}
			}
		}
	}
}


