package plugin;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.configuration.SubnodeConfiguration;

import plugincore.PluginEngine;
import plugins.PluginInterface;
import shared.LogEvent;


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
	public String executeCommand(String command)
    {
		return ((PluginEngine) pe).executeCommand(command);
	}
	   
	public boolean initialize(ConcurrentLinkedQueue<LogEvent> logQueue,SubnodeConfiguration configObj) 
	{
	   return ((PluginEngine) pe).initialize(logQueue, configObj);
    }	
}

