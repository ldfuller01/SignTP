package me.pookeythekid.SignTP.Executors;

import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.Messages.Msgs;
import me.pookeythekid.SignTP.Permissions.Permissions;

import org.bukkit.command.CommandSender;

public class HelpSub {

	public Main M;

	private Permissions Permissions;
	
	private Msgs Msgs;

	public HelpSub(Main plugin) {

		M = plugin;

		Permissions = new Permissions();
		
		Msgs = new Msgs(plugin);

	}

	private boolean isInteger(String str) {

		if (str == null) {

			return false;

		}

		int length = str.length();

		if (length == 0) {

			return false;

		}

		int i = 0;

		if (str.charAt(0) == '-') {

			if (length == 1)

				return false;

			i = 1;

		}

		for (; i < length; i++) {

			char c = str.charAt(i);

			if (c <= '/' || c >= ':')

				return false;

		}

		return true;

	}


	public void helpCmd(CommandSender sender, String[] args) {

		if (sender.hasPermission(Permissions.mainCmd)) {

			if (args.length < 2)

				Msgs.sendHelpMenu(sender, 1);

			else if (args.length > 1) {

				if (this.isInteger(args[1]))

					Msgs.sendHelpMenu(sender, Integer.valueOf(args[1]));

				else

					Msgs.sendHelpMenu(sender, 1);

			}

		} else

			sender.sendMessage(Msgs.NoPerms());

	}



}
