package io.github.theepicblock.browny.database;

import io.github.theepicblock.browny.util.FixInfo;

/**
 * An extended version of the Towny Flatfile. Has a few Browny-specific optimizations
 * @author TheEpicBlock_TEB
 *
 */
public class BrownyFlatfile extends TownyFlatfile {

	public BrownyFlatfile(String path, FixInfo fixInfo, int plotSize) {
		super(path, fixInfo, plotSize);
	}

}
