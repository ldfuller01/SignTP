package me.pookeythekid.SignTP.Executors;

import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.Messages.Msgs;
import me.pookeythekid.SignTP.Permissions.Permissions;
import me.pookeythekid.SignTP.api.Teleporting;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpSub {

	public Main M;

	private Teleporting Teleporting;

	private Permissions Permissions;
	
	private Msgs Msgs;

	public WarpSub(Main plugin) {

		M = plugin;

		Teleporting = new Teleporting(plugin);

		Permissions = new Permissions();
		
		Msgs = new Msgs(plugin);

	}


	@SuppressWarnings("deprecation")
	public void warpCmd(CommandSender sender, String[] args) {

		if (sender.hasPermission(Permissions.warpCmd)) {

			if (sender instanceof Player) {

				Player p = (Player) sender;

				if (args.length == 2) {

					Teleporting.teleport(p, null, args[1]);

				} else if (args.length > 2) {

					if (p.hasPermission(Permissions.warpOther)) {

						if (Bukkit.getPlayer(args[2]) == null)

							sender.sendMessage(Msgs.PlayerNotOnline());

						else {

							Player target = Bukkit.getPlayer(args[2]);

							if (Teleporting.teleport(p, target, args[1]))

								p.sendMessage(Msgs.Tag() + ChatColor.GREEN + "Teleported player " + target.getName()
										+ " to warp " + args[1] + ".");

						}

					} else

						Teleporting.teleport(p, null, args[1]);

				}

				else if (args.length < 2)

					if (p.hasPermission(Permissions.warpOther))

						p.sendMessage(Msgs.NotEnoughArgs("/SignTP Warp <Warp Name> [Player]"));

					else

						p.sendMessage(Msgs.NotEnoughArgs("/SignTP Warp <Warp Name>"));

			} else if (!(sender instanceof Player))

				if (args.length > 2) {
					
					if (Bukkit.getPlayer(args[2]) == null)

						sender.sendMessage(Msgs.PlayerNotOnline());

					else {

						Player target = Bukkit.getPlayer(args[2]);

						if (Teleporting.teleport(sender, target, args[1]))

							sender.sendMessage(Msgs.Tag() + ChatColor.GREEN + "Teleported player " + target.getName()
									+ " to warp " + args[1] + ".");

					}
					
				} else
					
					sender.sendMessage(Msgs.NotEnoughArgs("/SignTP Warp <Warp Name> <Player>"));

		} else

			sender.sendMessage(Msgs.NoPerms());

	}


}
