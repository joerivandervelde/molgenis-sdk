
package plugins.search;

import java.util.ArrayList;
import java.util.List;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.Query;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.db.QueryRule.Operator;
import org.molgenis.framework.server.MolgenisRequest;
import org.molgenis.framework.ui.EasyPluginController;
import org.molgenis.framework.ui.ScreenController;
import org.molgenis.framework.ui.ScreenMessage;
import org.molgenis.framework.ui.ScreenView;
import org.molgenis.framework.ui.html.ActionInput;
import org.molgenis.framework.ui.html.IntInput;
import org.molgenis.framework.ui.html.MolgenisForm;
import org.molgenis.framework.ui.html.SelectInput;
import org.molgenis.framework.ui.html.TupleTable;
import org.molgenis.util.tuple.Tuple;

import example.org.molgenis.gonl.Variant;

public class SearchPluginWidgetStyle extends EasyPluginController<SearchPluginWidgetStyle>
{
	public SearchPluginWidgetStyle(String name, ScreenController<?> parent)
	{
		super(name, parent);
		this.setModel(this); //you can create a seperate class as 'model'.
	}
	
	//what is shown to the user
	public ScreenView getView()
	{
		//uncomment next line if you want to use template file instead
		//return new FreemarkerView("EmptyPluginView.ftl", getModel()); 
		
		MolgenisForm view = new MolgenisForm(this);
		
		//view.add(new StringInput("chrom"));
		SelectInput chrom = new SelectInput("chrom");
		chrom.setOptions(new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","X"});
		view.add(chrom);
		view.add(new IntInput("start"));
		view.add(new IntInput("stop"));
		view.add(new ActionInput("Find"));
		
		return view;
	}
	
	private String helloName = "UNKNOWN";
	
	//matches ActionInput("sayHello")
	public void Find(Database db, MolgenisRequest request) throws DatabaseException
	{
		if(request.isNull("chrom"))
		{
			this.setMessages(new ScreenMessage("Please enter a chromosome. (1 through 22 or X)", false));
		}
		if(request.isNull("start"))
		{
			this.setMessages(new ScreenMessage("Please enter a start position for your search window. (usually between 1 and 250000000)", false));
		}
		if(request.isNull("stop"))
		{
			this.setMessages(new ScreenMessage("Please enter a stop position for your search window. (usually between 1 and 250000000)", false));
		}
		
		String chrom = request.getString("chrom");
		int start = request.getInt("start");
		int stop = request.getInt("stop");
		
		
		Query q = db.query(Variant.class);
		q.addRules(new QueryRule(Variant.POS, Operator.GREATER_EQUAL, start));
		q.addRules(new QueryRule(Variant.POS, Operator.LESS_EQUAL, stop));
		q.addRules(new QueryRule(Variant.CHROM, Operator.EQUALS, chrom));
		List<Variant> variants = q.find();
		
		if(variants.size() > 1000)
		{
			this.setMessages(new ScreenMessage("Over 1000 variants found. Please narrow down your search window.", false));
		}
		
		this.setMessages(new ScreenMessage("found " + variants.size(), true));
		
		
		List<Tuple> results = new ArrayList<Tuple>();
		for(Variant v : variants)
		{
			results.add(v.getValues());
		}
		TupleTable t = new TupleTable("results", results);
		MolgenisForm view = new MolgenisForm(this);
		view.add(t);
		

	}
	
	@Override
	public void reload(Database db) throws Exception
	{	
//		//example: update model with data from the database
//		Query q = db.query(Person.class);
//		q.like("name", "john");
//		getModel().investigations = q.find();
	}
}