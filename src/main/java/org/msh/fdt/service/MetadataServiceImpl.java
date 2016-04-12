package org.msh.fdt.service;

import com.intellisoftkenya.fdt.metadata.api.SchemaMiner;
import com.intellisoftkenya.fdt.metadata.api.XmlReader;
import com.intellisoftkenya.fdt.metadata.api.XmlWriter;
import com.intellisoftkenya.fdt.metadata.data.Database;
import com.intellisoftkenya.fdt.metadata.sql.JdbcSqlExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Allows metadata to be downloaded from the database to an XML file and
 * also to be uploaded from an XML file into the database.
 *
 * Created by gitahi on 20/03/15.
 */
public class MetadataServiceImpl implements MetaDataService {

	/**
	 * The database connection properties to use when connecting
	 * to the database.
	 */
	private Properties connectionProperties;

	public void setConnectionProperties(Properties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	/**
	 * Writes out XML content to the HTTP response stream of the metadata
	 * download request.
	 *
	 * @param responseOutputStream the OutputStream of the HTTP response
	 */
	@Override
	public void download(OutputStream responseOutputStream) throws IOException {
		JdbcSqlExecutor.configure(connectionProperties);
		SchemaMiner ssm = new SchemaMiner();
		XmlWriter xmlWriter = new XmlWriter();
		try {
			Database database = ssm.mine();
			xmlWriter.writeXml(database.getTableList(), responseOutputStream);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (responseOutputStream != null) {
				responseOutputStream.close();
			}
		}
	}

	/**
	 * Writes out XML content of the metadata file to the database.
	 *
	 * @param fileInputStream the metadata file InputStream
	 */
	@Override
	public String upload(InputStream fileInputStream) {
		JdbcSqlExecutor.configure(connectionProperties);
		String message = "Unknown metadata upload result.";
		try {
			XmlReader reader = new XmlReader();
			reader.readXml(fileInputStream);
			message = "Metadata import succeeded.";
		} catch (Exception ex) {
			ex.printStackTrace();
			message = "Metadata import failed! " + ex.getMessage();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
					message = "Metadata import failed! " + e.getMessage();
				}
			}
		}
		return message;
	}
}
