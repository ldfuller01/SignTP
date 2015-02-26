package me.pookeythekid.SignTP.api;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.Messages.Msgs;

import org.bukkit.Bukkit;
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
	 * Makes a lower-cased character advance to upper case. Doesn't really have much to do with in-game signs.
	 * 
	 * @param letter - Letter to modify.
	 * @return A character with a char value that is 32 lower than {@code letter}'s char value.
	 */
	private char toUpperChar(char letter) {

		return (char) ((int) letter - 32);

	}

	/**
	 * Makes an upper-cased character decline to lower case. Doesn't really have much to do with in-game signs.
	 * 
	 * @param letter - Letter to modify.
	 * @return A character with a char value that is 32 higher than {@code letter}'s char value.
	 */
	private char toLowerChar(char letter) {

		return (char) ((int) letter + 32);

	}

	/**
	 * Checks if the provided string is an integer.
	 * 
	 * @param str - String to check.
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
	 * @param sign - Sign to get prefix from.
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
	 * @param sign - Sign to check prefix of.
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
	 * @param e - Event to use.
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
	 * Checks if the given sign has a price on the fourth line.
	 * 
	 * @param sign - Sign whose price to check.
	 * @return {@code true} if the fourth line both is prefixed with a dollar sign and has content after the dollar sign.
	 */
	public boolean signHasPrice(Sign sign) {

		return sign.getLine(3) != null  &&  sign.getLine(3) != ""  &&  !this.signPriceInvalid(sign);

	}

	/**
	 * Checks if the sign in the given SignChangeEvent has a price on the fourth line.
	 * 
	 * @param e - Event to use.
	 * @return {@code true} if the fourth line both is prefixed with a dollar sign and has content after the dollar sign.
	 */
	public boolean signHasPrice(SignChangeEvent e) {

		return e.getLine(3) != null  &&  e.getLine(3) != ""  &&  !this.signPriceInvalid(e);

	}

	/**
	 * Checks if the given sign meets the minimum requirements of a SignTP sign.
	 * 
	 * @param sign - Sign to check validity of.
	 * @return {@code true} if it has a valid SignTP prefix and addresses a valid warp name.
	 */
	public boolean signIsValid(Sign sign) {

		return signHasPrefix(sign) &&  M.warpsList.contains(sign.getLine(1).toLowerCase());

	}

	/**
	 * Checks if the sign in the given SignChangeEvent meets the minimum requirements of a SignTP sign.
	 * 
	 * @param e - Event to use.
	 * @return {@code true} if it has a valid SignTP prefix and addresses a valid warp name.
	 */
	public boolean signIsValid(SignChangeEvent e) {

		return signHasPrefix(e) && M.warpsList.contains(e.getLine(1).toLowerCase());

	}

	/**
	 * Checks if the given sign's price tag is invalid.
	 * 
	 * @param sign - Sign to check the pricetag of.
	 * @return {@code true} if the price is not in a format such as "$1.99".
	 */
	public boolean signPriceInvalid(Sign sign) {

		String priceLine = sign.getLine(3);

		int decimals = 0;

		for (char c : priceLine.toCharArray()) {

			if (c == '-')

				return true;

			if (decimals > 1)

				return true;

			if (c == '.')

				decimals++;

		}

		if (!priceLine.startsWith("$") || priceLine.endsWith("$"))

			return true;

		if (!this.isInteger(String.valueOf(priceLine.replace("$", "").replace(".", ""))))

			return true;


		return false;


	}

	/**
	 * Checks if the sign in the given SignChangeEvent's price is invalid.
	 * 
	 * @param e - Event to use.
	 * @return {@code true} if the price is not in a format such as "$1.99".
	 */
	public boolean signPriceInvalid(SignChangeEvent e) {

		String priceLine = e.getLine(3);

		int decimals = 0;

		for (char c : priceLine.toCharArray()) {

			if (c == '-')

				return true;

			if (decimals > 1)

				return true;

			if (c == '.')

				decimals++;

		}

		if (!priceLine.equals("")) {

			if (!priceLine.startsWith("$") || priceLine.endsWith("$")) {

				return true;

			}

			String priceLine2 = priceLine.replace("$", "").replace(".", "");

			if (!this.isInteger(priceLine2)) {

				return true;

			}

		}


		return false;


	}

	/**
	 * Marks the sign in the given SignChangeEvent to be unusable by giving it a red prefix.
	 * 
	 * @param e - Event to use.
	 * @return {@code true} if the sign is invalid because the prefix is too long.
	 */
	public boolean markSignInvalid(SignChangeEvent e) {

		List<String> cfgPrefixList = this.getCfgPrefixes();

		String signPrefix = Colors.noChatColors(e.getLine(0));

		for (String s : cfgPrefixList) {

			String cfgPrefix = Colors.noChatColors(s);

			String cfgPrefixCodes = s;

			if (cfgPrefix.equalsIgnoreCase(signPrefix)) {

				if (cfgPrefixCodes.length() > 15) {

					e.setLine(0, ChatColor.DARK_RED + cfgPrefix);

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
	 * @param e - Event to use.
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

				StringBuffer newPrefix = new StringBuffer(signPrefix);

				char[] lowerCases = new String("abcdefghijklmnopqrstuvwxyz").toCharArray();

				/**
				 * Simply contains lower-case letters, so I can check if a char is lower-cased.
				 */
				List<Character> lowerCList = new ArrayList<Character>();

				for (char c : lowerCases) {

					lowerCList.add(c);

				}

				char[] upperCases = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

				/**
				 * Simply contains upper-case letters, so I can check if a char is upper case.
				 */
				List<Character> upperCList = new ArrayList<Character>();

				for (char c : upperCases) {

					upperCList.add(c);

				}

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

				e.setLine(0, Colors.toChatColors(cfgPrefixColored));

				break;

			}

		}

	}

	/**
	 * Gets the amount of money required to use the given sign.
	 * 
	 * @param sign - Sign to get the price of.
	 * @return The double value of the amount of money required to use the sign.
	 */
	public double getPrice(Sign sign) {

		int digits = Main.economy.fractionalDigits();

		String priceLine = sign.getLine(3).replace("$", "");

		StringBuffer zeroFormat = new StringBuffer();

		for (int i = 0; i < digits; i++) {

			if (digits == 0)

				break;

			if (digits < 0) {

				for (char c : priceLine.toCharArray()) {

					if (c != '.')

						zeroFormat.append("0");

					else

						zeroFormat.append(String.valueOf(c));

				}

				break;

			}

			if (priceLine.charAt(i) != '.')

				zeroFormat.append("0");

			else

				zeroFormat.append(String.valueOf(priceLine.charAt(i)));

		}

		DecimalFormat dm = new DecimalFormat(zeroFormat.toString());

		if (priceLine.equals(""))

			return 0D;

		return Double.valueOf(dm.format(Double.valueOf(priceLine)));

	}

	/**
	 * Gets the amount of money required to use the given sign.
	 * 
	 * @param e - Event to use.
	 * @return The double value of the amount of money required to use the sign.
	 */
	public double getPrice(SignChangeEvent e) {

		int digits = Main.economy.fractionalDigits();

		String priceLine = e.getLine(3).replace("$", "");

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

			Bukkit.broadcastMessage("This is a test");

			Bukkit.broadcastMessage(String.valueOf(priceLine.charAt(i)));

			if (priceLine.charAt(i) != '.')

				zeroFormat.append("0");

			else

				zeroFormat.append(String.valueOf(priceLine.charAt(i)));

		}

		DecimalFormat dm = new DecimalFormat(zeroFormat.toString());

		if (priceLine.equals(""))

			return 0D;

		return Double.valueOf(dm.format(Double.valueOf(priceLine)));

	}

	/**
	 * Checks if the given sign has a correctly formatted pricetag, according to the server's economy management plugin.
	 * 
	 * @param sign - Sign to check.
	 * @return {@code true} if the sign has a correctly formatted pricetag.
	 */
	public boolean hasPriceFormat(Sign sign) {

		if (this.signPriceInvalid(sign))

			return false;

		String priceLine = sign.getLine(3).replace("$", "");

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

	}

	/**
	 * Gives the sign a price with the same currency format as the server's economy plugin.
	 * 
	 * @param sign - Sign to manipulate.
	 */
	public void formatPrice(Sign sign) {

		if (this.getPrice(sign) == 0D) {

			sign.setLine(3, "");

			return;

		}

		String stringPrice = String.valueOf(this.getPrice(sign));

		if (stringPrice.endsWith(".0")) {

			String newPrice = stringPrice.replace(".0", "");

			sign.setLine(3, "$" + newPrice);

			return;

		}

		sign.setLine(3, "$" + stringPrice);

	}

	/**
	 * Gives the sign a price with the same currency format as the server's economy plugin.
	 * 
	 * @param e - Event to use.
	 */
	public void formatPrice(SignChangeEvent e) {

		if (this.getPrice(e) == 0D) {

			e.setLine(3, "");

			return;

		}

		String stringPrice = String.valueOf(this.getPrice(e));

		if (stringPrice.endsWith(".0")) {

			String newPrice = stringPrice.replace(".0", "");

			e.setLine(3, "$" + newPrice);

			return;

		}

		e.setLine(3, "$" + stringPrice);

	}

}
