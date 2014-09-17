package plugincore;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.SubnodeConfiguration;

public class PluginConfig {

	private SubnodeConfiguration configObj; 
	  
	
	public PluginConfig(SubnodeConfiguration configObj) throws ConfigurationException
	{
	    this.configObj = configObj;
	}
	public String getPluginName()
	{
		return configObj.getString("pluginname");
	}
	public int getWatchDogTimer()
	{
		return configObj.getInt("watchdogtimer");
	}
	
	
}