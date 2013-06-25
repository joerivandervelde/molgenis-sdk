package plugins.empty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.db.QueryRule.Operator;
import org.molgenis.framework.server.MolgenisRequest;
import org.molgenis.framework.ui.EasyPluginController;
import org.molgenis.framework.ui.ScreenController;
import org.molgenis.framework.ui.ScreenMessage;
import org.molgenis.framework.ui.ScreenView;
import org.molgenis.framework.ui.html.HorizontalRuler;
import org.molgenis.framework.ui.html.MolgenisForm;
import org.molgenis.framework.ui.html.Paragraph;
import org.molgenis.framework.ui.html.Table;
import org.molgenis.omx.core.CustomClass;
import org.molgenis.omx.core.FlexCol;
import org.molgenis.omx.core.FlexColValue;
import org.molgenis.omx.organization.Individual;
import org.molgenis.omx.values.StringValue;

public class EmptyPlugin extends EasyPluginController<EmptyPlugin>
{
	public EmptyPlugin(String name, ScreenController<?> parent)
	{
		super(name, parent);
		this.setModel(this); // you can create a seperate class as 'model'.
	}

	// what is shown to the user
	public ScreenView getView()
	{
		MolgenisForm view = new MolgenisForm(this);
		
		// uncomment next line if you want to use template file instead
		// return new FreemarkerView("EmptyPluginView.ftl", getModel());
		try
		{

			
			List<CustomClass> ccForIndv = this.getDatabase().find(CustomClass.class, new QueryRule(CustomClass.EXTENDSCLASS_ENTITYCLASSNAME, Operator.EQUALS, "Individual"));
			ccForIndv.add(new CustomClass()); //for non-custom

			//first show all regular individuals
//			List<Individual> indvs = this.getDatabase().find(Individual.class, new QueryRule(Individual.CUSTOMCLASS, Operator.EQUALS, ""));
			
//			Table data = new Table("regularIndvs");
//			Map<String,Integer> colIndexFor = new HashMap<String,Integer>();
//			List<String> indvFields = new Individual().getFields();
//			int colCount = 0;
//			for (String f : indvFields)
//			{
//				data.addColumn(f);
//				colIndexFor.put(f, colCount++);
//			}
//			int count = 0;
//			for(Individual i : indvs)
//			{
//				data.addRow(""+count);
//				for(String field : i.getFields()){					
//					data.setCell(colIndexFor.get(field), count, (i.get(field)!=null?i.get(field):""));	
//				}
//				count++;
//			}
			
			//now make tables for the custom classes
			
			
			
			for(CustomClass cc : ccForIndv)
			{
				List<Individual> indvs = this.getDatabase().find(Individual.class, new QueryRule(Individual.CUSTOMCLASS_ENTITYCLASSNAME, Operator.EQUALS, cc.getEntityName()));
				
				if(cc.getEntityName() == null)
				{
					view.add(new HorizontalRuler());
					Paragraph p = new Paragraph("indv");
					p.setValue("Individuals");
					view.add(p);
				}
				else
				{
					view.add(new HorizontalRuler());
					Paragraph p = new Paragraph(cc.getEntityName());
					p.setValue(cc.getEntityName());
					view.add(p);
				}
				
				Table data = new Table("regularIndvs");
				Map<String,Integer> colIndexFor = new HashMap<String,Integer>();
				List<String> indvFields = new Individual().getFields();
				int colCount = 0;
				for (String f : indvFields)
				{
					data.addColumn(f);
					colIndexFor.put(f, colCount++);
				}
				List<FlexCol> flexCols = this.getDatabase().find(FlexCol.class, new QueryRule(FlexCol.TARGETCLASS_ENTITYCLASSNAME, Operator.EQUALS, cc.getEntityClassName()));
				for(FlexCol f : flexCols){
					data.addColumn(f.getName());
					colIndexFor.put(f.getName(), colCount++);
				}
				int count = 0;
				for(Individual i : indvs)
				{
					data.addRow(""+count);
					for(String field : i.getFields()){					
						data.setCell(colIndexFor.get(field), count, (i.get(field)!=null?i.get(field):""));	
					}
					for(FlexCol f : flexCols){
						
						FlexColValue value = this.getDatabase().find(FlexColValue.class, new QueryRule(FlexColValue.TARGET_IDENTIFIER, Operator.EQUALS, i.getIdentifier()), new QueryRule(FlexColValue.FLEXCOL_IDENTIFIER, Operator.EQUALS, f.getIdentifier())).get(0);
					
						//org.molgenis.omx.observ.value.Value tmp = new org.molgenis.omx.observ.value.Value();
						//tmp.set(value.getValue().getValues());
						//Object obj = new ValueConverter(this.getDatabase()).extractValue(tmp);
				
						String val = ((StringValue)value.getValue()).getValue();
						
						data.setCell(colIndexFor.get(f.getName()), count, (val != null ? val : ""));
						
						
					
					}
					
					count++;
				}
				
				view.add(data);
			}
			
			
			
			
			

			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			view.getModel().setMessages(new ScreenMessage(e.getMessage(), false));
		}
		return view;

	}

	private String helloName = "UNKNOWN";

	// matches ActionInput("sayHello")
	public void sayHello(Database db, MolgenisRequest request)
	{
		if (!request.isNull("helloName"))
		{
			this.helloName = request.getString("helloName");
		}
	}

	@Override
	public void reload(Database db) throws Exception
	{
		// //example: update model with data from the database
		// Query q = db.query(Person.class);
		// q.like("name", "john");
		// getModel().investigations = q.find();
	}
}