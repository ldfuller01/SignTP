package me.pookeythekid.SignTP.Listeners;

import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.Executors.SetwarpSub;
import me.pookeythekid.SignTP.Messages.Msgs;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener {
	
	public Main M;
	
	private SetwarpSub SetwarpSub;
	
	private Msgs Msgs;
	
	public ChatListener(Main plugin) {
		
		M = plugin;
		
		SetwarpSub = new SetwarpSub(plugin);
		
		Msgs = new Msgs(plugin);
		
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent e) {
		
		if (M.overwriters.containsKey(e.getPlayer())) {
			
			if (e.getMessage().toLowerCase().startsWith("yes")) {
				
				e.setCancelled(true);
				
				SetwarpSub.overwriteWarp(e.getPlayer(), M.overwriters.get(e.getPlayer()));
				
				return;
				
			}
			
			if (e.getMessage().toLowerCase().startsWith("no")) {
				
				e.setCancelled(true);
				
				M.overwriters.remove(e.getPlayer());
				
				e.getPlayer().sendMessage(Msgs.Tag() + ChatColor.GREEN + "Cancelled.");
				
				return;
				
			}
			
			e.setCancelled(true);
			
			e.getPlayer().sendMessage(Msgs.Tag() + ChatColor.AQUA + "Chat " + ChatColor.GREEN + "\"yes\""
					+ ChatColor.AQUA + " to overwrite the warp, or chat " + ChatColor.RED + "\"no\""
					+ ChatColor.AQUA + " to cancel.");
			
		}
		
	}
	

}
