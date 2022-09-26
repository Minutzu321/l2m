package me.minutz.l2m;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import me.minutz.l2m.cmd.Consola;
import me.minutz.l2m.config.Configuratie;
import me.minutz.l2m.config.Configuratie.ConfTip;
import me.minutz.l2m.config.post.PostConfig;
import me.minutz.l2m.config.program.ProgramConfig;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.events.ha.EventListener;
import me.minutz.l2m.servers.AndroidServer;
import me.minutz.l2m.servers.ServerRunner;
import me.minutz.l2m.servers.WebServer;
import me.minutz.l2m.util.DateUtil;

public class L2MSystem {
	
	public static boolean DEBUG = true;
	public static String parola = "qmamrefsronbfgsw", key = "mogxtuhspfdc8300" , iv="qalfdslabdsulabs", configPath="/home/pi/database";
	public static int emailActivationExpireInMinutes = 5,sesiuneaCookieurilor = 86400 * 30 * 6;
	public static ServerRunner webrun,androidrun;
	public static EventListener el;
	public static List<Configuratie> configuratii = new ArrayList<Configuratie>();
	public static Logger logger = Logger.getLogger("BBLog");  
	
	public static Configuratie getConfig(ConfTip ct) {
		for(Configuratie c : configuratii) {
			if(c.tip == ct) {
				return c;
			}
		}
		return null;
	}
	
	public L2MSystem(int webPort,int androidPort) throws IOException {
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss");
		File fd = new File(configPath+"/logs");
		if(!fd.exists()) {
			fd.mkdirs();
		}
		for (final File fileEntry : fd.listFiles()) {
			String data = fileEntry.getName().replaceAll(".log", "");
			try {
				if(DateUtil.getElapsedTime(df.parse(data), TimeUnit.DAYS)>7) {
					fileEntry.delete();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		File f = new File(configPath+"/logs/"+df.format(Calendar.getInstance().getTime())+".log");
		f.createNewFile();
	    FileHandler fh;  
	    try {  
	        fh = new FileHandler(f.getPath());  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
		System.setOut(new PrintStream(System.out) {
			  public void println(String s) {
			    logger.info(s);
			    super.println(s);
			  }
			});
	    logger.info("Sistemul de log-uri s-a initializat");  
		
		el = new EventHandler();
		System.out.println("Se incarca baza de date...");
		configuratii.add(new PostConfig());
		configuratii.add(new UsersConfig());
		ProgramConfig.load();
		System.out.println("Baza de date a fost incarcata");
		
		System.out.println("Se deschid serverele...");
		WebServer webserver = new WebServer(webPort);
		webserver.start();
		webrun = new ServerRunner(webserver);
		AndroidServer androidserver = new AndroidServer(androidPort);
		androidserver.start();
		androidrun = new ServerRunner(androidserver);
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new Consola(), 1, 1, TimeUnit.MILLISECONDS);
		System.out.println("BIG BROTHER s-a initializat");
	}

}
