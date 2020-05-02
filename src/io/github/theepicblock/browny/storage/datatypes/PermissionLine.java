package io.github.theepicblock.browny.storage.datatypes;

import io.github.theepicblock.browny.storage.interfaces.CanBeDirty;

/**
 * Defines actions that someone can or can't take
 * @author TheEpicBlock_TEB
 */
public class PermissionLine implements CanBeDirty{
	private boolean dirty;
	
	private boolean build;
	private boolean destoy;
	private boolean _switch;
	private boolean itemUse;
	
	public PermissionLine(boolean build, boolean destoy, boolean _switch, boolean itemUse) {
		this.build = build;
		this.destoy = destoy;
		this._switch = _switch;
		this.itemUse = itemUse;
	}
	
	//dirty
	@Override
	public boolean isDirty() {
		return dirty;
	}
	@Override
	public void markDirty(boolean v) {
		dirty = v;
	}
	
	//getters
	public boolean getBuild() {
		return build;
	}
	public boolean getDestoy() {
		return destoy;
	}
	public boolean getSwitch() {
		return _switch;
	}
	public boolean getItemUse() {
		return itemUse;
	}
	
	//setters
	public void setBuild(Boolean build) {
		this.build = build;
		dirty = true;
	}
	public void setDestoy(Boolean destoy) {
		this.destoy = destoy;
		dirty = true;
	}
	public void setSwitch(Boolean _switch) {
		this._switch = _switch;
		dirty = true;
	}
	public void setItemUse(Boolean itemUse) {
		this.itemUse = itemUse;
		dirty = true;
	}
}
