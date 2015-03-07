package me.pookeythekid.SignTP.Executors;

import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.Permissions.Permissions;
import me.pookeythekid.SignTP.api.Msgs;
import me.pookeythekid.SignTP.api.ReloadWarps;

import org.bukkit.command.CommandSender;

public class ReloadSub {

	public Main M;

	private Permissions Permissions;
	
	private Msgs Msgs;

	public ReloadSub(Main plugin) {

		M = plugin;

		Permissions = new Permissions();
		
		Msgs = new Msgs(plugin);

	}


	public void reloadCmd(CommandSender sender, String[] args) {

		if (sender.hasPermission(Permissions.reloadCmd)) {

			M.reloadConfig();

			new ReloadWarps(M).reloadWarps();
			
			if (!M.getConfig().getBoolean("useEconomy"))
				M.economyIsOn = false;
			
			else {
				
				if (Main.economy != null)
					M.economyIsOn = true;
				
				else {
					M.disable();
					M.enable();
				}
				
			}

			sender.sendMessage(Msgs.CfgReloaded());

		} else

			sender.sendMessage(Msgs.NoPerms());

	}


}
