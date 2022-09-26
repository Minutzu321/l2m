package me.minutz.l2m.procese.ha;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.procese.AplicaProces;
import me.minutz.l2m.procese.CheckCodProces;
import me.minutz.l2m.procese.CheckUserProces;
import me.minutz.l2m.procese.GoogleProces;
import me.minutz.l2m.procese.LoginProces;
import me.minutz.l2m.procese.LogoutProces;
import me.minutz.l2m.procese.PingProces;
import me.minutz.l2m.procese.RegisterProces;
import me.minutz.l2m.procese.RequestCommandProces;
import me.minutz.l2m.procese.RequestDataProces;

public class ProcesHandler {
	
	private static boolean initiat = false;
	

	private static List<Proces> procese = new ArrayList<Proces>();
	
	private static void init() {
		if(!initiat) {
			
			procese.add(new LoginProces());
			procese.add(new RegisterProces());
			procese.add(new CheckUserProces());
			procese.add(new CheckCodProces());
			procese.add(new LogoutProces());
			procese.add(new RequestDataProces());
			procese.add(new RequestCommandProces());
			procese.add(new PingProces());
			procese.add(new GoogleProces());
			procese.add(new AplicaProces());
			
			initiat = true;
		}
	}
	
	public static void addProcess(Proces process) {
		procese.add(process);
	}
	
	public static String executeProces(String cmd, String[] args) {
		init();
		for(Proces p : procese) {
			if(p.getComanda().equalsIgnoreCase(cmd)) {
				JSONObject r;
				try{
					r = p.execute(args);
					return r.toString();
				}catch(JSONException e){
					L2MSystem.logger.severe(e.toString());
					return null;
				}
			}
		}
		return null;
	}

}
