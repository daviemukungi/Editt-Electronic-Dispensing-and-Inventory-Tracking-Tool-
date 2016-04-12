package org.msh.fdt.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Allows metadata to be downloaded from the database to an XML file and
 * also to be uploaded from an XML file into the database.
 *
 * Created by gitahi on 20/03/15.
 */
public interface MetaDataService {

	/**
	 * Writes out XML content to the HTTP response stream of the metadata
	 * download request.
	 *
	 * @param responseOutputStream the OutputStream of the HTTP response
	 */
	void download(OutputStream responseOutputStream)  throws IOException;


	/**
	 * Writes out XML content of the metadata file to the database.
	 *
	 * @param fileInputStream the metadata file InputStream
	 */
	String upload(InputStream fileInputStream);
}
