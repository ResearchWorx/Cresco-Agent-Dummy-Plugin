package plugin;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.configuration.SubnodeConfiguration;

import plugincore.PluginEngine;
import plugins.PluginInterface;
import shared.CmdEvent;
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
	public CmdEvent incomingCommand(CmdEvent command)
    {
		return ((PluginEngine) pe).incomingCommand(command);
	}
	public LogEvent incomingLog(LogEvent log)
    {
		return ((PluginEngine) pe).incomingLog(log);
	}
	
	   /*
	public CmdEvent incomingCommand(CmdEvent command);
	   public CmdEvent outgoingCommand(CmdEvent command);
	   public LogEvent incomingCommand(LogEvent log);
	   public LogEvent outgoingCommand(LogEvent log);
	   */
	
	public boolean initialize(ConcurrentLinkedQueue<LogEvent> logQueue,SubnodeConfiguration configObj,String pluginSlot) 
	{
	   return ((PluginEngine) pe).initialize(logQueue, configObj,pluginSlot);
    }
	public void shutdown()
	{
		   ((PluginEngine) pe).shutdown(); 
	}
	
}

