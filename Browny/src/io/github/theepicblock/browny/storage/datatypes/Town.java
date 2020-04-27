package io.github.theepicblock.browny.storage.datatypes;

import java.util.UUID;

import io.github.theepicblock.browny.BrownyMain;
import io.github.theepicblock.browny.config.TownLevel;
import io.github.theepicblock.browny.database.Database;
import io.github.theepicblock.browny.storage.interfaces.DatabaseSaveable;
import io.github.theepicblock.browny.storage.interfaces.Fixable;

public class Town implements DatabaseSaveable, Fixable{
	public Boolean isDirty;
	private String UUID;
	private String name;
	
	public Town(String UUID, String name) {
		this.UUID = UUID;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.isDirty = true;
		this.name = name;
	}

	@Override
	public void Save(Database db) {
		db.saveTown(this);
	}

	public String getUUID() {
		return UUID;
	}
	
	/**
	 * @return the amount of residents this town has
	 */
	public int getAmountOfResidents() {
		// TODO implement
		return 0;
	}
	
	/**
	 * @return the level that this town has
	 */
	public TownLevel getLevel() {
		return BrownyMain.getBrownyConfig().getLevelByNumberOfResidents(this.getAmountOfResidents());
	}

	@Override
	public void TryFix() {
		if (UUID == null) {
			UUID = java.util.UUID.randomUUID().toString();
			isDirty = true;
		}
		
		if (name == null) {
			name = "MissingName" + UUID;
		}
	}
}
