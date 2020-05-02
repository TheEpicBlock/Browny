package io.github.theepicblock.browny.util;

import io.github.theepicblock.browny.config.BrownyConfig;

/**
 * contains info needed to fill in any missing info from objects.
 * @author TheEpicBlock_TEB
 *
 */
public class FixInfo {
	private BrownyConfig config;
	
	public FixInfo(BrownyConfig config) {
		this.config = config;
	}

	public BrownyConfig getConfig() {
		return config;
	}
}
