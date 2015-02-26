package me.pookeythekid.SignTP.Listeners;

import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.Messages.Msgs;
import me.pookeythekid.SignTP.Permissions.Permissions;
import me.pookeythekid.SignTP.api.Signs;
import me.pookeythekid.SignTP.api.Teleporting;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignUsage implements Listener {

	public Main M;

	private Signs Signs;

	private Teleporting Teleporting;

	private Permissions Permissions;
	
	private Msgs Msgs;

	public SignUsage(Main plugin) {

		M = plugin;

		Signs = new Signs(plugin);

		Teleporting = new Teleporting(plugin);

		Permissions = new Permissions();
		
		Msgs = new Msgs(plugin);

	}


	@EventHandler
	public void onSignUse(PlayerInteractEvent e) {
		
		Economy economy = Main.economy;

		Player p = e.getPlayer();

		Block clickedBlock = e.getClickedBlock();

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (clickedBlock.getType() == Material.SIGN
					|| clickedBlock.getType() == Material.SIGN_POST
					|| clickedBlock.getType() == Material.WALL_SIGN) {

				Sign sign = (Sign) clickedBlock.getState();

				if (Signs.signIsValid(sign)) {

					if (p.hasPermission(Permissions.useSign)) {
						
						/*
						 * Cancels the event so the player doesn't accidentally place a block on the sign.
						 */
						e.setCancelled(true);

						if (!M.economyIsOn || !Signs.signHasPrice(sign)) {

							Teleporting.teleport(p, null, sign.getLine(1));

						}

						else if (M.economyIsOn && Signs.signHasPrice(sign)) {
							
							if (!Signs.hasPriceFormat(sign))
								
								Signs.formatPrice(sign);

							double price = Signs.getPrice(sign);

							if (economy.getBalance(p) >= price) {

								economy.withdrawPlayer(p, price);

								Teleporting.teleport(p, null, sign.getLine(1));

							} else if (economy.getBalance(p) < price) {

								p.sendMessage(Msgs.NotEnoughCash());

							}

						}

					}

				}

			}

		}

	}



}
