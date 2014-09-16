package plugincore;

import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.SubnodeConfiguration;


public class PluginEngine {

	
	public PluginEngine()
	{
		
	}
	public boolean initialize(SubnodeConfiguration config) 
	{
		Iterator it = config.getKeys();
		while (it.hasNext()) {
			Object key = it.next();
			String value = config.getString(key.toString());
			//result.put(key.toString(), value);
			System.out.println(key.toString() + " " + value);
		}
		return true;
	}
	
	
	/*
	 public int getWatchDogTimer(String pluginID)
	{
		SubnodeConfiguration sObj = iniConfObj.getSection("plugin" + pluginID);
		return sObj.getInt("watchdogtimer");
	}
	public String getAMPQDataExchange(String pluginID)
	{
		SubnodeConfiguration sObj = iniConfObj.getSection("plugin" + pluginID);
		return sObj.getString("ampq_data_exchange");
	}
	public boolean setAMPQLogExchange(String pluginID,String dataExchange) throws ConfigurationException
	{
		SubnodeConfiguration sObj = iniConfObj.getSection("plugin" + pluginID);
	    sObj.setProperty("ampq_data_exchange", dataExchange);
	    iniConfObj.save();
	    return true;
	}
	public String getAMPQHost(String pluginID)
	{
		SubnodeConfiguration sObj = iniConfObj.getSection("plugin" + pluginID);
		return sObj.getString("ampq_host");
	}
	public boolean setAMPQHost(String pluginID,String host) throws ConfigurationException
	{
		SubnodeConfiguration sObj = iniConfObj.getSection("plugin" + pluginID);
	    sObj.setProperty("ampq_host", host);
	    iniConfObj.save();
	    return true;
	}
	public String getAMPQUser(String pluginID)
	{
		SubnodeConfiguration sObj = iniConfObj.getSection("plugin" + pluginID);
		return sObj.getString("ampq_username");	    
	}
	public boolean setAMPQUser(String pluginID,String userName) throws ConfigurationException
	{
		SubnodeConfiguration sObj = iniConfObj.getSection("plugin" + pluginID);
	    sObj.setProperty("ampq_username", userName);
	    iniConfObj.save();
	    return true;
	}
	public String getAMPQPassword(String pluginID)
	{
		SubnodeConfiguration sObj = iniConfObj.getSection("plugin" + pluginID);
		return sObj.getString("ampq_password");	    
	}
	public boolean setAMPQPassword(String pluginID,String password) throws ConfigurationException
	{
		SubnodeConfiguration sObj = iniConfObj.getSection("plugin" + pluginID);
	    sObj.setProperty("ampq_password", password);
	    iniConfObj.save();
	    return true;
	}

	 */
	
}
