package me.pookeythekid.SignTP;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import me.pookeythekid.SignTP.Executors.CommandHub;
import me.pookeythekid.SignTP.Listeners.ChatListener;
import me.pookeythekid.SignTP.Listeners.SignCreation;
import me.pookeythekid.SignTP.Listeners.SignUsage;
import me.pookeythekid.SignTP.Permissions.Permissions;
import me.pookeythekid.SignTP.api.ReloadWarps;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.skionz.dataapi.DataFile;

public class Main extends JavaPlugin {

	public Logger logger = getLogger();


	/**
	 * Contains warp locations in string form. Other than in the process of auto-updating from v1.0.1 to a later version,
	 * this HashMap no longer has a use.
	 * <p>
	 * String key - The name of the warp, all lower case.
	 * <p>
	 * String value - The location in string form. For example: "x: 1 - y: 2 - z: 3 - pitch: 4 - yaw: 5 - world: world".
	 */
	public HashMap<String, String> warpMap = new HashMap<String, String>();


	/**
	 * Contains all warp names. All warp names are lower case.
	 */
	public ArrayList<String> warpsList = new ArrayList<String>();


	/**
	 * Contains players who are asked to overwrite a warp if they're setting a warp that already exists.
	 * <p>
	 * Player key - Players who are overwriting a warp.
	 * <p>
	 * String value - The warp name that they are overwriting. All lower case.
	 */
	public HashMap<Player, String> overwriters = new HashMap<Player, String>();


	private Permissions Permissions = new Permissions();

	private SignCreation SignCreation = new SignCreation(this);

	private SignUsage SignUsage = new SignUsage(this);

	private ChatListener ChatListener = new ChatListener(this);

	private CommandHub CommandHub = new CommandHub(this);

	private ReloadWarps ReloadWarps = new ReloadWarps(this);


	private boolean enabledOnce = false;

	public boolean economyIsOn = false;

	public static Economy economy = null;

	private boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	public void disable() {

		this.setEnabled(false);

	}

	public void enable() {

		this.setEnabled(true);

	}

	@Override
	public void onEnable() {

		if (!setupEconomy() && getConfig().getBoolean("useEconomy")) {

			economyIsOn = false;

			logger.severe("Error setting up economy with Vault! Proceeding without economy!");

		} else if (getConfig().getBoolean("useEconomy")) {

			economyIsOn = true;

			logger.info("Successfully linked to Vault.");

		}

		PluginManager pm = this.getServer().getPluginManager();

		Permissions.registerPerms(pm);

		if (!enabledOnce) {

			pm.registerEvents(SignCreation, this);

			pm.registerEvents(SignUsage, this);

			pm.registerEvents(ChatListener, this);

		}
		
		getCommand("signtp").setExecutor(CommandHub);

		ReloadWarps.reloadOldWarps();

		try {

			saveDefaultConfig();

			File oldWarpsFile = new File(getDataFolder(), "warps.txt");

			File dir = new File(getDataFolder(), "warps");

			if (!dir.exists())

				dir.mkdir();

			if (oldWarpsFile.exists()) {

				for (String s : this.warpsList) {

					File file = new File(dir, s + ".txt");

					if (!file.exists())
						file.createNewFile();

				}

				for (File file : dir.listFiles()) {

					if (!file.getName().endsWith(".yml")) {

						DataFile warpFile = new DataFile(dir + File.separator + file.getName().replace(".txt", ""), "txt");

						for (String s : this.warpMap.get(file.getName().replace(".txt", "")).split(" - ")) {

							if (s.startsWith("x: ")) {

								warpFile.set("x", s.replace("x: ", ""));

							}

							if (s.startsWith("y: ")) {

								warpFile.set("y", s.replace("y: ", ""));

							}

							if (s.startsWith("z: ")) {

								warpFile.set("z", s.replace("z: ", ""));

							}

							if (s.startsWith("pitch: ")) {

								warpFile.set("pitch", s.replace("pitch: ", ""));

							}

							if (s.startsWith("yaw: ")) {

								warpFile.set("yaw", s.replace("yaw: ", ""));

							}

							if (s.startsWith("world: ")) {

								warpFile.set("world", s.replace("world: ", ""));

							}

						}

					}

				}

				oldWarpsFile.delete();

			} else {

				for (File file : dir.listFiles()) {

					if (file.getName().endsWith(".yml")) {

						DataFile ymlFile = new DataFile(dir + File.separator + file.getName().replace(".yml", ""), "yml");

						DataFile dataFile = new DataFile(dir + File.separator + file.getName().replace(".yml", ""), "txt");

						// Remove the ' characters from the strings. The old YAML files like single quotes, DataAPI does not.

						dataFile.set("x", Double.valueOf(ymlFile.getString("x").replace("'", "")));

						dataFile.set("y", Double.valueOf(ymlFile.getString("y").replace("'", "")));

						dataFile.set("z", Double.valueOf(ymlFile.getString("z").replace("'", "")));

						dataFile.set("pitch", Float.valueOf(ymlFile.getString("pitch").replace("'", "")));

						dataFile.set("yaw", Float.valueOf(ymlFile.getString("yaw").replace("'", "")));

						dataFile.set("world", ymlFile.getString("world"));

						file.delete();

					}

				}

			}

		} catch (Exception e) { e.printStackTrace(); }


		ReloadWarps.reloadWarps();
		
		enabledOnce = true;


		// Old code, removed after v1.0.1.

		/*
		try {

			File warpsFile = new File(getDataFolder(), "warps.txt");

			if (!warpsFile.exists()) {

				warpsFile.createNewFile();

			}

			LineNumberReader lnr = new LineNumberReader(new FileReader(warpsFile));

			lnr.setLineNumber(1);

			String firstLine = lnr.readLine();

			lnr.close();

			if (firstLine == "" || firstLine == null) {

				FileWriter writer = new FileWriter(warpsFile);

				writer.write("warpnames: Warpiness\n\n");

				writer.write("Warp Warpiness\n");

				writer.write("x: 1\n");

				writer.write("y: 60\n");

				writer.write("z: 100\n");

				writer.write("Pitch: 1\n");

				writer.write("Yaw: 1\n");

				writer.write("World: world\n");

				writer.write("End Warp");

				writer.close();

			}

		} catch (Exception e) { e.printStackTrace(); }
		 */


	}

	@Override
	public void onDisable() {

		Permissions.removePerms(getServer().getPluginManager());

	}


}
