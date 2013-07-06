/* Date:        July 12, 2010
 * Template:	PluginScreenJavaTemplateGen.java.ftl
 * generator:   org.molgenis.generators.ui.PluginScreenJavaTemplateGen 3.3.2-testing
 * 
 * THIS FILE IS A TEMPLATE. PLEASE EDIT :-)
 */

package plugins.search;

import java.util.Arrays;
import java.util.List;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.Query;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.db.QueryRule.Operator;
import org.molgenis.framework.server.MolgenisRequest;
import org.molgenis.framework.ui.PluginModel;
import org.molgenis.framework.ui.ScreenController;
import org.molgenis.framework.ui.ScreenMessage;

import example.org.molgenis.gonl.Variant;

public class SearchPlugin extends PluginModel {

	public SearchPlugin(String name, ScreenController<?> parent) {
		super(name, parent);
	}

	private Integer start;
	private Integer stop;
	private String chrom;
	private List<Variant> results;
	private List<String> chromOpts;
	
	public List<String> getChromOpts()
	{
		return chromOpts;
	}
	
	public Integer getStart() {
		return start;
	}

	public Integer getStop() {
		return stop;
	}

	public String getChrom() {
		return chrom;
	}

	public List<Variant> getResults() {
		return results;
	}

	@Override
	public String getViewName() {
		return "plugins_search_SearchPlugin";
	}

	@Override
	public String getViewTemplate() {
		return "templates/" + SearchPlugin.class.getName().replace('.', '/')
				+ ".ftl";
	}

	@Override
	public void handleRequest(Database db, MolgenisRequest request) {
		try {
			
			if (request.isNull("chrom")) {
				throw new Exception(
						"Please enter a chromosome. (1 through 22 or X)");
			}
			String chrom = request.getString("chrom");
			this.chrom = chrom;
			
			if (request.isNull("start")) {
				throw new Exception(
						"Please enter a start position for your search window. (usually between 0 and 250000000)");
			}
			int start = request.getInt("start");
			this.start = start;
			
			if (request.isNull("stop")) {
				throw new Exception(
						"Please enter a stop position for your search window. (usually between 0 and 250000000)");
			}
			int stop = request.getInt("stop");
			this.stop = stop;
			
			if(start < 0 || stop < 0)
			{
				throw new Exception("Start/stop position may not be negative");
			}
			
			if(start > stop)
			{
				throw new Exception("Start position must come before stop position");
			}
			
			Query<Variant> q = db.query(Variant.class);
			q.addRules(new QueryRule(Variant.CHROM, Operator.EQUALS, chrom));
			q.addRules(new QueryRule(Variant.POS, Operator.GREATER_EQUAL, start));
			q.addRules(new QueryRule(Variant.POS, Operator.LESS_EQUAL, stop));
			List<Variant> variants = q.find();

			if (variants.size() > 10000) {
				throw new Exception(
						"Over 10000 variants found. Please narrow down your search window.");
			}

			this.results = variants;

			this.setMessages(new ScreenMessage("Found " + variants.size()
					+ " variants in region chr" + chrom + ":" + start + "-" + stop, true));

		} catch (Exception e) {
			this.setMessages(new ScreenMessage(e.getMessage(), false));
		}
	}

	@Override
	public void reload(Database db) {
		if(this.chromOpts == null)
		{
			List<String> chromOpts = Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","X"});
			this.chromOpts = chromOpts;
		}
	}

	@Override
	public boolean isVisible() {
		return true;
	}
}
