package io.github.theepicblock.browny.storage.datatypes;

/**
 * Stores information about a world
 * @author TheEpicBlock_TEB
 */
public class World {
	private String Name;
	
	public World (String Name) {
		this.Name = Name;
	}
	
	@Override
	public String toString() {
		return Name;
	}
}
