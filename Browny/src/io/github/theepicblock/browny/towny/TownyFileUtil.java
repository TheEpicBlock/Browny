package io.github.theepicblock.browny.towny;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Stream;

import io.github.theepicblock.browny.BrownyMain;
import io.github.theepicblock.browny.storage.datatypes.World;

public class TownyFileUtil {
	
	/**
	 * Converts a file into a hashmap.
	 * @param file file to convert
	 * @return the converted hashmap
	 * 
	 * @author TownyAdvanced (LlmDl)
	 */
	public static HashMap<String,String> getKeyPairFromFile(File file) {
		HashMap<String,String> keyPair = new HashMap<String,String>();
		
		try (FileInputStream stream = new FileInputStream(file)){
			InputStreamReader streamReader = new InputStreamReader(stream,StandardCharsets.UTF_8);
			Properties props = new Properties();
			props.load(streamReader);
			for (String key : props.stringPropertyNames()) {
				String value = props.getProperty(key);
				keyPair.put(key, String.valueOf(value));
			}
		} catch (Exception e) {
			BrownyMain.logError("Error whilst reading '"+file.getAbsolutePath()+"' More info:");
			BrownyMain.logError(e);
			return null;
		}
		
		
		return keyPair;
	}
	
	public static void saveKeyPairToFile(HashMap<String,String> keyPair, File file) {
		Properties props = new Properties();
		keyPair.forEach((Key,Object) -> {
			props.put(Key, Object);
		});
		
		try {
			FileOutputStream stream = new FileOutputStream(file);
			props.store(new OutputStreamWriter(null, StandardCharsets.UTF_8), "");
		} catch (Exception e) {
			BrownyMain.logError("Error whilst writing to '"+file.getAbsolutePath()+"'");
			BrownyMain.logError("It could be that data was lost; More info:");
			BrownyMain.logError(e);
		}
		
	}
}
