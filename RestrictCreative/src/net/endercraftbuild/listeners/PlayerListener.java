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

	private String prefix = ChatColor.RED + "[" + ChatColor.GOLD + "RestrictCreative" + ChatColor.RED + "] ";
	
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
		if (plugin.getConfig().getBoolean("block-pvp.enabled") == true); 
			event.setCancelled(true);
			String configmsg = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("block-pvp.message"));
			((Player)event.getDamager()).sendMessage(prefix + configmsg);
					}
				}
			}
		}
	}
	//Creative Item drop check
	@EventHandler
	public void Share(PlayerDropItemEvent e) {
		Player pl = e.getPlayer();
		if(plugin.getConfig().getBoolean("block-item-drop.enabled") == true)
		{
		if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
		{
		if(!(e.getPlayer().hasPermission("restrictcreative.bypass.drop")))
			e.setCancelled(true);
			String configmsg = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("block-item-drop.message"));
			pl.sendMessage(prefix + configmsg);
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
		if(!(event.getPlayer().hasPermission("restrictcreative.bypass.placeblacklist")))
		{
		if (plugin.getConfig().getBoolean("place-blacklist.enabled") == true) {
			List<Integer> blacklistID = plugin.getConfig().getIntegerList("place-blacklist.blacklist");
			Block placedBlock = event.getBlock();
				{
				if (blacklistID.contains(Integer.valueOf(placedBlock.getTypeId()))) {
					event.setCancelled(true);
					String configmsg = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("place-blacklist.message"));
					player.sendMessage(prefix + configmsg +  Material.getMaterial(placedBlock.getTypeId()) + "!");
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
		if (plugin.getConfig().getBoolean("chest-block.enabled") == true) {
		{
			if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest);
				e.setCancelled(true);
				String configmsg = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("chest-block.message"));
				p.sendMessage(prefix + configmsg); }
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
			List<String> CommandBlacklist = plugin.getConfig().getStringList("command-blacklist.blacklist");
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
					if (plugin.getConfig().getBoolean("command-blacklist.enabled") == true) {
					{
						if (CommandBlacklist.contains(command.getName().toLowerCase())); {
							String configmsg = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("command-blacklist.message"));
							e.setCancelled(true);
							player.sendMessage(prefix + configmsg); }
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
		if (plugin.getConfig().getBoolean("block-pickup.enabled") == true) {
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
		if (plugin.getConfig().getBoolean("break-blacklist.enabled") == true) {
				List<Integer> breakblacklistID = plugin.getConfig().getIntegerList("break-blacklist.blacklist");
				Block brokeBlock = event.getBlock();
					{
						if (breakblacklistID.contains(Integer.valueOf(brokeBlock.getTypeId()))) {
							String configmsg = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("breack-blacklist.message"));
							event.setCancelled(true);
							player.sendMessage(prefix + configmsg +  Material.getMaterial(brokeBlock.getTypeId()) + "!");
							}
						}
					}
				}
			}
		}
	}
}
