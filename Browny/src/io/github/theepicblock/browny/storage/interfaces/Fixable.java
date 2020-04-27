package io.github.theepicblock.browny.storage.interfaces;

import io.github.theepicblock.browny.config.BrownyConfig;

public interface Fixable {
	/**
	 * Attempts to fix this object. 
	 * In general this should look for any missing fields and fill them in with placeholders/default values from the config.
	 * It should also mark this as Dirty (if applicable)
	 */
	public void TryFix(BrownyConfig config);
}
