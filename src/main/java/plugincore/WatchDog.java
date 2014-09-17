package plugincore;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import channels.LogEvent;


public class WatchDog {
	private PluginConfig config;
	private Timer timer;
	public Queue<LogEvent> log;
	private long startTS;
		
	  
	  public WatchDog(Queue<LogEvent> log, PluginConfig config) {
		  this.config = config;
		  startTS = System.currentTimeMillis();
		  this.log = log;
		  timer = new Timer();
	      timer.scheduleAtFixedRate(new WatchDogTask(), 500, config.getWatchDogTimer());
	      
	      LogEvent le = new LogEvent("INFO",config.getPluginName(),"WatchDog timer set to " + config.getWatchDogTimer() + " milliseconds");
		  log.offer(le);
	  }


	class WatchDogTask extends TimerTask {
	    public void run() {
	    	
	    
	    	 long runTime = System.currentTimeMillis() - startTS;
			 LogEvent le = new LogEvent("WATCHDOG",config.getPluginName(),"Plugin Uptime " + String.valueOf(runTime) + "ms");
			 log.offer(le);
	      //timer.cancel(); //Not necessary because we call System.exit
	      //System.exit(0); //Stops the AWT thread (and everything else)
	    
	    }
	  }

}
