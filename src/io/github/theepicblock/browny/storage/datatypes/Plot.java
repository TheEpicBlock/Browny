package io.github.theepicblock.browny.storage.datatypes;

import io.github.theepicblock.browny.storage.interfaces.CanBeDirty;

public class Plot implements CanBeDirty{
	private boolean dirty;
	
	private String name;
	private double price;
	private String townUUID;
	
	
	public Plot(String name, double price, String townUUID) {
		this.name = name;
		this.price = price;
		this.townUUID = townUUID;
	}
	
	//getters
	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getTownUUID() {
		return townUUID;
	}
	
	//setters
	public void setName(String name) {
		this.name = name;
		dirty = true;
	}

	public void setPrice(double price) {
		this.price = price;
		dirty = true;
	}

	public void setTownUUID(String townUUID) {
		this.townUUID = townUUID;
		dirty = true;
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return dirty;
	}
	
	@Override
	public void markDirty(boolean v) {
		this.dirty = v;
	}
}
