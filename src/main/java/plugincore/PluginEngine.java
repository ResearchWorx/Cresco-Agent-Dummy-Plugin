package plugincore;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import org.apache.commons.configuration.SubnodeConfiguration;

import plugin.PluginImplementation;
import channels.LogEvent;



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
		
		logQueue.offer(new LogEvent("INFO",pluginName,"Initializing Plugin"));
		
		WatchDog wd = new WatchDog(logQueue,config);
		
		
		logQueue.offer(new LogEvent("INFO",pluginName,"Watchdog Set"));
		
		/*
		Iterator it = config.getKeys();
		while (it.hasNext()) {
			Object key = it.next();
			String value = config.getString(key.toString());
			//result.put(key.toString(), value);
			System.out.println(key.toString() + " " + value);
		}
		*/
		return true;
	}
	public String getCommandSet()
    {
		return "Dummy Plugin is a Dummy";
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
