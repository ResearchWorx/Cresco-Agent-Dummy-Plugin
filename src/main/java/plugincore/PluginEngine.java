package plugincore;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import org.apache.commons.configuration.SubnodeConfiguration;

import plugin.PluginImplementation;
import shared.CmdEvent;
import shared.LogEvent;



public class PluginEngine {

	private PluginConfig config;
	private ConcurrentLinkedQueue<LogEvent> logQueue;
	private String pluginName;
	private String pluginSlot;
	
	public PluginEngine()
	{
		//change this to the name of your plugin
		pluginName="DummyPlugin";	
	}
	public String getName()
	{
		   return pluginName; 
	}
	public String getVersion()
    {
		   String version;
		   try{
		   String jarFile = PluginImplementation.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		   File file = new File(jarFile.substring(5, (jarFile.length() -2)));
           FileInputStream fis = new FileInputStream(file);
           @SuppressWarnings("resource")
		   JarInputStream jarStream = new JarInputStream(fis);
		   Manifest mf = jarStream.getManifest();
		   
		   Attributes mainAttribs = mf.getMainAttributes();
           version = mainAttribs.getValue("Implementation-Version");
		   }
		   catch(Exception ex)
		   {
			   String msg = "Unable to determine Plugin Version " + ex.toString();
			   System.err.println(msg);
			   logQueue.offer(new LogEvent("ERROR",pluginSlot,msg));
			   version = "Unable to determine Version";
		   }
		   
		   return pluginName + "." + version;
	   }
	public boolean initialize(ConcurrentLinkedQueue<LogEvent> logQueue, SubnodeConfiguration configObj, String pluginSlot)  
	{
		this.logQueue = logQueue;
		this.pluginSlot = pluginSlot;
		
		try{
			this.config = new PluginConfig(configObj);
			
			String msg = "Initializing Plugin: " + getVersion();
			System.err.println(msg);
			logQueue.offer(new LogEvent("INFO",pluginSlot,msg));
			
			WatchDog wd = new WatchDog(logQueue,config,pluginSlot);
			
			return true;
		}
		catch(Exception ex)
		{
			String msg = "ERROR IN PLUGIN: " + ex.toString();
			System.err.println(msg);
			logQueue.offer(new LogEvent("ERROR",pluginSlot,msg));
			return false;
		}
		
	}
	
	public CmdEvent executeCommand(CmdEvent ce)
	{
		if(ce.getCmdType().equals("discover"))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("help\n");
			sb.append("show\n");
			sb.append("show_name\n");
			sb.append("show_version\n");
			ce.setCmdResult(sb.toString());
		}
		else if(ce.getCmdArg().equals("show") || ce.getCmdArg().equals("help"))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("\nPlugin " + getName() + " Help\n");
			sb.append("-\n");
			sb.append("show\t\t\t\t\t Shows Commands\n");
			sb.append("show name\t\t\t\t Shows Plugin Name\n");
			sb.append("show version\t\t\t\t Shows Plugin Version");
			ce.setCmdResult(sb.toString());
		}
		else if(ce.getCmdArg().equals("show_version"))
		{
			ce.setCmdResult(getVersion());
		}
		else if(ce.getCmdArg().equals("show_name"))
		{
			ce.setCmdResult(getName());
		}
		else
		{
			ce.setCmdResult("Plugin Command [" + ce.getCmdType() + "] unknown");
		}
		return ce;
	}
		
}
