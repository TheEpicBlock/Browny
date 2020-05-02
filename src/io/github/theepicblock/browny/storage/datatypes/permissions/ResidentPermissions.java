package io.github.theepicblock.browny.storage.datatypes.permissions;
import io.github.theepicblock.browny.storage.interfaces.CanBeDirty;

/**
 * Contains PermissionLines for all the types of relatives to a resident;
 * @author TheEpicBlock_TEB
 *
 */
public class ResidentPermissions implements CanBeDirty{
	public PermissionLine friend;
	public PermissionLine town;
	public PermissionLine ally;
	public PermissionLine outsider;
	
	@Override
	public boolean isDirty() {
		return friend.isDirty() | town.isDirty() | ally.isDirty() | outsider.isDirty();
	}
	@Override
	public void markDirty(boolean v) {
		friend.markDirty(v);
		town.markDirty(v);   
		ally.markDirty(v);     
		outsider.markDirty(v); 
	}
}
