package me.pookeythekid.SignTP.Executors;

import java.io.File;

import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.Permissions.Permissions;
import me.pookeythekid.SignTP.api.Msgs;
import me.pookeythekid.SignTP.api.ReloadWarps;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DelwarpSub {

	public Main M;
	
	private ReloadWarps ReloadWarps;

	private Permissions Permissions;
	
	private Msgs Msgs;

	public DelwarpSub(Main plugin) {

		M = plugin;
		
		ReloadWarps = new ReloadWarps(plugin);

		Permissions = new Permissions();
		
		Msgs = new Msgs(plugin);

	}


	public void deleteWarp(CommandSender sender, String[] args) {

		if (sender.hasPermission(Permissions.delWarp)) {

			if (args.length > 1) {

				if (M.warpsList.contains(args[1].toLowerCase())) {

					String warpName = args[1].toLowerCase();

					File file = new File(M.getDataFolder(), "warps" + File.separator + warpName + ".txt");

					if (file.delete()) {
						
						ReloadWarps.reloadWarps();

						sender.sendMessage(Msgs.WarpRemoved());
						
					}

					else

						sender.sendMessage(Msgs.Tag() + ChatColor.RED + "An error occured while deleting the warp.");

				} else
					
					sender.sendMessage(Msgs.WarpNotExist());

			} else

				sender.sendMessage(Msgs.NotEnoughArgs("/SignTP Delwarp <Warp Name>"));

		} else

			sender.sendMessage(Msgs.NoPerms());

	}


}
