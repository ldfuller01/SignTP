package me.pookeythekid.SignTP.api;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import me.pookeythekid.SignTP.Main;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

public class Signs {

	public Main M;

	private Colors Colors;

	private Msgs Msgs;

	public Signs(Main plugin) {

		M = plugin;

		Colors = new Colors();

		Msgs = new Msgs(plugin);

	}

	/**
	 * Checks if the provided string is an integer.
	 * 
	 * @param str String to check.
	 * @return {@code true} if the string is an integer.
	 */
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

	/**
	 * Gets the first line of the given sign.
	 * 
	 * @param sign Sign to get prefix from.
	 * @return The first line of the given sign.
	 */
	public String getPrefix(Sign sign) {

		return sign.getLine(0);

	}

	/**
	 * Gets what the config has a valid sign's prefix set to.
	 * 
	 * @return A List of the allowed prefixes as defined by the config, all Strings are lowercased.
	 */
	public List<String> getCfgPrefixes() {

		List<String> cfgPrefixes = M.getConfig().getStringList("signPrefixes");

		return cfgPrefixes;

	}

	/**
	 * Checks if a sign has a valid SignTP prefix.
	 * 
	 * @param sign Sign to check prefix of.
	 * @return {@code true} if the given sign has a valid SignTP prefix.
	 */
	public boolean signHasPrefix(Sign sign) {

		List<String> cfgPrefixes = this.getCfgPrefixes();

		List<String> lowerCasePrefixes = new ArrayList<String>();

		for (String s : cfgPrefixes) {

			lowerCasePrefixes.add(Colors.toChatColors(s.toLowerCase()));

		}

		String signPrefix = Colors.noChatColors(sign.getLine(0));

		return lowerCasePrefixes.contains(signPrefix.toLowerCase());

	}

	/**
	 * Checks if the sign from the given SignChangeEvent has a valid SignTP prefix.
	 * 
	 * @param e Event to use.
	 * @return {@code true} if the sign in the given SignChangeEvent has a valid SignTP prefix.
	 */
	public boolean signHasPrefix(SignChangeEvent e) {

		List<String> cfgPrefixes = this.getCfgPrefixes();

		List<String> lowerCasePrefixes = new ArrayList<String>();

		for (String s : cfgPrefixes) {

			lowerCasePrefixes.add(Colors.noChatColors(s.toLowerCase()));

		}

		String signPrefix = Colors.noChatColors(e.getLine(0));

		return lowerCasePrefixes.contains(signPrefix.toLowerCase());

	}

	/**
	 * Checks if the given sign has any form of a price on the fourth line.
	 * 
	 * @param sign Sign whose price to check.
	 * @return {@code true} if the fourth line both has numbers in it.
	 */
	public boolean signHasPrice(Sign sign) {
		
		String priceLine = ChatColor.stripColor(sign.getLine(3));

		if (!M.getConfig().getString("pricetag").isEmpty() && priceLine.contains(M.getConfig().getString("pricetag")))

			return true;

		for (char c : sign.getLine(3).toCharArray())

			if (this.isInteger(String.valueOf(c)))

				return true;

		return false;

	}

	/**
	 * Checks if the sign in the given SignChangeEvent has some form of a price on the fourth line.
	 * 
	 * @param e Event to use.
	 * @return {@code true} if the fourth line has numbers in it.
	 */
	public boolean signHasPrice(SignChangeEvent e) {
		
		String priceLine = ChatColor.stripColor(e.getLine(3));

		if (!M.getConfig().getString("pricetag").isEmpty() && priceLine.contains(M.getConfig().getString("pricetag")))

			return true;

		for (char c : e.getLine(3).toCharArray())

			if (this.isInteger(String.valueOf(c)))

				return true;

		return false;

	}

	/**
	 * Checks if the given sign meets the minimum requirements of a SignTP sign.
	 * 
	 * @param sign Sign to check validity of.
	 * @return {@code true} if it has a valid SignTP prefix and addresses a valid warp name.
	 */
	public boolean signIsValid(Sign sign) {

		return signHasPrefix(sign) &&  M.warpsList.contains(sign.getLine(1).toLowerCase());

	}

	/**
	 * Checks if the sign in the given SignChangeEvent meets the minimum requirements of a SignTP sign.
	 * 
	 * @param e Event to use.
	 * @return {@code true} if it has a valid SignTP prefix and addresses a valid warp name.
	 */
	public boolean signIsValid(SignChangeEvent e) {

		return signHasPrefix(e) && M.warpsList.contains(e.getLine(1).toLowerCase());

	}

	/**
	 * Checks if the given sign's price tag is invalid.
	 * 
	 * @param sign Sign to check the pricetag of.
	 * @return {@code true} if the price is not in a format such as "$1.99".
	 */
	public boolean signPriceInvalid(Sign sign) {

		String priceLine = ChatColor.stripColor(sign.getLine(3));

		int decimals = 0;

		for (char c : priceLine.toCharArray()) {

			if (c == '-')

				return true;

			if (decimals > 1)

				return true;

			if (c == '.')

				decimals++;

		}

		if (priceLine.equals("") || priceLine.equals(null))

			return true;

		else {

			if (!priceLine.contains(M.getConfig().getString("pricetag")))

				return true;

			String priceLine2 = priceLine.replace(M.getConfig().getString("pricetag"), "").replace(".", "");

			if (!this.isInteger(priceLine2))

				return true;

		}


		return false;


	}

	/**
	 * Checks if the sign in the given SignChangeEvent's price is invalid.
	 * 
	 * @param e Event to use.
	 * @return {@code true} if the price is not in a format such as "$1.99".
	 */
	public boolean signPriceInvalid(SignChangeEvent e) {

		String priceLine = ChatColor.stripColor(e.getLine(3));

		int decimals = 0;

		for (char c : priceLine.toCharArray()) {

			if (c == '-')

				return true;

			if (decimals > 1)

				return true;

			if (c == '.')

				decimals++;

		}

		if (priceLine.equals("") || priceLine.equals(null))

			return true;

		else {

			if (!priceLine.contains(M.getConfig().getString("pricetag")))

				return true;

			String priceLine2 = priceLine.replace(M.getConfig().getString("pricetag"), "").replace(".", "");

			if (!this.isInteger(priceLine2))

				return true;

		}


		return false;


	}

	/**
	 * Marks the given sign to be unusable by giving it a red prefix.
	 * 
	 * @param sign Sign to use.
	 * @return {@code true} if the sign is invalid because the prefix is too long.
	 */
	public boolean markSignInvalid(Sign sign) {

		List<String> cfgPrefixList = this.getCfgPrefixes();

		String signPrefix = Colors.noChatColors(sign.getLine(0));

		for (String s : cfgPrefixList) {

			String cfgPrefix = Colors.noChatColors(s);

			String cfgPrefixCodes = s;

			if (cfgPrefix.equalsIgnoreCase(signPrefix)) {

				if (cfgPrefixCodes.length() > 15) {

					sign.setLine(0, ChatColor.DARK_RED + cfgPrefix);

					return true;

				}

				sign.setLine(0, ChatColor.DARK_RED + cfgPrefix);

				break;

			}

		}

		return false;

	}

	/**
	 * Marks the sign in the given SignChangeEvent to be unusable by giving it a red prefix.
	 * 
	 * @param e Event to use.
	 * @param warnPlayer Whether to tell the player, if the prefix is too long, that the prefix is too long.
	 * @return {@code true} if the sign is invalid because the prefix is too long.
	 */
	public boolean markSignInvalid(SignChangeEvent e, boolean warnPlayer) {

		List<String> cfgPrefixList = this.getCfgPrefixes();

		String signPrefix = Colors.noChatColors(e.getLine(0));

		for (String s : cfgPrefixList) {

			String cfgPrefix = Colors.noChatColors(s);

			String cfgPrefixCodes = s;

			if (cfgPrefix.equalsIgnoreCase(signPrefix)) {

				if (cfgPrefixCodes.length() > 15) {

					e.setLine(0, ChatColor.DARK_RED + cfgPrefix);

					if (warnPlayer)
						e.getPlayer().sendMessage(Msgs.PrefixTooLong());

					return true;

				}

				e.setLine(0, ChatColor.DARK_RED + cfgPrefix);

				break;

			}

		}

		return false;

	}

	/**
	 * Marks the sign in the given SignChangeEvent to be usable by giving it a valid SignTP prefix.
	 * If the prefix is too long (> 15 characters), it will mark the sign as invalid, as if it were being marked by markSignInvalid.
	 * 
	 * @param e Event to use.
	 */
	public void markSignUsable(SignChangeEvent e) {

		List<String> cfgPrefixList = this.getCfgPrefixes();

		String signPrefix = Colors.noChatColors(e.getLine(0));

		for (String s : cfgPrefixList) {

			String cfgPrefix = Colors.noChatColors(s);

			String cfgPrefixColored = Colors.toChatColors(s);

			String cfgPrefixCodes = s;

			if (cfgPrefix.equalsIgnoreCase(signPrefix)) {

				if (cfgPrefixCodes.length() > 15) {

					e.setLine(0, ChatColor.DARK_RED + cfgPrefix);

					e.getPlayer().sendMessage(Msgs.PrefixTooLong());

					break;

				}

				// I worked very hard on this code. And apparently it did nothing all this time.
				// Removed since v1.0.4

				/*
				StringBuffer newPrefix = new StringBuffer(signPrefix);

				char[] lowerCases = new String("abcdefghijklmnopqrstuvwxyz").toCharArray();

				List<Character> lowerCList = new ArrayList<Character>();

				for (char c : lowerCases)

					lowerCList.add(c);

				char[] upperCases = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

				List<Character> upperCList = new ArrayList<Character>();

				for (char c : upperCases)

					upperCList.add(c);

				int index = 0;

				for (char cfgChar : cfgPrefix.toCharArray()) {

					char signChar = newPrefix.charAt(index);

					char lowerSignChar = toLowerChar(newPrefix.charAt(index));

					char upperSignChar = toUpperChar(newPrefix.charAt(index));

					if (lowerCList.contains(cfgChar) && upperCList.contains(signChar))

						newPrefix.setCharAt(index, lowerSignChar);

					else if (upperCList.contains(cfgChar) && lowerCList.contains(signChar))

						newPrefix.setCharAt(index, upperSignChar);

					index++;

				}
				 */

				e.setLine(0, Colors.toChatColors(cfgPrefixColored));

				break;

			}

		}

	}

	/**
	 * Marks the sign price invalid by making it red, as well as marks the entire sign unusable.
	 * 
	 * @param sign Sign to use.
	 */
	public void markPriceInvalid(Sign sign) {

		this.markSignInvalid(sign);

		sign.setLine(3, ChatColor.RED + ChatColor.stripColor(sign.getLine(3)));

		sign.update();

	}

	/**
	 * Marks the sign price invalid by making it red, as well as marks the entire thing unusable.
	 * This uses the markSignInvalid(SignChangeEvent) method, and will not tell the player whether the sign prefix is too long upon creation.
	 * 
	 * @param e SignChangeEvent to use.
	 */
	public void markPriceInvalid(SignChangeEvent e) {

		this.markSignInvalid(e, false);

		e.setLine(3, ChatColor.RED + ChatColor.stripColor(e.getLine(3)));

	}

	/**
	 * Gets the amount of money required to use the given sign, in the format that the server's economy plugin wants it. For example, 1.23
	 * 
	 * @param sign Sign to get the price of.
	 * @return The double value of the amount of money required to use the sign. 0.0 if economy is disabled.
	 */
	public double getPrice(Sign sign) {

		String priceLine = ChatColor.stripColor(sign.getLine(3).replace(M.getConfig().getString("pricetag"), ""));

		if (priceLine.equals(""))

			return 0D;

		if (M.economyIsOn) {

			int digits = Main.economy.fractionalDigits();

			StringBuffer zeroFormat = new StringBuffer();

			if (digits < 0) {

				for (char c : priceLine.toCharArray()) {

					if (c != '.')

						zeroFormat.append("0");

					else

						zeroFormat.append(String.valueOf(c));

				}

			}

			for (int i = 0; i < digits; i++) {

				if (priceLine.charAt(i) != '.')

					zeroFormat.append("0");

				else

					zeroFormat.append(String.valueOf(priceLine.charAt(i)));

			}

			DecimalFormat dm = new DecimalFormat(zeroFormat.toString());

			return Double.valueOf(dm.format(Double.valueOf(priceLine)));

		}

		return 0D;

	}

	/**
	 * Gets the amount of money required to use the given sign, in the format that the server's economy plugin wants it. For example, 1.23
	 * 
	 * @param e Event to use.
	 * @return The double value of the amount of money required to use the sign. 0.0 if economy is disabled.
	 */
	public double getPrice(SignChangeEvent e) {

		String priceLine = ChatColor.stripColor(e.getLine(3).replace(M.getConfig().getString("pricetag"), ""));

		if (priceLine.equals(""))

			return 0D;

		if (M.economyIsOn) {

			int digits = Main.economy.fractionalDigits();

			StringBuffer zeroFormat = new StringBuffer();

			if (digits < 0) {

				for (char c : priceLine.toCharArray()) {

					if (c != '.')

						zeroFormat.append("0");

					else

						zeroFormat.append(String.valueOf(c));

				}

			}

			for (int i = 0; i < digits; i++) {

				if (priceLine.charAt(i) != '.')

					zeroFormat.append("0");

				else

					zeroFormat.append(String.valueOf(priceLine.charAt(i)));

			}

			DecimalFormat dm = new DecimalFormat(zeroFormat.toString());

			return Double.valueOf(dm.format(Double.valueOf(priceLine)));

		}

		return 0D;

	}

	/**
	 * Checks if the given sign has a correctly formatted pricetag, according to the server's economy management plugin.
	 * 
	 * @param sign Sign to check.
	 * @return {@code true} if the sign has a correctly formatted pricetag.
	 */
	/*public boolean hasPriceFormat(Sign sign) {

		if (this.signPriceInvalid(sign))

			return false;

		String priceLine = sign.getLine(3).replace(M.getConfig().getString("pricetag"), "");

		char[] chars = priceLine.toCharArray();

		for (int i = 0; i < chars.length; i++) {

			if (chars[i] != '.')

				chars[i] = '0';

		}

		StringBuffer ecoFormatStr = new StringBuffer(String.valueOf(this.getPrice(sign)));

		for (int i = 0; i < ecoFormatStr.length(); i++) {

			if (ecoFormatStr.charAt(i) != '.')

				ecoFormatStr.setCharAt(i, '0');

		}

		String signFormatStr = String.valueOf(chars);

		DecimalFormat signFormat = new DecimalFormat(signFormatStr);

		DecimalFormat ecoFormat = new DecimalFormat(ecoFormatStr.toString());

		double signPrice = Double.valueOf(priceLine);

		if (signFormat.format(signPrice).equals(ecoFormat.format(signPrice)))

			return true;

		return false;

	}*/

	/**
	 * Gives the sign a price with the same currency format as the server's economy plugin.
	 * 
	 * @param sign Sign to manipulate.
	 * @return Whether the price successfully formatted.
	 */
	public boolean formatPrice(Sign sign) {
		
		String line = ChatColor.stripColor(sign.getLine(3));

		try {

			if (this.getPrice(sign) == 0D) {

				sign.setLine(3, "");

				return true;

			}

			String price = String.valueOf(this.getPrice(sign));

			String stringPrice = line;

			String zeroesPrice = line.replace(M.getConfig().getString("pricetag"), "");

			int decIndex = 0;

			for (char c : zeroesPrice.toCharArray())

				if (c != '.')
					decIndex++;

			boolean allZeroes = true;

			for (char c : zeroesPrice.substring(decIndex).toCharArray())

				if (c != '0')
					allZeroes = false;

			if (allZeroes) {

				if (M.getConfig().getBoolean("trimZeroes"))
					price = zeroesPrice.substring(0, decIndex - 1);

				else
					price = zeroesPrice;

			}

			if (!M.getConfig().getString("pricetag").isEmpty())

				switch (M.getConfig().getString("pricetagPosition")) {

				case "after":
					sign.setLine(3, price + M.getConfig().getString("pricetag"));
					break;

				case "both":
					sign.setLine(3, M.getConfig().getString("pricetag") + price + M.getConfig().getString("pricetag"));
					break;

				case "either":
					int length = stringPrice.length() % 2 == 0 ? stringPrice.length() - 1 : stringPrice.length();
					String firstHalf = stringPrice.substring(0, length / 2);
					String secondHalf = stringPrice.substring(length / 2, stringPrice.length() - 1);

					if (firstHalf.contains(M.getConfig().getString("pricetag")))
						sign.setLine(3, M.getConfig().getString("pricetag") + price);

					else if (secondHalf.contains(M.getConfig().getString("pricetag")))
						sign.setLine(3, price + M.getConfig().getString("pricetag"));

					break;

				default:
				case "before":
					sign.setLine(3, M.getConfig().getString("pricetag") + price);
					break;

				}

			sign.update();
			
			return true;

		} catch (Exception e) { return false; }

	}

	/**
	 * Gives the sign a price with the same currency format as the server's economy plugin.
	 * 
	 * @param e Event to use.
	 * @return Whether the price successfully formatted.
	 */
	public boolean formatPrice(SignChangeEvent e) {

		String line = ChatColor.stripColor(e.getLine(3));

		try {

			if (this.getPrice(e) == 0D) {

				e.setLine(3, "");

				return true;

			}

			String price = String.valueOf(this.getPrice(e));

			String stringPrice = line;

			String zeroesPrice = line.replace(M.getConfig().getString("pricetag"), "");

			int decIndex = 0;

			for (char c : zeroesPrice.toCharArray())

				if (c != '.')
					decIndex++;

			boolean allZeroes = true;

			for (char c : zeroesPrice.substring(decIndex).toCharArray())

				if (c != '0')
					allZeroes = false;

			if (allZeroes) {

				if (M.getConfig().getBoolean("trimZeroes"))
					price = zeroesPrice.substring(0, decIndex - 1);

				else
					price = zeroesPrice;

			}

			if (!M.getConfig().getString("pricetag").isEmpty())

				switch (M.getConfig().getString("pricetagPosition")) {

				case "after":
					e.setLine(3, price + M.getConfig().getString("pricetag"));
					break;

				case "both":
					e.setLine(3, M.getConfig().getString("pricetag") + price + M.getConfig().getString("pricetag"));
					break;

				case "either":
					int length = stringPrice.length() % 2 == 0 ? stringPrice.length() - 1 : stringPrice.length();
					String firstHalf = stringPrice.substring(0, length / 2);
					String secondHalf = stringPrice.substring(length / 2, stringPrice.length() - 1);

					if (firstHalf.contains(M.getConfig().getString("pricetag")))
						e.setLine(3, M.getConfig().getString("pricetag") + price);

					else if (secondHalf.contains(M.getConfig().getString("pricetag")))
						e.setLine(3, price + M.getConfig().getString("pricetag"));

					break;

				default:
				case "before":
					e.setLine(3, M.getConfig().getString("pricetag") + price);
					break;

				}
			
			return true;

		} catch (Exception ex) { return false; }
		
	}

}
