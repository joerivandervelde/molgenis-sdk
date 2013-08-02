package org.molgenis.example;

import java.io.File;
import java.io.IOException;

import javax.persistence.Persistence;

import org.molgenis.EntitiesImporterImpl;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.Database.DatabaseAction;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.jpa.JpaDatabase;
import org.molgenis.io.csv.CsvReader;

public class ImportGoNL {

	/**
	 * works on the output of ConvertToImportableEntities
	 * 
	 * @param args
	 * @throws DatabaseException
	 * @throws IOException 
	 */
	public static void main(String[] args) throws DatabaseException, IOException {
		
		File extractDir = new File("/Users/pdopheide/Documents/GoNL/release4_noContam_noChildren_with_AN_AC_GTC_stripped");
		
		Database db = new org.molgenis.JpaDatabase(
				Persistence
						.createEntityManagerFactory(JpaDatabase.DEFAULT_PERSISTENCE_UNIT_NAME));
		EntitiesImporterImpl entitiesImporter = new EntitiesImporterImpl(db);
		
		for(File f : extractDir.listFiles())
		{
			if(f.getName().startsWith("parsed_"))
			{
				CsvReader csvReader = new CsvReader(f, '\t', true);
				entitiesImporter.importEntities(csvReader, "GoNLVariant", DatabaseAction.ADD);
				csvReader.close();
			}
		}

	}

}
