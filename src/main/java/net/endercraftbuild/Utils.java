package main.java.net.endercraftbuild;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
	private static String prefix = "&4[&6RestrictCreative&4] ";
	
	public static void sendMessage(Player player, String message, Object... args) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(prefix + message, args)));
	}
}
