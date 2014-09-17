package plugin;

import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.commons.configuration.SubnodeConfiguration;

import channels.LogEvent;
import plugincore.PluginEngine;
import plugins.PluginInterface;


public class PluginImplementation implements PluginInterface {

	public PluginEngine pe;
	
	public PluginImplementation()
	{
		pe = new PluginEngine(); //actual plugin code
		
	}
	public String getName()
	{
		   return ((PluginEngine) pe).getName(); 
	}
    public String getVersion()
    {
    	return ((PluginEngine) pe).getVersion();
	}
 
	public String getCommandSet()
    {
		return ((PluginEngine) pe).getCommandSet();
	}
	   
	public boolean initialize(ConcurrentLinkedQueue<LogEvent> logQueue,SubnodeConfiguration configObj) 
	{
	   return ((PluginEngine) pe).initialize(logQueue, configObj);
    }	
}

