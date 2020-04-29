package io.github.theepicblock.browny.config;

/**
 * Every instance defines a level of town
 * Each level has different stats, which level a town has is determined by the amount of residents
 * @author TheEpicBlock_TEB
 */
public class TownLevel{
	int minimumNumberOfResidents;
	String namePostfix;
	String nameSuffix;
	String mayorPrefix;
	String mayorSuffix;
	
	int townBlockLimit;
	double upkeepModifier;
	int townOutpostLimit;
	int townBonusLimit;
	
	
	public TownLevel(int minimumNumberOfResidents, String namePostfix, String nameSuffix, String mayorPrefix,
			String mayorSuffix, int townBlockLimit, double upkeepModifier, int townOutpostLimit, int townBonusLimit) {
		
		this.minimumNumberOfResidents = minimumNumberOfResidents;
		this.namePostfix = namePostfix;
		this.nameSuffix = nameSuffix;
		this.mayorPrefix = mayorPrefix;
		this.mayorSuffix = mayorSuffix;
		this.townBlockLimit = townBlockLimit;
		this.upkeepModifier = upkeepModifier;
		this.townOutpostLimit = townOutpostLimit;
		this.townBonusLimit = townBonusLimit;
	}

	@Override
	public String toString() {
		return "TownLevel [" + minimumNumberOfResidents + " residents, '" + nameSuffix + "']";
	}
	
	//getters
	public int getMinimumNumberOfResidents() {
		return minimumNumberOfResidents;
	}

	public String getNamePostfix() {
		return namePostfix;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public String getMayorPrefix() {
		return mayorPrefix;
	}

	public String getMayorSuffix() {
		return mayorSuffix;
	}

	public int getTownBlockLimit() {
		return townBlockLimit;
	}

	public double getUpkeepModifier() {
		return upkeepModifier;
	}

	public int getTownOutpostLimit() {
		return townOutpostLimit;
	}
	
	
}
