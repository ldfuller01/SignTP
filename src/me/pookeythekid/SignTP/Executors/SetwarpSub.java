package me.pookeythekid.SignTP.Executors;

import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.Messages.Msgs;
import me.pookeythekid.SignTP.Permissions.Permissions;
import me.pookeythekid.SignTP.api.ReloadWarps;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.skionz.dataapi.DataFile;

public class SetwarpSub {

	public Main M;

	private ReloadWarps ReloadWarps;

	private Permissions Permissions;
	
	private Msgs Msgs;

	public SetwarpSub(Main plugin) {

		M = plugin;

		ReloadWarps = new ReloadWarps(plugin);

		Permissions = new Permissions();
		
		Msgs = new Msgs(plugin);

	}


	public void setWarpCmd(CommandSender sender, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			if (sender.hasPermission(Permissions.setWarp)) {

				if (args.length < 2)

					sender.sendMessage(Msgs.NotEnoughArgs("/SignTP Setwarp <Warp Name>"));

				else {

					if (M.warpsList.contains(args[1].toLowerCase())) {

						M.overwriters.put(p, args[1].toLowerCase());

						p.sendMessage(Msgs.Tag() + ChatColor.RED + "That warp already exists. Would you like to overwrite it?" + "\n"
								+ ChatColor.AQUA + "Chat " + ChatColor.GREEN + "\"yes\""
								+ ChatColor.AQUA + " to overwrite the warp, or chat " + ChatColor.RED + "\"no\""
								+ ChatColor.AQUA + " to cancel.");

					} else
						
						this.setWarp(p, args[1].toLowerCase());

				}

			} else

				sender.sendMessage(Msgs.NoPerms());

		} else

			sender.sendMessage(Msgs.MustBePlayer());

	}


	public void overwriteWarp(Player p, String warpName) {
		
		p.sendMessage(Msgs.Tag() + ChatColor.GREEN + "Overwriting warp...");
		
		this.setWarp(p, warpName.toLowerCase());
		
		M.overwriters.remove(p);
		
	}
	
	
	public void setWarp(Player p, String warpName) {
		
		// MyConfig warpFile = M.configManager.getNewConfig("warps\\" + warpName.toLowerCase() + ".yml");
		
		DataFile warpFile = new DataFile(M.getDataFolder() + "\\warps\\" + warpName.toLowerCase(), "txt");
		
		Location loc = p.getLocation();
		
		double x = loc.getX();
		
		double y = loc.getY();
		
		double z = loc.getZ();
		
		float pitch = loc.getPitch();
		
		float yaw = loc.getYaw();
		
		String world = loc.getWorld().getName();
		
		warpFile.set("x", x);
		
		warpFile.set("y", y);
		
		warpFile.set("z", z);
		
		warpFile.set("pitch", pitch);
		
		warpFile.set("yaw", yaw);
		
		warpFile.set("world", world);
		
		ReloadWarps.reloadWarps();
		
		p.sendMessage(Msgs.WarpSet());
		
	}


	// Old code, removed after v1.0.1.

	/*
	public void setWarpCmd(CommandSender sender, String[] args) {

		if (sender.hasPermission(Permissions.mainCmd)) {

			if (args.length > 1) {

				if (args[0].equalsIgnoreCase("setwarp")) {

					if (sender.hasPermission(Permissions.setWarp)) {

						if (!(sender instanceof Player)) {

							sender.sendMessage(Msgs.MustBePlayer());

						} else if (sender instanceof Player) {

							Player p = (Player) sender;

							Location loc = p.getLocation();

							try {


								File warpsFile = new File(M.getDataFolder(), "warps.txt");

								LineNumberReader reader = new LineNumberReader(new FileReader(warpsFile));

								StringBuffer sb = new StringBuffer();

								int lnNum = 1;

								while (true) {

									reader.setLineNumber(lnNum);

									String line = reader.readLine();

									if (line == null) {
										lnNum = 1;
										break;
									}

									if (lnNum == 1)
										sb.append(line + "," +  args[1] + "\n");

									else if (lnNum > 1)
										sb.append(line + "\n");

									lnNum++;


								}


								reader.close();


								FileWriter writer = new FileWriter(warpsFile);

								sb.append("\n" +

										"Warp " + args[1] + "\n"
										+ "x: " + loc.getX() + "\n"
										+ "y: " + loc.getY() + "\n"
										+ "z: " + loc.getZ() + "\n"
										+ "Pitch: " + loc.getPitch() + "\n"
										+ "Yaw: " + loc.getYaw() + "\n"
										+ "World: " + loc.getWorld().getName() + "\n"
										+ "End Warp"

										);

								writer.write(sb.toString());

								writer.flush();
								writer.close();

								sb.setLength(0);


								ReloadWarps.reloadWarps();

								p.sendMessage(Msgs.WarpSet());



							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					} else if (!(sender.hasPermission(Permissions.setWarp)))
						sender.sendMessage(Msgs.NoPerms());

				}

			} else if (args.length < 2 && args[0].equalsIgnoreCase("setwarp")) {

				if (!(sender instanceof Player))

					sender.sendMessage(Msgs.MustBePlayer());

				else if (sender instanceof Player)

					sender.sendMessage(Msgs.NotEnoughArgs("/SignTP Setwarp <Warp Name>"));

			}

		} else if (!sender.hasPermission(Permissions.mainCmd))
			sender.sendMessage(Msgs.NoPerms());



	}
	 */



}
