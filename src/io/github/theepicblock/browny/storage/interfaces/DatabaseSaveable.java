package io.github.theepicblock.browny.storage.interfaces;

import io.github.theepicblock.browny.database.Database;

public interface DatabaseSaveable {
	/**
	 * Saves this object to a database
	 * @param db database to save to
	 */
	public void Save(Database db);
}
