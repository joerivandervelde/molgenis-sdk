package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.Query;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.db.QueryRule.Operator;
import org.molgenis.framework.server.MolgenisContext;
import org.molgenis.framework.server.MolgenisRequest;
import org.molgenis.framework.server.MolgenisResponse;
import org.molgenis.framework.server.MolgenisService;
import org.molgenis.xgap.Gene;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Controller class for the data explorer.
 * 
 * The implementation javascript file for the resultstable is defined in a MolgenisSettings property named
 * 'dataexplorer.resultstable.js' possible values are '/js/SingleObservationSetTable.js' or
 * '/js/MultiObservationSetTable.js' with '/js/MultiObservationSetTable.js' as the default
 * 
 * @author erwin
 * 
 */
public class MutationService implements MolgenisService
{
	protected MolgenisContext mc;
	protected Database db;
	public MutationService(MolgenisContext mc)
	{
		this.mc = mc;
	}
	
	@Override
	public void handleRequest(MolgenisRequest request, MolgenisResponse response)
			throws ParseException, DatabaseException, IOException {
		Database db = request.getDatabase();
		this.db = db;
		List<Gene> result = null;
		
		// get id and process data
		if(request.get("hit").toString() != ""){
			String[] id = request.get("hit").toString().split("_");
			String start = id[id.length - 2]; // get second to last element
			String end = id[id.length - 1]; // get last element
			String mId = id[id.length - 3];
//			System.out.println("start: " + start);
//			System.out.println("end: " + end);
//			System.out.println("id: " + mId);
		
			Query q = db.query(Gene.class);
			q.addRules(new QueryRule(Gene.BPSTART, Operator.GREATER_EQUAL, start), new QueryRule(Gene.BPEND, Operator.LESS_EQUAL, end));
			result = q.find();
		}else{
			Query q = db.query(Gene.class);
			result = q.find();
		}
		
//		List<Gene> result2 = db.find(Gene.class, new QueryRule(Gene.NAME, Operator.EQUALS, "mijn favoriete gen"));
		
		JsonObject jsonObject = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		for (Gene g : result)
		{
			JsonObject jsonValues = new JsonObject();
			for (String field : g.getFields())
			{
				if (g.get(field) != null) jsonValues.addProperty(field, g.get(field).toString());
				else jsonValues.addProperty(field, "");
			}
			jsonArray.add(jsonValues);
		}
		jsonObject.add("mut", jsonArray);
		
		// send it back to client
		//System.out.println("JSON\r" + jsonObject.toString());
		PrintWriter p = response.getResponse().getWriter();
		response.getResponse().setContentType("text/plain");
		p.print(jsonObject);
		p.close();
	}
}
