package me.pookeythekid.SignTP.Messages;

import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.api.Colors;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Msgs {
	
	public Main M;
	
	private Colors Colors;
	
	public Msgs(Main plugin) {
		
		M = plugin;
		
		Colors = new Colors();
		
	}

	public String Tag() {
		
		if (!M.getConfig().getString("mainTag").equalsIgnoreCase("nothing"))
		
			return Colors.toChatColors(M.getConfig().getString("mainTag")) + ChatColor.RESET + " ";
		
		return "";
		
	}


	public void sendHelpMenu(CommandSender sender, int page) {
		
		if (page < 1) {
			
			sender.sendMessage(Tag() + ChatColor.YELLOW + "Help Menu | Page 1/2");

			sender.sendMessage(ChatColor.GOLD + "/SignTP " + ChatColor.DARK_AQUA + "- Show this help menu.");

			sender.sendMessage(ChatColor.GOLD + "/SignTP Help <Page> " + ChatColor.DARK_AQUA + "- Show this help menu or another page in it.");

			sender.sendMessage(ChatColor.GOLD + "/SignTP Reload " + ChatColor.DARK_AQUA + "- Reload the config.");

			sender.sendMessage(ChatColor.GOLD + "/SignTP Setwarp " + ChatColor.DARK_AQUA + "- Set a signwarp to your location.");
			
			return;
			
		}

		switch (page) {

		case 1:

			sender.sendMessage(Tag() + ChatColor.YELLOW + "Help Menu | Page 1/2");

			sender.sendMessage(ChatColor.GOLD + "/SignTP " + ChatColor.DARK_AQUA + "- Show this help menu.");

			sender.sendMessage(ChatColor.GOLD + "/SignTP Help <Page> " + ChatColor.DARK_AQUA + "- Show this help menu or another page in it.");

			sender.sendMessage(ChatColor.GOLD + "/SignTP Reload " + ChatColor.DARK_AQUA + "- Reload the config.");

			sender.sendMessage(ChatColor.GOLD + "/SignTP Setwarp " + ChatColor.DARK_AQUA + "- Set a signwarp to your location.");

			break;


		default:
		case 2:

			sender.sendMessage(Tag() + ChatColor.YELLOW + "Help Menu | Page 2/2");

			sender.sendMessage(ChatColor.GOLD + "/SignTP List " + ChatColor.DARK_AQUA + "- List all signwarps.");

			sender.sendMessage(ChatColor.GOLD + "/SignTP Warp <Warp Name> [Player] " + ChatColor.DARK_AQUA + "- Teleport yourself or"
					+ " another player to a signwarp.");
			
			sender.sendMessage(ChatColor.GOLD + "/SignTP Delwarp <Warp Name>");

			break;

		}

	}

	public String MustBePlayer() {

		return (Tag() + ChatColor.RED + "You must be a player to perform this command.");

	}

	public String NotEnoughArgs(String usage) {

		return (Tag() + ChatColor.RED + "Not enough arguments. Usage: " + ChatColor.GREEN + usage);

	}

	public String NoPerms() {

		return (Tag() + ChatColor.RED + "You don't have permission to perform this command.");

	}

	public String YouTpd() {

		return (Tag() + Colors.toChatColors(M.getConfig().getString("tpMessage")));

	}

	public String EcoOffWarn() {

		return (Tag() + ChatColor.RED + "Notice: " + ChatColor.GOLD
				+ "Economy is set to false in the SignTP config, but this sign contains a pricetag. Until economy is set to "
				+ "true in the config, this sign will not be able to charge players to teleport.");

	}

	public String InvalidPrice() {

		return (Tag() + ChatColor.RED + "Error: " + ChatColor.GOLD + "Invalid sign price. Prefix price with a $ sign, and only use digits, "
				+ "with the exception of a decimal point. Don't use negative numbers, either.");

	}

	public String SpecifyWarp() {

		return (Tag() + ChatColor.RED + "Please specify a valid warp.");

	}

	public String NotEnoughCash() {

		return (Tag() + ChatColor.RED + "Your funds are not sufficient to use this sign.");

	}

	public String CfgReloaded() {

		return (Tag() + ChatColor.GREEN + "Config reloaded.");

	}

	public String WarpSet() {

		return (Tag() + ChatColor.GREEN + "Warp set.");

	}
	
	public String WarpRemoved() {
		
		return (Tag() + ChatColor.GREEN + "Warp removed.");
		
	}
	
	public String WarpNotExist() {
		
		return (Tag() + ChatColor.RED + "That warp does not exist.");
		
	}
	
	public String UnknownCmd() {
		
		return (Tag() + ChatColor.RED + "Unknown subcommand.");
		
	}
	
	public String PlayerNotOnline() {
		
		return (Tag() + ChatColor.RED + "That player is not online.");
		
	}
	
	public String PrefixTooLong() {
		
		return (Tag() + ChatColor.RED + "That prefix is too long for the plugin to fit onto the sign. "
				+ "Perhaps you or an administrator can try simplifying the color codes in the config file.");
		
	}


}
