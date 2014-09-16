package plugin;

import java.io.File;
import java.io.FileInputStream;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import org.apache.commons.configuration.SubnodeConfiguration;

import plugins.PluginInterface;


public class PluginImplementation implements PluginInterface {

	public String pluginName;
	public SubnodeConfiguration config;
	
	public PluginImplementation()
	{
		pluginName = "dummyPlugin";
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
		   this.config = config;
		   System.out.println("Init completed");
		   return true;
	   }
	   
	
}

