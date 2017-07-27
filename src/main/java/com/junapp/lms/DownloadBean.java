package com.junapp.lms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.junapp.lms.entity.Server;
import com.junapp.lms.networking.SSLHelper;
import com.junapp.lms.persistence.EntityDAO;

@ManagedBean
@SessionScoped
public class DownloadBean {

	private DataModel<Server> serverDM;

	public void downloadLog() throws IOException
	{
		Server selectedServer = serverDM.getRowData();
		scpFile(selectedServer);
		downloadFile();
	}
	
	@SuppressWarnings({ "resource", "unused" })
	private void downloadFile() throws IOException
	{
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();

	    File file = new File("/tmp/"+serverDM.getRowData().getLogName());
	    FileInputStream input = new FileInputStream(file);
	    OutputStream output = ec.getResponseOutputStream();
	    
	    ec.responseReset();
	    ec.setResponseContentType("text/plain");
	    ec.setResponseContentLength((int) file.length());
	    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + serverDM.getRowData().getLogName() + "\"");
	    
	    byte[] buffer = new byte[1024];  
        int i = 0;  
        while ((i = input.read(buffer)) != -1) {  
            output.write(buffer);  
            output.flush();  
        }
        output.close();
	    
	    fc.responseComplete();
	}
	
	private void scpFile(Server server)
	{
		SSLHelper.scpFrom(server);
	}
	
	public DataModel<Server> getServerDM()
	{
		if(serverDM == null)
		{
			serverDM = new ListDataModel<Server>(getAllServers());
		}
		return serverDM;
	}
	
	private List<Server> getAllServers()
	{
		return EntityDAO.instance.getAllServers();
	}
}
