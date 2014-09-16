package plugin;

import java.io.File;
import java.io.FileInputStream;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import org.apache.commons.configuration.SubnodeConfiguration;

import plugincore.PluginEngine;
import plugins.PluginInterface;


public class PluginImplementation implements PluginInterface {

	public PluginEngine pe;
	public String pluginName;
	
	public PluginImplementation()
	{
		pe = new PluginEngine();
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
	   
	public String getCommandSet()
    {
		return "Dummy Plugin does nothing";
	}
	   
	public boolean initialize(SubnodeConfiguration config) 
	{
	   return ((PluginEngine) pe).initialize(config);
	   
    }	
}

