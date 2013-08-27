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

public class ImportGenes
{

	/**
	 * @param args
	 * @throws DatabaseException
	 * @throws IOException
	 */
	public static void main(String[] args) throws DatabaseException, IOException
	{
		String elegansChromosomes = "src/main/resources/elegans_chromosomes.tsv";
		String elegansGenes = "src/main/resources/elegans_genes.tsv";
		
		String col7a1Chromosomes = "src/main/resources/col7a1-chromosomes.tsv";
		String col7a1Genes = "src/main/resources/col7a1-mutations.tsv";
		
		String chd7Chromosomes = "src/main/resources/CHD7-chromosomes.tsv";
//		String chd7Genes = "src/main/resources/CHD7-mutations.tsv";
		String chd7Genes = "src/main/resources/CHD7-mutationsModifiedID.tsv";
		
		//importGenes(elegansChromosomes, elegansGenes);
//		importGenes(col7a1Chromosomes, col7a1Genes);
//		importGenes(chd7Chromosomes, chd7Genes);
		
		// ###
//		String patientsFile = "src/main/resources/CHD7-patients.tsv";
		String patientsFile = "src/main/resources/col7a1-patients-PUBLIC-DEMODATA.tsv";
		importPatients(patientsFile);
	}
	
	private static void importGenes(String chromosomesFile, String genesFile) throws DatabaseException, IOException{
		System.out.println("preparing..");
		
		Database db = new org.molgenis.JpaDatabase(
				Persistence.createEntityManagerFactory(JpaDatabase.DEFAULT_PERSISTENCE_UNIT_NAME));
		EntitiesImporterImpl entitiesImporter = new EntitiesImporterImpl(db);
		
		System.out.println("starting import of chromosomes..");
		
		File chromos = new File(chromosomesFile);
		CsvReader csvReader = new CsvReader(chromos, '\t', true);
		entitiesImporter.importEntities(csvReader, "Chromosome", DatabaseAction.ADD);
		csvReader.close();
		
		System.out.println("..done, starting import of genes..");
		
		File genes = new File(genesFile);
		csvReader = new CsvReader(genes, '\t', true);
		entitiesImporter.importEntities(csvReader, "Gene", DatabaseAction.ADD);
		csvReader.close();
		
		System.out.println("..done");
	}
	
	private static void importPatients(String patientsFile) throws DatabaseException, IOException{
		System.out.println("preparing..");
		
		Database db = new org.molgenis.JpaDatabase(
				Persistence.createEntityManagerFactory(JpaDatabase.DEFAULT_PERSISTENCE_UNIT_NAME));
		EntitiesImporterImpl entitiesImporter = new EntitiesImporterImpl(db);
		
		System.out.println("starting import of patients..");
		
		File pat = new File(patientsFile);
		CsvReader csvReader = new CsvReader(pat, '\t', true);
		entitiesImporter.importEntities(csvReader, "Patient", DatabaseAction.ADD);
		csvReader.close();
		
		System.out.println("..done");
	}

}
