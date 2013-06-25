package org.molgenis.example;

import org.molgenis.MolgenisDatabasePopulator;
import org.molgenis.framework.db.Database;
import org.springframework.beans.factory.annotation.Value;

public class WebAppDatabasePopulator extends MolgenisDatabasePopulator
{

	@Value("${admin.password:@null}")
	private String adminPassword;

	@Override
	protected void initializeApplicationDatabase(Database database) throws Exception
	{
		new OMX3_WebAppDatabasePopulator().populateOMX3Database();
	}

	

}