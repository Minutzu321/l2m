package me.minutz.l2m.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.android.robot.Robot;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.ha.ProcesHandler;
import me.minutz.l2m.site.users.Membru;

public class AndroidListener {

  public static List<AConn> conns = new ArrayList<AConn>();

  public static void onMesaj(String rcmd, AConn conn){
    System.out.println("ANROID: "+rcmd);
    try{
      JSONObject o = new JSONObject(rcmd);
      String ccmd = o.getString("cmd");
      String arg = o.getString("arg");
      if(ccmd.equals("show")){
        Robot.data = arg;
      }
      if(ccmd.equals("config")){
        Robot.config = arg; 
      }
      if(ccmd.equals("ping")) {
    	  JSONObject jo = new JSONObject();
		  jo.append("cmd", "ping");
		  conn.send(jo.toString());
      }
      if(ccmd.equals("login")) {
    	  Membru u = UsersConfig.getUserByUUID(arg).getMembru();
    	  JSONObject jo = new JSONObject();
    	  if(u != null) {
    		  jo.append("cmd", "login");
    		  jo.append("succes", true);
    		  jo.append("uuid", arg);
    		  jo.append("nume", u.getNume());
    		  jo.append("email", u.getEmail());
    		  jo.append("grad", u.getGrad().getJSON().toString());
    		  jo.append("imgURL", u.getImage());
    	  }else {
    		  jo.append("cmd", "login");
    		  jo.append("succes", false);
    		  jo.append("mesaj", "Userul nu a fost gasit");
    	  }
    	  conn.send(jo.toString());
      }
      if(ccmd.equals("proces")) {
			JSONObject data = new JSONObject(arg);
			String cmd = data.getString("cmd");
			JSONArray argz = data.getJSONArray("args");
			String[] args = new String[argz.length()];
			for(int i=0;i<argz.length();i++){
				args[i]=String.valueOf(argz.get(i));
			}
			String rez = ProcesHandler.executeProces(cmd, args);
			conn.send(rez);
      }
    }catch(JSONException e){
      e.printStackTrace();
    }
  }

  public static void sendMesaj(String mesaj, AConn conn){
    
  }

  public static void addAConn(AConn aConn){
    conns.add(aConn);
  }

}