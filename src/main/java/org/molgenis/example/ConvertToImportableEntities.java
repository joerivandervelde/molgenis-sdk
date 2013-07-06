package org.molgenis.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.molgenis.framework.db.DatabaseException;

public class ConvertToImportableEntities {

	/**
	 * Convert extracted files to MOLGENIS importable files
	 * Filtered on quality control = PASS
	 * 
	 * example line of values:
	 * 
	 * 1	10108	.	C	T	322.78	TruthSensitivityTranche99.90to100.00	AC=28;AN=996;GTC=470,28,0
	 * 
	 * @param args
	 * @throws DatabaseException
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws DatabaseException, FileNotFoundException {
		File extractDir = new File("/Users/jvelde/Desktop/Projects/GoNL/release4_noContam_noChildren_with_AN_AC_GTC_stripped");
		
		for(File f : extractDir.listFiles())
		{
			
			File outFile = new File(extractDir, "parsed_"+f.getName());
			PrintWriter pw = new PrintWriter(outFile);

			System.out.println("working on " + f.getName() + ", writing to " + outFile.getName());
			
			Scanner s = new Scanner(f);
			boolean endOfHeader = false;
			while(s.hasNextLine())
			{
				String line = s.nextLine();

				if(!endOfHeader && line.startsWith("#CHROM"))
				{
					endOfHeader = true;
					pw.println("Chrom\tPos\tRsid\tRef\tAlt\tQual\tFilter\tInfo");
					continue;
				}
				
				if(endOfHeader)
				{
					String[] split = line.split("\t");
					if(split[6].equals("PASS"))
					{
						pw.println(line);
					}
				}
				
			}
			
			pw.flush();
			pw.close();
			
		}
		

	}

}
