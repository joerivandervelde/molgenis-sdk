package services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.biojava.servlets.dazzle.DazzleServlet;
import org.molgenis.example.WebAppInitializer;
import org.molgenis.framework.server.MolgenisContext;
import org.molgenis.framework.server.MolgenisRequest;
import org.molgenis.framework.server.MolgenisResponse;
import org.molgenis.framework.server.MolgenisService;

public class MolgenisDasService extends DazzleServlet implements MolgenisService
{
	private static final Logger logger = Logger.getLogger(WebAppInitializer.class);
	private static final long serialVersionUID = 1063263480490518100L;

	public MolgenisDasService(MolgenisContext mc) throws ServletException
	{
		init(mc.getServletConfig());
	}

	@Override
	public void destroy()
	{
		super.destroy();
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Override
	public void handleRequest(MolgenisRequest request, MolgenisResponse response) throws IOException
	{
		try
		{
			/**
			 * DEMO PURPOSES!!
			 */
			request.getClass();
			
			HttpServletRequest req = request.getRequest();
			String requestURI = req.getRequestURI();
			
			if(requestURI.startsWith("/das/patient&pid=")){
				String toReplace = requestURI.substring(requestURI.indexOf("&pid="), requestURI.lastIndexOf("/")); // we want to keep the slash in the URI
				String newURI = requestURI.replace(toReplace, ""); // remove &pid= from the URI
				XgapGenePlugin2Patient.patientId = toReplace.split("=")[1]; // pass patient id to plugin code
				req.getRequestDispatcher(newURI).forward(req, response.getResponse());
			}

			super.doGet(request.getRequest(), response.getResponse());
		}
		catch (ServletException e)
		{
			logger.error("Failed to handle request: " + e.getMessage());
		}
	}
}
