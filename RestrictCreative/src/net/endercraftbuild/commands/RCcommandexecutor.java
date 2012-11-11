package net.endercraftbuild.commands;

import net.endercraftbuild.Main;

import org.bukkit.ChatColor;
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
		if (cmd.getName().equalsIgnoreCase("rc")) {
			{
				if (args.length == 1) {
					{
						if (args[0].equalsIgnoreCase("reload")) {
							{
								if (sender instanceof Player) 
								{
									if (sender.hasPermission("restrictcreative.reload")) {
										{
											plugin.reloadConfig();
											sender.sendMessage(ChatColor.AQUA + "[RestrictCreative]" + ChatColor.GREEN + " Plugin Reloaded!");
										}
									}
									else {
										sender.sendMessage(ChatColor.AQUA + "[RestrictCreative]" + ChatColor.RED + " You do not have permission to use this command!");
									}
								}
							}
						}
						else if(cmd.getName().equalsIgnoreCase("rc"))
						{
							if(args.length == 1) {
								{
									if(args[0].equalsIgnoreCase("help")) {
										{
											if (sender instanceof Player) 
											{
												sender.sendMessage(ChatColor.GREEN + "=========" + ChatColor.AQUA + ChatColor.BOLD + "RestrictCreative" + ChatColor.GREEN +  "=========" );
												sender.sendMessage(ChatColor.YELLOW + "RestrictCreative v 1.4.2 by superpeanut911");
												sender.sendMessage(ChatColor.DARK_GREEN + "Commands:");
												sender.sendMessage(ChatColor.GREEN + "/rc reload - Reloads plugin");
												sender.sendMessage(ChatColor.GREEN + "==================================" );
											}
										}
									}
								}
							}
							{
								return true;
							}
						}
					}
				}
			}
			return true;
		}
	return true;
	}
	{
	}
}