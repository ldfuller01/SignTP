package me.pookeythekid.SignTP.Executors;

import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.Messages.Msgs;
import me.pookeythekid.SignTP.Permissions.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ListSub {

	public Main M;
	
	private Permissions Permissions;
	
	private Msgs Msgs;

	public ListSub(Main plugin) {

		M = plugin;
		
		Permissions = new Permissions();
		
		Msgs = new Msgs(plugin);

	}

	public void listWarps(CommandSender sender, String[] args) {

		if (sender.hasPermission(Permissions.listWarps)) {

			StringBuffer warps = new StringBuffer();

			int i = 1;

			for (String s : M.warpsList) {

				if (i < M.warpsList.size())
					warps.append(s + ", ");

				else if (i == M.warpsList.size())
					warps.append(s);
				
				i++;

			}

			sender.sendMessage(Msgs.Tag() + ChatColor.GREEN + "Warp List: " + ChatColor.RED + warps.toString());

		} else
			
			sender.sendMessage(Msgs.NoPerms());

	}


}
