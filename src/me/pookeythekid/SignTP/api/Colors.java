package me.pookeythekid.SignTP.api;

import org.bukkit.ChatColor;

public class Colors {
	
	/**
	 * Converts the given string into a color coded string.
	 * 
	 * @param string - The string to manipulate.
	 * @return A string with color codes replaced by chatcolors.
	 */
	public String toChatColors(String string) {
		
		string = string.replaceAll("&1", ChatColor.DARK_BLUE + "");
		string = string.replaceAll("&2", ChatColor.DARK_GREEN + "");
		string = string.replaceAll("&3", ChatColor.DARK_AQUA + "");
		string = string.replaceAll("&4", ChatColor.DARK_RED + "");
		string = string.replaceAll("&5", ChatColor.DARK_PURPLE + "");
		string = string.replaceAll("&6", ChatColor.GOLD + "");
		string = string.replaceAll("&7", ChatColor.GRAY + "");
		string = string.replaceAll("&8", ChatColor.DARK_GRAY + "");
		string = string.replaceAll("&9", ChatColor.BLUE + "");
		string = string.replaceAll("&0", ChatColor.BLACK + "");
		
		string = string.replaceAll("&a", ChatColor.GREEN + "");
		string = string.replaceAll("&b", ChatColor.AQUA + "");
		string = string.replaceAll("&c", ChatColor.RED + "");
		string = string.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
		string = string.replaceAll("&e", ChatColor.YELLOW + "");
		string = string.replaceAll("&f", ChatColor.WHITE + "");
		
		string = string.replaceAll("&l", ChatColor.BOLD + "");
		string = string.replaceAll("&o", ChatColor.ITALIC + "");
		string = string.replaceAll("&m", ChatColor.STRIKETHROUGH + "");
		string = string.replaceAll("&n", ChatColor.UNDERLINE + "");
		string = string.replaceAll("&k", ChatColor.MAGIC + "");
		
		return string;
		
	}
	
	/**
	 * Strips color code text out of the given string.
	 * 
	 * @param string - The string to manipulate.
	 * @return The given string without color code text.
	 */
	public String noChatColors(String string) {

		string = string.replaceAll("&1", "");
		string = string.replaceAll("&2", "");
		string = string.replaceAll("&3", "");
		string = string.replaceAll("&4", "");
		string = string.replaceAll("&5", "");
		string = string.replaceAll("&6", "");
		string = string.replaceAll("&7", "");
		string = string.replaceAll("&8", "");
		string = string.replaceAll("&9", "");
		string = string.replaceAll("&0", "");

		string = string.replaceAll("&a", "");
		string = string.replaceAll("&b", "");
		string = string.replaceAll("&c", "");
		string = string.replaceAll("&d", "");
		string = string.replaceAll("&e", "");
		string = string.replaceAll("&f", "");

		string = string.replaceAll("&l", "");
		string = string.replaceAll("&o", "");
		string = string.replaceAll("&m", "");
		string = string.replaceAll("&n", "");
		string = string.replaceAll("&k", "");

		return string;

	}
	

}
