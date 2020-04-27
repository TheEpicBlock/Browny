package io.github.theepicblock.browny.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.theepicblock.browny.BrownyMain;
import io.github.theepicblock.browny.database.Database;
import io.github.theepicblock.browny.database.TownyFlatfile;
import io.github.theepicblock.browny.storage.datatypes.Town;

/**
 * manages the reading and interpretation of the Browny config file
 * @author TheEpicBlock_TEB
 */
public class BrownyConfig {
	JavaPlugin browny;
	FileConfiguration config;
	
	//Debug settings
	public boolean DebugLogsEnabled;
	public boolean SpamLogsEnabled;
	
	//town levels
	public List<TownLevel> townLevels;
	
	//default towns
	public Town.PlotPrices defaultPlotPrices;
	
	
	public BrownyConfig(JavaPlugin browny) {
		this.browny = browny;
		browny.saveDefaultConfig();
		browny.reloadConfig();
		config = browny.getConfig();
		
		//load debug settings /////////////////////////////
		DebugLogsEnabled = config.getBoolean("DebugLogsEnabled");
		SpamLogsEnabled = config.getBoolean("SpamLogsEnabled");
		
		//load town levels ////////////////////////////////
		List<Map<?, ?>> levels = config.getMapList("levels.townLevels"); //gets all the arguments as a list of maps			
		
		townLevels = new ArrayList<TownLevel>(); //get a new array to populate
		
		levels.forEach((level) -> {
			try {
				int minimumNumberOfResidents 	= (Integer)level.get("minNumResidents");
				String namePostfix 				= (String)level.get("namePostfix");
				String nameSuffix 				= (String)level.get("nameSuffix");
				String mayorPrefix 				= (String)level.get("mayorPrefix");
				String mayorSuffix 				= (String)level.get("mayorSuffix");
				
				int townBlockLimit 				= (Integer)level.get("townBlockLimit");
				double upkeepModifier 			= (double)level.get("upkeepModifier");
				int townOutpostLimit 			= (Integer)level.get("townOutpostLimit");
				int townBonusLimit 				= (Integer)level.get("townBonusLimit");
				
				TownLevel v = new TownLevel(minimumNumberOfResidents, namePostfix, nameSuffix, mayorPrefix, mayorSuffix, townBlockLimit, upkeepModifier, townOutpostLimit, townBonusLimit);
				townLevels.add(v);
			} catch (NullPointerException e) {
				BrownyMain.logError("Invalid town level detected, it will be skipped"); // TODO more helpful debug message, showing what is wrong. Maybe making TownLevel serializable https://bukkit.gamepedia.com/Configuration_API_Reference
				BrownyMain.logError(e.getMessage());
			} catch (ClassCastException e) {
				BrownyMain.logError("Invalid town level detected, a value is of the wrong type, it will be skipped");
				BrownyMain.logError(e.getMessage());
			}
		});
		
		//load default plot prices for towns
		ConfigurationSection pricesSection = config.getConfigurationSection("townDefault.defaultPrices");
		
		Double plotPrice = 				pricesSection.getDouble("plotPrice");
		Double plotTax = 				pricesSection.getDouble("plotTax");
		Double commercialPlotPrice = 	pricesSection.getDouble("commercialPlotPrice");		
		Double commercialPlotTax = 		pricesSection.getDouble("commercialPlotTax");		
		Double embassyPlotPrice = 		pricesSection.getDouble("embassyPlotPrice");		
		Double embassyPlotTax = 		pricesSection.getDouble("embassyPlotTax");
		
		defaultPlotPrices = new Town.PlotPrices(plotPrice, plotTax, commercialPlotPrice, commercialPlotTax, embassyPlotPrice, embassyPlotTax);
	}
	
	/**
	 * 
	 * @return a database of the type that was defined by the config
	 */
	public Database getDatabase() {
		switch (config.getString("database.type")) {
		case "TownyFlatfile":
			return new TownyFlatfile(getTownyPath(),this);
		default:
			BrownyMain.logError("Invalid database type in config: '"+config.getString("database.type")+"'");
			BrownyMain.logError("Defaulting to TownyFlatfile, please edit the config file");
			return new TownyFlatfile(getTownyPath(),this);
		}
	}
	
	/**
	 * Get's the TownLevel corresponding to the amount of residents in a town
	 * @param res amount of residents of the town
	 * @return the TownLevel
	 */
	public TownLevel getLevelByNumberOfResidents(int res) {
		for (int i = townLevels.size(); i > 0; i--) {
			if (townLevels.get(i).getMinimumNumberOfResidents() <= res) {
				return townLevels.get(i);
			}
		}
		return null; //something needs to go very wrong for this to happen
	}
	
	private String getTownyPath() {
		return browny.getDataFolder().getParentFile() + File.separator + "Towny" + File.separator + "data"; //gets the Towny/data directory
	}
}
