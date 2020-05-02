package io.github.theepicblock.browny.storage.interfaces;

/**
 * This class can be marked as dirty when any of its values are changed
 * @author TheEpicBlock_TEB
 */
public interface CanBeDirty {
	public boolean isDirty();
	
	public void markDirty(boolean v);
}
