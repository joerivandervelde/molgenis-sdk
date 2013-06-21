package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import org.biojava.servlets.dazzle.Segment;
import org.biojava.servlets.dazzle.datasource.AbstractGFFFeatureSource;
import org.biojava.servlets.dazzle.datasource.DataSourceException;
import org.biojava.servlets.dazzle.datasource.GFFFeature;
import org.molgenis.JpaDatabase;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.Query;
import org.molgenis.xgap.Gene;

/**
 * see http://biojava.org/wiki/Dazzle:writeplugin
 * @author jvelde
 *
 */
public class XgapGenePlugin extends AbstractGFFFeatureSource
{

	Database db;

	@Override
	public GFFFeature[] getFeatures(Segment segment, String[] arg1) throws DataSourceException
	{
		try
		{
			if (db == null)
			{
				db = new org.molgenis.JpaDatabase(
						Persistence.createEntityManagerFactory(JpaDatabase.DEFAULT_PERSISTENCE_UNIT_NAME));
			}

			System.out.println("got a features request for " + segment.toString());
			
			if(arg1 != null && arg1.length > 0)
			for (String s : arg1)
			{
				System.out.println("s: " + s);
			}

			int start = segment.getStart();
			int stop = segment.getStop();
			String chr = segment.getReference();
			System.out.println("start / stop / ref: " + start + " / " + stop + " / " + chr);

			Query<Gene> q = db.query(Gene.class);
			q.greaterOrEqual(Gene.BPSTART, start);
			q.lessOrEqual(Gene.BPEND, stop);
			q.equals(Gene.CHROMOSOME_NAME, chr);
			List<Gene> genes = q.find();
			System.out.println(genes.size() + " genes found!!!");

			List<GFFFeature> features = new ArrayList<GFFFeature>();
			
			for(Gene g : genes)
			{
				GFFFeature gff = new GFFFeature();
				gff.setLink("http://www.wormqtl.org/molgenis.do?__target=QtlFinderPublic2&select=QtlFinderPublic2&p=" + g.getName());
				gff.setType("gene");
				gff.setTypeId(g.getSeq());
				gff.setMethod(g.getSymbol());
				gff.setLabel(g.getLabel());
				gff.setStart(g.getBpStart()+"");
				gff.setEnd(g.getBpEnd()+"");
				gff.setName(g.getName());
				gff.setNote(g.getDescription());
				gff.setOrientation(g.getOrientation());
				features.add(gff);
			}

			// This is up to YOU:
			// get your data from somewhere, e.g. a database, parse a flat file
			// whatever you like.
			// then with your data we fill the GFFFeature objects

			// GFFFeature is a simple Java-bean
//			GFFFeature gff = new GFFFeature();
//
//			gff.setType("annotation type");
//			gff.setLabel("the annotation label");
//			// start and end are strings to support e.g. PDB -file residue
//			// numbering, which can contain insertion codes
//			gff.setStart("123");
//			gff.setEnd("234");
//
//			gff.setName("the name of my feature");
//			gff.setMethod("the dazzle plugin tutorial");
//			gff.setLink("http://www.biojava.org/wiki/Dazzle:writeplugin");
//			gff.setNote("the note field contains the actual annotation!");

			// see the documentation for GFFFeature for all possible fields


			// and we return our features
			return (GFFFeature[]) features.toArray(new GFFFeature[features.size()]);
		}
		catch (Exception e)
		{
			throw new DataSourceException(e);
		}
	}

}
