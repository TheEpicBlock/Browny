package io.github.theepicblock.browny.storage;

import java.util.List;

import io.github.theepicblock.browny.BrownyMain;
import io.github.theepicblock.browny.database.Database;
import io.github.theepicblock.browny.storage.datatypes.Plot;
import io.github.theepicblock.browny.storage.datatypes.Town;
import io.github.theepicblock.browny.storage.datatypes.World;

/**
 * Manages which things are loaded when. When you want to get something, like a town or plot, you should get it from this class.
 * @author TheEpicBlock_TEB
 *
 */
public class CacheManager {
	private Database database;
	
	public List<World> Worlds;
	private List<Town> Towns;
	private List<Plot> Plots;
	
	public CacheManager(Database database) {
		this.database = database;
		loadBase();
	}
	
	/**
	 * Loads essential things which need to be in the cache
	 */
	public void loadBase() {
		Worlds = database.getWorlds();
			if (Worlds.size() > 0) {
				BrownyMain.logDebug("detected "+Worlds.size()+" worlds");
				BrownyMain.logSpam("Worlds detected: "+Worlds);
			} else {
				BrownyMain.logError("No worlds detected!! Please add worlds if you want Browny to actually do something");
			}
	}
}
