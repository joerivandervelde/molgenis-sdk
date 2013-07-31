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
import org.molgenis.xgap.Patient;

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
		List<Patient> result = null;
		
		// get id and process data
		if(request.get("hit").toString() != ""){
			String[] id = request.get("hit").toString().split("_");
			String mId = id[id.length - 3];
			System.out.println("mutation id: " + mId); // used for debugging, testing, etc.
		
			Query q = db.query(Patient.class);
			q.addRules(new QueryRule(Patient.MUTATION1, Operator.EQUALS, mId));
			q.addRules(new QueryRule(Operator.OR));
			q.addRules(new QueryRule(Patient.MUTATION2, Operator.EQUALS, mId));
			result = q.find();
		}else{
			Query q = db.query(Patient.class);
			result = q.find();
		}
		
		JsonObject jsonObject = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		for (Patient p : result)
		{
			JsonObject jsonValues = new JsonObject();
			for (String field : p.getFields())
			{
				if (p.get(field) != null) jsonValues.addProperty(field, p.get(field).toString());
				else jsonValues.addProperty(field, "");
			}
			jsonArray.add(jsonValues);
		}
		jsonObject.add("mut", jsonArray);
		
		PrintWriter p = response.getResponse().getWriter();
		response.getResponse().setContentType("text/plain");
		p.print(jsonObject);
		p.close();
	}
}
