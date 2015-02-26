package me.pookeythekid.SignTP.api;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

import me.pookeythekid.SignTP.Main;

public class ReloadWarps {

	public Main M;

	public ReloadWarps(Main plugin) {

		M = plugin;

	}
	
	
	/**
	 * Primary warp loading method.
	 */
	public void reloadWarps() {
		
		M.warpsList.clear();
		
		File dir = new File(M.getDataFolder(), "warps");
		
		if (!dir.exists())
			
			dir.mkdir();
		
		for (File file : dir.listFiles()) {
			
			M.warpsList.add(file.getName().replace(".txt", ""));
			
		}
		
	}


	/**
	 * Used to be named reloadWarps().
	 * Loads warps out of the warps.txt file. No longer used as primary warp loading method.
	 * Used in the process of auto-updating out of v1.0.1.
	 */
	public void reloadOldWarps() {

		try {

			File warpsFile = new File(M.getDataFolder(), "warps.txt");

			if (warpsFile.exists()) {

				LineNumberReader reader = new LineNumberReader(new FileReader(warpsFile));

				reader.setLineNumber(1);

				String[] warpNames = reader.readLine().replaceFirst("warpnames: ", "").split(",");

				M.warpMap.clear();

				M.warpsList.clear();

				for (String s : warpNames) {

					M.warpMap.put(s.toLowerCase(), null);

					M.warpsList.add(s.toLowerCase());

				}

				boolean reading = false;

				String name = "";

				StringBuffer location = new StringBuffer();
				
				reader.setLineNumber(1);

				while (true) {

					String line = reader.readLine();

					if (line == null)
						break;

					if (line.startsWith("Warp ") && M.warpsList.contains(line.replaceFirst("Warp ", "").toLowerCase()))
						reading = true;

					else if (line.startsWith("End Warp"))
						reading = false;

					if (reading) {

						if (line.startsWith("Warp "))
							name = line.replaceFirst("Warp ", "");

						else {

							if (!line.startsWith("World:"))
								location.append(line + " - ");

							else
								location.append(line);

						}

						M.warpMap.put(name.toLowerCase(), location.toString().toLowerCase());

					} else
						
						location.setLength(0);


				}

				reader.close();

			}

		} catch (Exception e) { e.printStackTrace(); }

	}

}
