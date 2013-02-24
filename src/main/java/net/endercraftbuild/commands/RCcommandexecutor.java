package net.endercraftbuild.commands;

import net.endercraftbuild.Main;
import net.endercraftbuild.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RCcommandexecutor implements CommandExecutor {
	
	private Main plugin;
	
	public RCcommandexecutor(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player!");
			return true;
		}
		
		Player player = (Player) sender;
		
		if (args.length < 1) {
			player.sendMessage(cmd.getUsage());
			return true;
		}
		
		if (args[0].equalsIgnoreCase("reload")) {
			if (player.hasPermission("restrictcreative.reload")) {
				plugin.reloadConfig();
				Utils.sendMessage(player, "&6Plugin Reloaded!");
			} else {
				Utils.sendMessage(player, "&4You do not have permission to use this command!");
			}
		} else if (args[0].equalsIgnoreCase("help")) {
			Utils.sendMessage(player, "&a=========&b&lRestrictCreative&a=========");
			Utils.sendMessage(player, "&eRestrictCreative v 1.5.4 by superpeanut911");
			Utils.sendMessage(player, "&2Commands:");
			Utils.sendMessage(player, "&a/rc reload - Reloads plugin");
			Utils.sendMessage(player, "&a==================================");
		}
		
		return true;
	}
	
}