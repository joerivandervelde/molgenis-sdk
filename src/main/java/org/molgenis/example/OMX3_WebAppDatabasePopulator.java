package org.molgenis.example;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.persistence.Persistence;

import org.molgenis.JpaDatabase;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.model.MolgenisModelException;
import org.molgenis.model.elements.Entity;
import org.molgenis.model.elements.UISchema;
import org.molgenis.omx.core.EntityClass;
import org.molgenis.omx.core.SystemClass;
import org.molgenis.omx.core.TargetClass;
import org.molgenis.omx.core.ValueClass;

public class OMX3_WebAppDatabasePopulator
{
	
	public void populateOMX3Database() throws DatabaseException, IOException
	{
		Database database = new org.molgenis.JpaDatabase(
				Persistence.createEntityManagerFactory(JpaDatabase.DEFAULT_PERSISTENCE_UNIT_NAME));
		
		Vector<Entity> entities = database.getMetaData().getEntities();
		
		
		Vector<Entity> systemClasses = new Vector<Entity>();
		Vector<Entity> entityClasses = new Vector<Entity>();
		Vector<Entity> valueClasses = new Vector<Entity>();
		Vector<Entity> targetClasses = new Vector<Entity>();
		Vector<SystemClass> guiSystemClasses = new Vector<SystemClass>();
		
		
		//FIXME: empty list..
		List<UISchema> uiElements = database.getMetaData().getUserinterface().getAllChildren();
		for(UISchema uiElement : uiElements)
		{
			System.out.println("uiElement " + uiElement.toString());
			SystemClass sc = new SystemClass();
			sc.setEntityType(uiElement.getType().toString());
			sc.setEntityClassName(uiElement.getClassName());
			sc.setEntityName(uiElement.getName());
			guiSystemClasses.add(sc);
		}
		
		
		
		outer:
		for(Entity e : entities)
		{
			for(Entity ancestor : e.getAllAncestors())
			{
				if(e.getAllAncestors().size() == 1 && ancestor.getName().equals("Value"))
				{
					valueClasses.add(e);
					continue outer;
				}
				if(ancestor.getName().equals("Target"))
				{
					targetClasses.add(e);
					continue outer;
				}
			}
			if(e.isSystem() || e.isAbstract())
			{
				systemClasses.add(e);
				continue;
			}
			entityClasses.add(e);
		}
		
		System.out.println("guiSystemClasses");
		for(SystemClass e : guiSystemClasses)
		{
			System.out.println("\t" + e.toString());
		}
		
		System.out.println("systemClasses");
		for(Entity e : systemClasses)
		{
			System.out.println("\t" + e.getName());
		
			SystemClass sc = new SystemClass();
			sc.setEntityName(e.getName());
			sc.setEntityClassName(e.getName());
			sc.setEntityType("SystemClass");
			database.add(sc);
		}
		
		System.out.println("entityClasses");
		for(Entity e : entityClasses)
		{
			System.out.println("\t" + e.getName());
			
			EntityClass ec = new EntityClass();
			ec.setEntityType("EntityClass");
			ec.setEntityName(e.getName());
			ec.setEntityClassName(e.getName());
			database.add(ec);
		}
		
		System.out.println("targetClasses");
		for(Entity e : targetClasses)
		{
			System.out.println("\t" + e.getName());
			
			TargetClass tc = new TargetClass();
			tc.setEntityType("TargetClass");
			tc.setEntityName(e.getName());
			tc.setEntityClassName(e.getName());
			database.add(tc);
		}
		
		System.out.println("valueClasses");
		for(Entity e : valueClasses)
		{
			System.out.println("\t" + e.getName());
			
			ValueClass vc = new ValueClass();
			vc.setEntityType("ValueClass");
			vc.setEntityName(e.getName());
			vc.setEntityClassName(e.getName());
			database.add(vc);
		}
		
		
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
		new OMX3_WebAppDatabasePopulator().populateOMX3Database();
	}

}
