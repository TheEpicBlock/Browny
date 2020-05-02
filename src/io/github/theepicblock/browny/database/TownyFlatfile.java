package io.github.theepicblock.browny.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import io.github.theepicblock.browny.BrownyMain;
import io.github.theepicblock.browny.storage.datatypes.Plot;
import io.github.theepicblock.browny.storage.datatypes.Town;
import io.github.theepicblock.browny.storage.datatypes.World;
import io.github.theepicblock.browny.util.DataUtil;
import io.github.theepicblock.browny.util.FixInfo;

/**
 * A database type that reads and writes to a Towny Flatfile.
 * @author TheEpicBlock_TEB
 *
 */
public class TownyFlatfile extends Database {
	private String path;
	final String FS = File.separator;
	
	public TownyFlatfile(String path, FixInfo fixInfo, int plotSize) {
		super(fixInfo, plotSize);
		this.path = path;
		
		BrownyMain.logGeneral("Initializing FlatFile DataBase at path " + path);
	}
	
	
	//database overridden functions, please make sure this is in the same order as in the Database class ////////////
	@Override
	public Boolean isTownyCompat() {
		return true;
	}

	@Override
	public Plot getPlot(int x, int y, World world) {
		// TODO check if towny uses XY or YX
		File plotFile = new File(path, "townblocks"+FS+world+FS+  x+"_"+y+"_"+this.plotSize+".data"); //yes, towny appends the plotsize to the filename of every plot
		if (!plotFile.exists()) {
			return null; //there is no plot info here			
		}
		
		HashMap<String,String> keys = DataUtil.getKeyPairFromFile(plotFile);

		String name = keys.get("name");
		double price = DataUtil.parseAsDoubleOrNaN(keys.get("price"));
		String townName = keys.get("town");
		
		String townUUID = this.getTownByName(townName).getUUID();
		
		return new Plot(name, price, townUUID);
	}

	@Override
	public List<World> getWorlds() {
		File worldFile = getOrCreateFile("worlds.txt");
		List<World> returnValue = new ArrayList<World>();
		
		
		getLinesOfFile(worldFile).forEach((string) -> {
			returnValue.add(new World(string));
		});
		
		return returnValue;
	}
	
	@Override
	public Town getTownByName(String name) {
		File townFile = new File(path, "towns"+FS+name+".txt");
		if (!townFile.exists()) {
			return null;			
		}
		
		HashMap<String,String> keys = DataUtil.getKeyPairFromFile(townFile);
		
		String townUUID = keys.get("UUID");
		String townName = keys.get("name");
		
		//parsing plot prices
		Town.PlotPrices plotPrices = null;		
		
		Double plotPrice = 				DataUtil.parseAsDoubleOrNaN(keys.get("plotPrice"));
		Double plotTax = 				DataUtil.parseAsDoubleOrNaN(keys.get("plotTax"));
		Double commercialPlotPrice = 	DataUtil.parseAsDoubleOrNaN(keys.get("commercialPlotPrice"));		
		Double commercialPlotTax = 		DataUtil.parseAsDoubleOrNaN(keys.get("commercialPlotTax"));		
		Double embassyPlotPrice = 		DataUtil.parseAsDoubleOrNaN(keys.get("embassyPlotPrice"));		
		Double embassyPlotTax = 		DataUtil.parseAsDoubleOrNaN(keys.get("embassyPlotTax"));		
		
		plotPrices = new Town.PlotPrices(plotPrice, plotTax, commercialPlotPrice, commercialPlotTax, embassyPlotPrice, embassyPlotTax);
	
		//parse taxes
		double tax = DataUtil.parseAsDoubleOrNaN(keys.get("taxes"));
		
		Town town =  new Town(townUUID, townName, tax, plotPrices);
		town.TryFix(fixInfo); //just in case any fields are missing
		return town;
	}
	
	public Town getTownByUUID(String uuid) {
		Iterator<Town> iterator = this.getTownIterator();
		
		while (iterator.hasNext()) {
			Town town = iterator.next();
			if (town.getUUID() == uuid) {
				return town;
			}
		}
		
		return null; //no town found with that uuid
	}
	
	public Iterator<Town> getTownIterator() {
		return new FlatfileTownIterator(this);
	}
	
	/**
	 * An iterator that iterates over files.
	 * Caches the town names but loads them only when needed.
	 * @author TheEpicBlock_TEB
	 */
	public class FlatfileTownIterator implements Iterator<Town>{
		Database db;
		List<String> townNames;
		int index;
		int listSize;
		
		public FlatfileTownIterator(Database db) {
			this.db = db;
			this.townNames = db.getAllTownNames();
			this.index = 0;
			this.listSize = townNames.size();
		}

		@Override
		public boolean hasNext() {
			return index + 1 >= listSize; //not tested yet, I hope this is correct
		}

		@Override
		public Town next() {
			String townName = townNames.get(index);
			index++;
			return db.getTownByName(townName);
		}
	}

	@Override
	public void saveTown(Town town) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<String> getAllTownNames() {
		List<String> returnV = new ArrayList<String>();
		
		return getLinesOfFile(getOrCreateFile("towns.txt"));
	}
	
	//////////// Helper functions //////////////////
	private File getOrCreateFile(String LocalPath) {
		File file = new File(path, LocalPath);
		if (!file.exists()) {
			BrownyMain.logSpam("Creating File: "+LocalPath);
			Boolean couldCreateDirs = file.getParentFile().mkdirs();
			if (!couldCreateDirs) {
				BrownyMain.logError("Couldn't create all directories for '" + LocalPath + "'");
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				BrownyMain.logError("Error whilst creating '" + LocalPath + "' More info:");
				BrownyMain.logError(e);
			}
		}
		
		return file;
	}
	
	private List<String> getLinesOfFile(File file) {
		List<String> returnValue = new ArrayList<String>();
		
		try (Stream<String> lines = Files.lines(file.toPath())) {
			lines.forEach((line)-> {
				if (line != "" && line != "\r") {
					returnValue.add(line);					
				}
			});
		} catch (IOException e) {
			BrownyMain.logError("Error whilst reading 'data/worlds.txt' More info:");
			BrownyMain.logError(e);
		}
		
		return returnValue;
	}
}
