package me.pookeythekid.SignTP.Executors;

import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.Messages.Msgs;
import me.pookeythekid.SignTP.Permissions.Permissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHub implements CommandExecutor {


	public Main M;

	private HelpSub HelpSub;

	private ReloadSub ReloadSub;

	private SetwarpSub SetwarpSub;

	private WarpSub WarpSub;

	private ListSub ListSub;
	
	private DelwarpSub DelwarpSub;

	private Permissions Permissions;
	
	private Msgs Msgs;

	public CommandHub(Main plugin) {

		M = plugin;

		HelpSub = new HelpSub(plugin);

		ReloadSub = new ReloadSub(plugin);

		SetwarpSub = new SetwarpSub(plugin);

		WarpSub = new WarpSub(plugin);

		ListSub = new ListSub(plugin);
		
		DelwarpSub = new DelwarpSub(plugin);

		Permissions = new Permissions();
		
		Msgs = new Msgs(plugin);

	}


	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (commandLabel.equalsIgnoreCase("signtp")) {

			if (sender.hasPermission(Permissions.mainCmd)) {
				
				if (args.length == 0 || (args.length > 0 
						&& args[0].equalsIgnoreCase("help")))

					HelpSub.helpCmd(sender, args);

				else if (args.length > 0 && args[0].equalsIgnoreCase("reload"))

					ReloadSub.reloadCmd(sender, args);

				else if (args.length > 0 && args[0].equalsIgnoreCase("setwarp"))

					SetwarpSub.setWarpCmd(sender, args);

				else if (args.length > 0 && args[0].equalsIgnoreCase("warp"))

					WarpSub.warpCmd(sender, args);

				else if (args.length > 0 && args[0].equalsIgnoreCase("list"))

					ListSub.listWarps(sender, args);
				
				else if (args.length > 0 && args[0].equalsIgnoreCase("delwarp"))
					
					DelwarpSub.deleteWarp(sender, args);
				
				else if (args.length > 0)
					
					sender.sendMessage(Msgs.UnknownCmd());
				
			}


		}


		return true;

	}



}
