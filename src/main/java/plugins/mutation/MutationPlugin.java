package plugins.mutation;

import java.util.Vector;

import org.molgenis.framework.db.Database;
import org.molgenis.framework.server.MolgenisRequest;
import org.molgenis.framework.ui.EasyPluginController;
import org.molgenis.framework.ui.PluginModel;
import org.molgenis.framework.ui.ScreenController;
import org.molgenis.framework.ui.ScreenMessage;
import org.molgenis.framework.ui.ScreenModel;
import org.molgenis.framework.ui.ScreenView;

public class MutationPlugin extends PluginModel
{
	public MutationPlugin(String name, ScreenController<?> parent)
	{
		super(name, parent);
	}

	@Override
	public String getViewName()
	{
		return "plugins_mutation_MutationPlugin";
	}

	@Override
	public String getViewTemplate()
	{
		return "plugins/mutation/MutationPlugin.ftl";
	}

	@Override
	public void handleRequest(Database db, MolgenisRequest request)
	{
		//static
	}

	@Override
	public void reload(Database db)
	{
		//static
	}

	@Override
	public boolean isVisible()
	{
		return true;
	}

	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
		
	}
	
	// adding custom content to the html <header>
	@Override
	public String getCustomHtmlHeaders(){
		return "<script language=\"javascript\" src=\"js/dalliance-all.custom.js\"></script><script language=\"javascript\" src=\"js/biodalliance-genome-browser.js\"></script><script language=\"javascript\" src=\"js/information-table.js\"></script><link rel=\"stylesheet\" href=\"css/bootstrap-scoped.css\" type=\"text/css\"><link rel=\"stylesheet\" href=\"css/dalliance-scoped.css\" type=\"text/css\">";
		
		// For testing
		//return "<script language=\"javascript\" src=\"js/dalliance-all.custom.js\"></script><script language=\"javascript\" src=\"js/biodalliance-genome-browser.js\"></script><script language=\"javascript\" src=\"js/information-table.js\"></script><link rel=\"stylesheet\" href=\"css/bootstrap-scoped.css\" type=\"text/css\"><link rel=\"stylesheet\" href=\"css/dalliance-scoped.css\" type=\"text/css\">  <script type=\"text/javascript\" charset=\"utf-8\" src=\"js/DataTables-1.9.4/media/js/jquery.dataTables.js\"></script>";
		//return "<script language=\"javascript\" src=\"js/dalliance-all.custom.js\"></script><script language=\"javascript\" src=\"js/biodalliance-genome-browser.js\"></script><link rel=\"stylesheet\" href=\"css/bootstrap-scoped.css\" type=\"text/css\"><link rel=\"stylesheet\" href=\"css/dalliance-scoped.css\" type=\"text/css\">";
	}
	
}
