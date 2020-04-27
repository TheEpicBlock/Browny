package io.github.theepicblock.browny.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import io.github.theepicblock.browny.BrownyMain;
import io.github.theepicblock.browny.storage.datatypes.Plot;
import io.github.theepicblock.browny.storage.datatypes.Town;
import io.github.theepicblock.browny.storage.datatypes.World;
import io.github.theepicblock.browny.towny.TownyFileUtil;

/**
 * A database type that reads and writes to a Towny Flatfile.
 * @author TheEpicBlock_TEB
 *
 */
public class TownyFlatfile extends Database {
	private String path;
	final String FS = File.separator;
	
	public TownyFlatfile(String path) {
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
		
		return null;
	}

	@Override
	public List<World> getWorlds() {
		File worldFile = getOrCreateFile("data"+FS+"worlds.txt");
		List<World> returnValue = new ArrayList<World>();
		
		
		getLinesOfFile(worldFile).forEach((string) -> {
			returnValue.add(new World(string));
		});
		
		return returnValue;
	}
	
	@Override
	public Town getTown(String name) {
		File townFile = getOrCreateFile("towns"+FS+name+".txt");
		if (!townFile.exists()) {
			return null;			
		}
		
		HashMap<String,String> keys = TownyFileUtil.getKeyPairFromFile(townFile);
		
		String townUUID = keys.get("UUID");
		String townName = keys.get("name");
		
		Town town =  new Town(townUUID,townName);
		town.TryFix(); //just in case any fields are missing
		return town;
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
	
	//Helper functions //////////////////
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
