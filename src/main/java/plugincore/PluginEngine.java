package plugincore;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import org.apache.commons.configuration.SubnodeConfiguration;

import plugin.PluginImplementation;
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
			   System.out.println(ex);
			   version = "Unable to determine Version";
		   }
		   
		   return pluginName + "." + version;
	   }
	public boolean initialize(ConcurrentLinkedQueue<LogEvent> logQueue, SubnodeConfiguration configObj)  
	{
		this.logQueue = logQueue;
		try{
		this.config = new PluginConfig(configObj);
		}
		catch(Exception ex)
		{
			System.out.println("ERROR IN PLUGIN: " + ex.toString());
		}
		
		logQueue.offer(new LogEvent("INFO",pluginName,"Initializing Plugin: " + getVersion()));
	
		WatchDog wd = new WatchDog(logQueue,config);
		
		return true;
	}
	public String getCommandSet()
    {
		return "echo: echos an input string";
    }
	public String executeCommand(String command)
	{
		return command;
	}
	
}
