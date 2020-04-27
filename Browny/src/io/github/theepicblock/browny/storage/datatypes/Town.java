package io.github.theepicblock.browny.storage.datatypes;

import io.github.theepicblock.browny.BrownyMain;
import io.github.theepicblock.browny.config.BrownyConfig;
import io.github.theepicblock.browny.config.TownLevel;
import io.github.theepicblock.browny.database.Database;
import io.github.theepicblock.browny.storage.interfaces.CanBeDirty;
import io.github.theepicblock.browny.storage.interfaces.DatabaseSaveable;
import io.github.theepicblock.browny.storage.interfaces.Fixable;

public class Town implements DatabaseSaveable, Fixable, CanBeDirty{
	public Boolean dirty;
	private String UUID;
	private String name;
	private double taxes;
	private PlotPrices plotPrices;

	public Town(String UUID, String name, double taxes, PlotPrices data) {
		this.UUID = UUID;
		this.name = name;
		this.taxes = taxes;
		this.plotPrices = data;
	}
	
	@Override
	public void Save(Database db) {
		db.saveTown(this);
	}

	//Helper methods to get some values quickly
	/**
	 * @return the level that this town has
	 */
	public TownLevel getLevel() {
		return BrownyMain.getBrownyConfig().getLevelByNumberOfResidents(this.getAmountOfResidents());
	}
	
	/**
	 * @return the amount of residents this town has
	 */
	public int getAmountOfResidents() {
		// TODO implement
		return 0;
	}
	
	//Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.dirty = true;
		this.name = name;
	}
	
	public PlotPrices getPlotPrices() {
		return plotPrices;
	}

	public String getUUID() {
		return UUID;
	}
	
	@Override
	public void TryFix(BrownyConfig config) {
		if (UUID == null) {
			UUID = java.util.UUID.randomUUID().toString();
			dirty = true;
		}
		
		if (name == null) {
			name = "MissingName" + UUID;
			dirty = true;
		}
		
		if (plotPrices == null) {
			plotPrices = new PlotPrices(Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		}
		
	}
	
	@Override
	public Boolean isDirty() {
		return dirty || this.plotPrices.dirty;
	}

	@Override
	public void markDirty(Boolean v) {
		this.dirty = v;
		this.plotPrices.dirty = v;
	}
	
	/**
	 * Contains the prices for various types of Plots.
	 * Any NaN values will be replaced by default values when TryFix is called.
	 * @author TheEpicBlock_TEB
	 */
	public static class PlotPrices implements Fixable{
		private double plotPrice;
		private double plotTax;
		private double commercialPlotPrice;
		private double commercialPlotTax;
		private double embassyPlotPrice;
		private double embassyPlotTax;
		
		public Boolean dirty;
		
		public PlotPrices(double plotPrice, double plotTax, double commercialPlotPrice, double commercialPlotTax,
				double embassyPlotPrice, double embassyPlotTax) {
			
			this.plotPrice = plotPrice;
			this.plotTax = plotTax;
			this.commercialPlotPrice = commercialPlotPrice;
			this.commercialPlotTax = commercialPlotTax;
			this.embassyPlotPrice = embassyPlotPrice;
			this.embassyPlotTax = embassyPlotTax;
		}

		@Override
		public void TryFix(BrownyConfig config) {
			//Goes thru all values in this class and checks if they are NaN, if so, it'll replace them with the default value defined in the config
			if (Double.isNaN(plotPrice)) {
				plotPrice = config.defaultPlotPrices.plotPrice;
				dirty = true;
			}
			if (Double.isNaN(plotTax)) {
				plotTax = config.defaultPlotPrices.plotTax;
				dirty = true;
			}
			if (Double.isNaN(commercialPlotPrice)) {
				commercialPlotPrice = config.defaultPlotPrices.commercialPlotPrice;
				dirty = true;
			}
			if (Double.isNaN(commercialPlotTax)) {
				commercialPlotTax = config.defaultPlotPrices.commercialPlotTax;
				dirty = true;
			}
			if (Double.isNaN(embassyPlotPrice)) {
				embassyPlotPrice = config.defaultPlotPrices.embassyPlotPrice;
				dirty = true;
			}
			if (Double.isNaN(plotPrice)) {
				embassyPlotTax = config.defaultPlotPrices.embassyPlotTax;
				dirty = true;
			}
		}

		public double getPlotPrice() {
			return plotPrice;
		}

		public double getPlotTax() {
			return plotTax;
		}

		public double getCommercialPlotPrice() {
			return commercialPlotPrice;
		}

		public double getCommercialPlotTax() {
			return commercialPlotTax;
		}

		public double getEmbassyPlotPrice() {
			return embassyPlotPrice;
		}

		public double getEmbassyPlotTax() {
			return embassyPlotTax;
		}

		public void setPlotPrice(double plotPrice) {
			this.plotPrice = plotPrice;
			this.dirty = true;
		}

		public void setPlotTax(double plotTax) {
			this.plotTax = plotTax;
			this.dirty = true;
		}

		public void setCommercialPlotPrice(double commercialPlotPrice) {
			this.commercialPlotPrice = commercialPlotPrice;
			this.dirty = true;
		}

		public void setCommercialPlotTax(double commercialPlotTax) {
			this.commercialPlotTax = commercialPlotTax;
			this.dirty = true;
		}

		public void setEmbassyPlotPrice(double embassyPlotPrice) {
			this.embassyPlotPrice = embassyPlotPrice;
			this.dirty = true;
		}

		public void setEmbassyPlotTax(double embassyPlotTax) {
			this.embassyPlotTax = embassyPlotTax;
			this.dirty = true;
		}
	}
}
