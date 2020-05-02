package io.github.theepicblock.browny;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.theepicblock.browny.config.BrownyConfig;
import io.github.theepicblock.browny.database.Database;
import io.github.theepicblock.browny.storage.CacheManager;

/**
 * Main Browny class. Has static references to a few things
 * @author TheEpicBlock_TEB
 *
 */
public class BrownyMain extends JavaPlugin {
	private static Database mainDB;
	private static CacheManager cache;
	private static BrownyConfig brownyConfig;
	private static Server server;
	
	@Override
    public void onEnable() {
		server = getServer();
		brownyConfig = new BrownyConfig(this);
		mainDB = brownyConfig.getDatabase();
		cache = new CacheManager(mainDB);
	}
	
	//log helper functions
	public static void logGeneral(Object msg) {
		System.out.println("[Browny]: " + msg.toString());
	}

	public static void logError(Object msg) {
		server.getConsoleSender().sendMessage(ChatColor.RED + msg.toString());
	}
	
	public static void logDebug(Object msg) {
		if (brownyConfig.DebugLogsEnabled) {
			System.out.println("[Browny Debug]: " + msg.toString());			
		}
	}
	
	public static void logSpam(Object msg) {
		if (brownyConfig.SpamLogsEnabled) {
			System.out.println("[Browny Spam]: " + msg.toString());			
		}
	}

	//getters
	public static Database getMainDB() {
		return mainDB;
	}

	/**
	 * Gets the Cache manager. Which stores the cache and manages what should be in it.
	 * @return
	 */
	public static CacheManager getCache() {
		return cache;
	}

	public static BrownyConfig getBrownyConfig() {
		return brownyConfig;
	}	
}
