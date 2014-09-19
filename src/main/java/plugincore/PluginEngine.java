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
	
	public PluginEngine()
	{
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
			   logQueue.offer(new LogEvent("ERROR",pluginName,msg));
			   version = "Unable to determine Version";
		   }
		   
		   return pluginName + "." + version;
	   }
	public boolean initialize(ConcurrentLinkedQueue<LogEvent> logQueue, SubnodeConfiguration configObj)  
	{
		this.logQueue = logQueue;
		try{
			this.config = new PluginConfig(configObj);
			
			String msg = "Initializing Plugin: " + getVersion();
			System.err.println(msg);
			logQueue.offer(new LogEvent("INFO",pluginName,msg));
			
			WatchDog wd = new WatchDog(logQueue,config);
			
			return true;
		}
		catch(Exception ex)
		{
			String msg = "ERROR IN PLUGIN: " + ex.toString();
			System.err.println(msg);
			logQueue.offer(new LogEvent("ERROR",pluginName,msg));
			return false;
		}
		
	}
	public String getCommandSet()
    {
		StringBuilder commands = new StringBuilder();
		commands.append("Plugin: " + pluginName + " commands\n\n");
		commands.append("echo\t\tEcho's Input String\n");
		return commands.toString();
    }
	public CmdEvent executeCommand(CmdEvent ce)
	{
		if(ce.getCmdType().equals("discover"))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("show\n");
			sb.append("show_name\n");
			sb.append("show_version\n");
			ce.setCmdResult(sb.toString());
		}
		else if(ce.getCmdArg().equals("show"))
		{
			StringBuilder commands = new StringBuilder();		
			commands.append("show\t\t Shows Commands\n");
			commands.append("show name\t\t Shows Plugin Name\n");
			commands.append("show version\t\t Shows Plugin Version");
			ce.setCmdResult(commands.toString());
		}
		else if(ce.getCmdArg().equals("show_version"))
		{
			ce.setCmdResult(getVersion());
		}
		else if(ce.getCmdArg().equals("show_name"))
		{
			ce.setCmdResult(getVersion());
		}
		else
		{
			ce.setCmdResult("Plugin Command [" + ce.getCmdType() + "] unknown");
		}
	    
		return ce;
	}
		
}
