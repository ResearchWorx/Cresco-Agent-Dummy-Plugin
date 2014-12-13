package plugincore;


import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import org.apache.commons.configuration.SubnodeConfiguration;

import dummyserv.DummyServerEngine;
import shared.Clogger;
import shared.MsgEvent;
import shared.PluginImplementation;



public class PluginEngine {

	public static PluginConfig config;
	
	public static String pluginName;
	public static String pluginVersion;
	public static String plugin;
	public static String agent;
	public static String region;
	
	public static CommandExec commandExec;
	
	public static ConcurrentMap<String,MsgEvent> rpcMap;
	
	public static ConcurrentLinkedQueue<MsgEvent> logOutQueue;
	
	
	public static Clogger clog;

	public static ConcurrentLinkedQueue<MsgEvent> msgInQueue;
	
	public PluginEngine()
	{
		pluginName="DummyPlugin";
	}
	public void shutdown()
	{
		System.out.println("Implement Shutdown in Plugin");
	}
	public String getName()
	{
		   return pluginName; 
	}
	public String getVersion() //This should pull the version information from jar Meta data
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
			   clog.error(msg);
			   version = "Unable to determine Version";
		   }
		   
		   return pluginName + "." + version;
	   }
	//steps to init the plugin
	public boolean initialize(ConcurrentLinkedQueue<MsgEvent> msgOutQueue,ConcurrentLinkedQueue<MsgEvent> msgInQueue, SubnodeConfiguration configObj, String region,String agent, String plugin)  
	{
		
		commandExec = new CommandExec();
		rpcMap = new ConcurrentHashMap<String,MsgEvent>();
		
		//this.msgOutQueue = msgOutQueue; //send directly to log queue
		this.msgInQueue = msgInQueue; //messages to agent should go here
		
		this.agent = agent;
		this.plugin = plugin;
		
		this.region = region;
		try{
			
			if(msgInQueue == null)
			{
				System.out.println("MsgInQueue==null");
				return false;
			}
			
			this.config = new PluginConfig(configObj);
			
			//create logger
			clog = new Clogger(msgInQueue,region,agent,plugin); //send logs directly to outqueue
			
			String startmsg = "Initializing Plugin: Region=" + region + " Agent=" + agent + " plugin=" + plugin + " version" + getVersion();
			clog.log(startmsg);
			
			
	    	try
	    	{
	    		System.out.println("Starting Dummy Service");
				DummyServerEngine dummyEngine = new DummyServerEngine();
				Thread dummyServerThread = new Thread(dummyEngine);
		        dummyServerThread.start();		    
	    	}
	    	catch(Exception ex)
	    	{
	    		System.out.println("Unable to Start HTTP Service : " + ex.toString());
	    	}
	    	
	    	/*
	    	AMPQLogProducer v = new AMPQLogProducer();
	    	ProducerThread = new Thread(v);
	    	ProducerThread.start();
	    	while(!ProducerEnabled)
	    	{
	    		Thread.sleep(1000);
	    		String msg = "Waiting for AMPQProducer Initialization : Region=" + region + " Agent=" + agent + " plugin=" + plugin;
	    		clog.log(msg);
	    	}
	    	*/
	    	
	    	
	    	WatchDog wd = new WatchDog();
			
    		return true;
    		
		
		}
		catch(Exception ex)
		{
			String msg = "ERROR IN PLUGIN: : Region=" + region + " Agent=" + agent + " plugin=" + plugin + " " + ex.toString();
			clog.error(msg);
			return false;
		}
		
	}
	
	public void msgIn(MsgEvent me)
	{
		
		final MsgEvent ce = me;
		try
		{
		Thread thread = new Thread(){
		    public void run(){
		
		    	try 
		        {
					MsgEvent re = commandExec.cmdExec(ce);
					if(re != null)
					{
						re.setReturn(); //reverse to-from for return
						msgInQueue.offer(re); //send message back to queue
					}
					
				} 
		        catch(Exception ex)
		        {
		        	System.out.println("Controller : PluginEngine : msgIn Thread: " + ex.toString());
		        }
		    }
		  };
		  thread.start();
		}
		catch(Exception ex)
		{
			System.out.println("Controller : PluginEngine : msgIn Thread: " + ex.toString());        	
		}
		
	}
		
		
}
