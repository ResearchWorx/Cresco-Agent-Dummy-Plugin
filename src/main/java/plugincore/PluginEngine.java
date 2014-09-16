package plugincore;

import java.util.Iterator;

import org.apache.commons.configuration.SubnodeConfiguration;


public class PluginEngine {

	public String pluginName;
	public SubnodeConfiguration config;
	
	
	public boolean initialize(SubnodeConfiguration config) 
	   {
		   
		Iterator it = config.getKeys();
		while (it.hasNext()) {
			Object key = it.next();
			String value = config.getString(key.toString());
			//result.put(key.toString(), value);
			System.out.println(key.toString() + " " + value);
		}
		return true;
		   /*
		   this.config = config;
		   System.out.println("Init completed");
		   return true;
		   */
	   }
	/*
	Iterator it = config.getKeys();
	while (it.hasNext()) {
		Object key = it.next();
		String value = config.getString(key.toString());
		//result.put(key.toString(), value);
		System.out.println(key.toString() + " " + value);
	}
	// TODO Auto-generated method stub
	return false;
}
	*/

	
}
