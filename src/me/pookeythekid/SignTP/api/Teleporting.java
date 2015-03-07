package me.pookeythekid.SignTP.api;

import java.io.File;

import me.pookeythekid.SignTP.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.skionz.dataapi.DataFile;

public class Teleporting {

	public Main M;
	
	private Msgs Msgs;

	public Teleporting(Main plugin) {

		M = plugin;
		
		Msgs = new Msgs(plugin);

	}


	/**
	 * Teleports a player to the specified warp.
	 * 
	 * @param sender - The player who executes the warp command. If {@code target} is {@code null}, this player will be the one to teleport.
	 * If {@code target} does have a value, that player will be the one to be teleported. Regardless, {@code sender} is the one who receives
	 * error reports and notifications that the teleportation was a success.
	 * 
	 * @param target - The player other than the sender to be teleported. This player will not receive error notifications if the teleportation
	 * was a failure. If the plugin config allows it, this player will only be told that they were teleported. If this parameter is {@code null},
	 * the method will teleport the {@code sender} instead.
	 * 
	 * @param warpName - The warp to which a player will be teleported. This is case insensitive, as the method converts it to lower case.
	 * 
	 * @return {@code true} if the teleportation was a success. {@code false} otherwise.
	 */
	public boolean teleport(Player sender, Player target, String warpName) {

		if (!M.warpsList.contains(warpName.toLowerCase())) {

			sender.sendMessage(Msgs.WarpNotExist());

			return false;

		}

		Location loc;

		if (target == null)

			loc = sender.getLocation();

		else

			loc = target.getLocation();

		DataFile warpFile = new DataFile(M.getDataFolder() + File.separator + "warps" + File.separator + warpName.toLowerCase(), "txt");

		double x = warpFile.getDouble("x");

		double y = warpFile.getDouble("y");

		double z = warpFile.getDouble("z");

		float pitch = warpFile.getFloat("pitch");

		float yaw = warpFile.getFloat("yaw");

		World world = Bukkit.getWorld(warpFile.getString("world"));

		loc.setX(x);

		loc.setY(y);

		loc.setZ(z);

		loc.setPitch(pitch);

		loc.setYaw(yaw);

		loc.setWorld(world);

		if (!loc.getBlock().getChunk().isLoaded())

			loc.getBlock().getChunk().load();

		if (target == null)

			sender.teleport(loc);

		else

			target.teleport(loc);

		if (M.getConfig().getBoolean("toggleTPMessage")) {

			if (target == null)

				sender.sendMessage(Msgs.YouTpd());

			else

				target.sendMessage(Msgs.YouTpd());

		}

		return true;

	}


	/**
	 * Teleports a player to the specified warp.
	 * 
	 * @param sender - The CommandSender who executes the warp command.
	 * 
	 * @param target - The player to be teleported. If the plugin config allows it, this player will only be told that they were teleported.
	 * 
	 * @param warpName - The warp to which a player will be teleported. This is case insensitive, as the method converts it to lower case.
	 * 
	 * @return {@code true} if the teleportation was a success. {@code false} otherwise.
	 */
	public boolean teleport(CommandSender sender, Player target, String warpName) {

		if (!M.warpsList.contains(warpName.toLowerCase())) {

			sender.sendMessage(Msgs.WarpNotExist());

			return false;

		}

		Location loc = target.getLocation();

		DataFile warpFile = new DataFile(M.getDataFolder() + File.separator + "warps" + File.separator + warpName.toLowerCase(), "txt");

		double x = warpFile.getDouble("x");

		double y = warpFile.getDouble("y");

		double z = warpFile.getDouble("z");

		float pitch = warpFile.getFloat("pitch");

		float yaw = warpFile.getFloat("yaw");

		World world = Bukkit.getWorld(warpFile.getString("world"));

		loc.setX(x);

		loc.setY(y);

		loc.setZ(z);

		loc.setPitch(pitch);

		loc.setYaw(yaw);

		loc.setWorld(world);

		if (!loc.getBlock().getChunk().isLoaded())

			loc.getBlock().getChunk().load();


		target.teleport(loc);


		if (M.getConfig().getBoolean("toggleTPMessage"))

			target.sendMessage(Msgs.YouTpd());


		return true;


	}


	// Old code, removed after v1.0.1

	/*
	public void useSign(Player p, String warpName) {

		Location ploc = p.getLocation();


		String location = M.warpMap.get(warpName.toLowerCase());

		String[] coords = location.split(" - ");

		String x = "";
		String y = "";
		String z = "";

		String pitch = "";
		String yaw = "";

		String world = "";

		for (String s : coords) {

			if (s.startsWith("x: "))
				x = s.replaceFirst("x: ", "");

			if (s.startsWith("y: "))
				y = s.replaceFirst("y: ", "");

			if (s.startsWith("z: "))
				z = s.replaceFirst("z: ", "");

			if (s.startsWith("pitch: "))
				pitch = s.replaceFirst("pitch: ", "");

			if (s.startsWith("yaw: "))
				yaw = s.replaceFirst("yaw: ", "");

			if (s.startsWith("world: "))
				world = s.replaceFirst("world: ", "");

		}


		double xloc = Double.valueOf(x);
		double yloc = Double.valueOf(y);
		double zloc = Double.valueOf(z);

		float pitchloc = Float.valueOf(pitch);
		float yawloc = Float.valueOf(yaw);

		ploc.setX(xloc);
		ploc.setY(yloc);
		ploc.setZ(zloc);

		ploc.setPitch(pitchloc);
		ploc.setYaw(yawloc);

		ploc.setWorld(Bukkit.getServer().getWorld(world));

		if (!ploc.getBlock().getChunk().isLoaded())
			ploc.getBlock().getChunk().load();

		p.teleport(ploc);

		if (M.getConfig().getBoolean("toggleTPMessage"))
			p.sendMessage(Msgs.YouTpd());

	}



	public void warpCmd(Player p, String warpName) {

		Location ploc = p.getLocation();


		String location = M.warpMap.get(warpName.toLowerCase());

		String[] coords = location.split(" - ");

		String x = "";
		String y = "";
		String z = "";

		String pitch = "";
		String yaw = "";

		String world = "";

		for (String s : coords) {

			if (s.startsWith("x: "))
				x = s.replaceFirst("x: ", "");

			if (s.startsWith("y: "))
				y = s.replaceFirst("y: ", "");

			if (s.startsWith("z: "))
				z = s.replaceFirst("z: ", "");

			if (s.startsWith("pitch: "))
				pitch = s.replaceFirst("pitch: ", "");

			if (s.startsWith("yaw: "))
				yaw = s.replaceFirst("yaw: ", "");

			if (s.startsWith("world: "))
				world = s.replaceFirst("world: ", "");

		}


		double xloc = Double.valueOf(x);
		double yloc = Double.valueOf(y);
		double zloc = Double.valueOf(z);

		float pitchloc = Float.valueOf(pitch);
		float yawloc = Float.valueOf(yaw);

		ploc.setX(xloc);
		ploc.setY(yloc);
		ploc.setZ(zloc);

		ploc.setPitch(pitchloc);
		ploc.setYaw(yawloc);

		ploc.setWorld(Bukkit.getServer().getWorld(world));

		if (!ploc.getBlock().getChunk().isLoaded())
			ploc.getBlock().getChunk().load();

		p.teleport(ploc);

		if (M.getConfig().getBoolean("toggleTPMessage"))
			p.sendMessage(Msgs.YouTpd());

	}
	 */


}
