package io.github.theepicblock.browny.storage.datatypes.permissions;

import io.github.theepicblock.browny.storage.interfaces.CanBeDirty;

/**
 * Contains PermissionLines for all the types of members of a town;
 * @author TheEpicBlock_TEB
 *
 */
public class TownPermissions implements CanBeDirty{
	public PermissionLine residents;
	public PermissionLine nation;
	public PermissionLine ally;
	public PermissionLine outsider;
	
	@Override
	public boolean isDirty() {
		return residents.isDirty() | nation.isDirty() | ally.isDirty() | outsider.isDirty();
	}
	@Override
	public void markDirty(boolean v) {
		residents.markDirty(v);
		nation.markDirty(v);   
		ally.markDirty(v);     
		outsider.markDirty(v); 
	}
}
