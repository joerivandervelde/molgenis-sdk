package org.molgenis.example;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.persistence.Persistence;

import org.molgenis.JpaDatabase;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.db.QueryRule.Operator;
import org.molgenis.model.MolgenisModelException;
import org.molgenis.model.elements.Entity;
import org.molgenis.model.elements.UISchema;
import org.molgenis.omx.core.EntityClass;
import org.molgenis.omx.core.SystemClass;
import org.molgenis.omx.core.TargetClass;
import org.molgenis.omx.core.ValueClass;
import org.molgenis.omx.organization.Individual;

public class OMX3_tmp
{
	
	public void populateOMX3Database() throws DatabaseException, IOException
	{
		Database database = new org.molgenis.JpaDatabase(
				Persistence.createEntityManagerFactory(JpaDatabase.DEFAULT_PERSISTENCE_UNIT_NAME));
	
		List<Individual> indvs = database.find(Individual.class, new QueryRule(Individual.CUSTOMCLASS_ENTITYCLASSNAME, Operator.EQUALS, null));
		System.out.println(indvs.size());
		
		database.close();
	}

	/**
	 * @param args
	 * @throws DatabaseException 
	 * @throws MolgenisModelException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws DatabaseException, MolgenisModelException, IOException
	{
		new OMX3_tmp().populateOMX3Database();
	}

}
