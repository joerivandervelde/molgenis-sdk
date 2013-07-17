package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Persistence;
import javax.servlet.ServletContext;

import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.symbol.Alphabet;
import org.biojava.bio.symbol.SimpleAtomicSymbol;
import org.biojava.bio.symbol.Symbol;
import org.biojava.servlets.dazzle.Segment;
import org.biojava.servlets.dazzle.datasource.AbstractGFFFeatureSource;
import org.biojava.servlets.dazzle.datasource.DataSourceException;
import org.biojava.servlets.dazzle.datasource.DazzleReferenceSource;
import org.biojava.servlets.dazzle.datasource.GFFFeature;
import org.molgenis.JpaDatabase;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.Query;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.db.QueryRule.Operator;
import org.molgenis.xgap.Chromosome;
import org.molgenis.xgap.Gene;

/**
 * see http://biojava.org/wiki/Dazzle:writeplugin
 * 
 * @author jvelde
 * 
 */
public class XgapGenePlugin2Chd7 extends AbstractGFFFeatureSource implements
		DazzleReferenceSource {

	Database db;

	String fileName; // the filename to parse

	public void init(ServletContext ctx) throws DataSourceException {
		super.init(ctx);
		try {
			db = new org.molgenis.JpaDatabase(
					Persistence
							.createEntityManagerFactory(JpaDatabase.DEFAULT_PERSISTENCE_UNIT_NAME));
		} catch (Exception ex) {
			throw new DataSourceException(ex, "Couldn't load sequence file");
		}
	}

	/**
	 * try to parse a score out of the feature notes
	 * 
	 */
	// public String getScore(Feature f) {

	// }

	@Override
	public String getDataSourceType() {

		return "MOLGENIS-DAS";
	}

	@Override
	public String getDataSourceVersion() {

		return "1.00";
	}

	/**
	 * this needs to return the DNA sequence of the chromosome!
	 * see http://www.derkholm.net:8080/das/cel_61_220/sequence?segment=V
	 */
	@Override
	public Sequence getSequence(String ref) throws DataSourceException,
			NoSuchElementException {
		Sequence s = null;

		//Alphabet dna = DNATools.getDNA();
		// Annotation a = new SimpleAnnotation();
		//Symbol s = new SimpleAtomicSymbol(null, null);

		try {
			System.out.println("GETTING CHR: " + ref);
			Chromosome chr = db.find(Chromosome.class,
					new QueryRule(Chromosome.NAME, Operator.EQUALS, ref))
					.get(0);
//			s = org.biojava.bio.seq.SequenceTools.createDummy(dna, chr
//					.getBpLength().intValue(), null, "URI", ref);
			
			s = org.biojava.bio.seq.ProteinTools.createProteinSequence(ref, ref+"iets");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	@Override
	public Set getAllTypes() {
		Set<String> s = new TreeSet<String>();
		s.add("Gene");
		return Collections.unmodifiableSet(s);
	}

	/**
	 * Return the names of the chromosomes
	 * see: http://www.derkholm.net:8080/das/cel_61_220/entry_points
	 */
	@Override
	public Set<String> getEntryPoints() {
		Set<String> s = new TreeSet<String>();

		try {
			List<Chromosome> chrs = db.find(Chromosome.class);

			// iterate through the sequences
			for (Chromosome chr : chrs) {
				s.add(chr.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	@Override
	public String getMapMaster() {
		// TODO Auto-generated method stub
		return "http://www.wormbase.org/db/das/elegans";
	}

	@Override
	public String getLandmarkVersion(String ref) throws DataSourceException,
			NoSuchElementException {
		return "1.00";
	}

	@Override
	public GFFFeature[] getFeatures(Segment segment, String[] arg1)
			throws DataSourceException {
		try {
			if (db == null) {
				db = new org.molgenis.JpaDatabase(
						Persistence
								.createEntityManagerFactory(JpaDatabase.DEFAULT_PERSISTENCE_UNIT_NAME));
			}

			System.out.println("got a features request for "
					+ segment.toString());

			if (arg1 != null && arg1.length > 0)
				for (String s : arg1) {
					System.out.println("s: " + s);
				}

			int start = segment.getStart();
			int stop = segment.getStop();
			String chr = segment.getReference();
			System.out.println("start / stop / ref: " + start + " / " + stop
					+ " / " + chr);

			Query<Gene> q = db.query(Gene.class);
			q.greaterOrEqual(Gene.BPSTART, start);
			q.lessOrEqual(Gene.BPEND, stop);
			q.equals(Gene.CHROMOSOME_NAME, chr);
			List<Gene> genes = q.find();
			System.out.println(genes.size() + " genes found!!!");

			List<GFFFeature> features = new ArrayList<GFFFeature>();

			for (Gene g : genes) {
				GFFFeature gff = new GFFFeature();
				//gff.setLink("http://www.deb-central.org/molgenis.do?__target=SearchPlugin&__action=showMutation&mid="
				//		+ g.getName() + "#results");
				gff.setType("mutation");
				gff.setTypeId(g.getSeq());
				gff.setMethod(g.getSymbol());
				//gff.setLabel(g.getLabel());
				gff.setStart(g.getBpStart() + "");
				gff.setEnd(g.getBpEnd() + "");
				gff.setName(g.getName());
				gff.setNote(g.getLabel() + "; " + g.getDescription());
				//gff.setOrientation(g.getOrientation());
				features.add(gff);
			}

			// and we return our features
			return (GFFFeature[]) features.toArray(new GFFFeature[features
					.size()]);
		} catch (Exception e) {
			throw new DataSourceException(e);
		}
	}

}
