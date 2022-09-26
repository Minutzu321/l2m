package me.minutz.l2m.android.robot;

import me.minutz.l2m.android.AConn;
import me.minutz.l2m.android.AndroidListener;

public class Robot{
	
	public enum Status{
	    NEINITIALIZAT,
	    INITIALIZAT,
	    PORNIT,
	    OPRIT;
	}
	
	public static String data="In asteptare",config;
	
	public static Status getStatusFromString(String s) {
		for(Status st:Status.values()) {
			if(st.toString().equalsIgnoreCase(s)) {
				return st;
			}
		}
		return Status.NEINITIALIZAT;
	}

	public static boolean updateRobotData(String jdata){
		for(AConn c : AndroidListener.conns){
		  if(c.eRobot){
		    c.send(jdata);
		    return true;
		  }
		}
	return false;
	}
}