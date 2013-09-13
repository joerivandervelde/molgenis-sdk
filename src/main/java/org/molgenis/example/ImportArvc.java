package org.molgenis.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.persistence.Persistence;

import org.molgenis.EntitiesImporterImpl;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.Database.DatabaseAction;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.jpa.JpaDatabase;
import org.molgenis.io.csv.CsvReader;

public class ImportArvc
{

	/**
	 * @param args
	 * @throws DatabaseException
	 * @throws IOException
	 */
	public static void main(String[] args) throws DatabaseException, IOException
	{
		String patientsFile = "/Users/pdopheide/Desktop/ARVC_track_test.txt";
		importPatients(patientsFile);
	}
	
	private static void importPatients(String patientsFile) throws DatabaseException, IOException{
		System.out.println("preparing..");
		
		Database db = new org.molgenis.JpaDatabase(
				Persistence.createEntityManagerFactory(JpaDatabase.DEFAULT_PERSISTENCE_UNIT_NAME));
		EntitiesImporterImpl entitiesImporter = new EntitiesImporterImpl(db);
		
		System.out.println("starting import..");
		
		File pat = new File(patientsFile);
		CsvReader csvReader = new CsvReader(pat, '\t', true);
		entitiesImporter.importEntities(csvReader, "Arvc", DatabaseAction.ADD);
		csvReader.close();
		
		System.out.println("..done");
	}

}
