package io.github.theepicblock.browny.database;

import java.util.List;

import io.github.theepicblock.browny.storage.datatypes.Plot;
import io.github.theepicblock.browny.storage.datatypes.Town;
import io.github.theepicblock.browny.storage.datatypes.World;
import io.github.theepicblock.browny.util.FixInfo;

/**
 * Class for managing the reading and writing of objects to a file/other storage location.
 * Should be extended to accommodate for different types of databases.
 * @author TheEpicBlock_TEB
 *
 */
public abstract class Database {
	/**
	 * Config used to fix any missing values
	 */
	FixInfo fixInfo;
	int plotSize;
	
	/**
	 * @param config config used to fix any missing values.
	 * @param plotSize the size of the plots in this database
	 */
	Database(FixInfo fixInfo, int plotSize) {
		this.fixInfo = fixInfo;
		this.plotSize = plotSize;
	}
	
	/**
	 * Specifies if this Database is Towny compatible.
	 * Towny compatible Databases may store thing differently.
	 * @return
	 */
	public abstract Boolean isTownyCompat();
	
	/**
	 * Gets a plot at the specified coordinates. Will return null if no plot info is present.
	 * @param x
	 * @param y
	 * @param world the world to look in
	 * @return the plot at the given plot coordinates or null
	 */
	public abstract Plot getPlot(int x, int y, World world);
	
	/**
	 * Gets a town by it's name.
	 * It is preferred to use UUID's, unless when working with Towny formats.
	 * @param name
	 * @return the town found
	 */
	public abstract Town getTown(String name);
	
	/**
	 * Saves a town to disk
	 * @param town the town to save
	 */
	public abstract void saveTown(Town town);
	
	/**
	 * @return the names of all towns in the database
	 */
	public abstract List<String> getAllTownNames();
	
	/**
	 * @return the list of all worlds defined in the Database
	 */
	public abstract List<World> getWorlds();
}
