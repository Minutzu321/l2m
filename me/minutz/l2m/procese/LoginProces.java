package me.minutz.l2m.procese;

import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.events.LoginEvent;
import me.minutz.l2m.procese.events.LoginEvent.LoginResult;
import me.minutz.l2m.procese.ha.Proces;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.TUser;
import me.minutz.l2m.site.users.User;
import me.minutz.l2m.util.DateUtil;
import me.minutz.l2m.util.LogRegUtil;

public class LoginProces extends Proces{

	public LoginProces() {
		super(ProcessType.LOGIN);
	}
	
	private JSONObject login(User user, String parola, String ip, String[] args) throws JSONException{
		JSONObject raspuns = new JSONObject();
    	if(!user.isBanat()) {
	    	if(user.getParola().equals(parola)) {
					LoginEvent e = new LoginEvent(args,LoginResult.SUCCES_USER,user);
    			L2MSystem.el.onLoginEvent(e);
    			if(e.isCancelled()) {
						raspuns.accumulate("status",false);
						raspuns.accumulate("mesaj", e.getCancelMessage());
    				return raspuns;
    			}
					user.addToIdlist(ip,true);
					raspuns.accumulate("status",true);
					raspuns.accumulate("uuid", user.getUUID());
					raspuns.accumulate("session", L2MSystem.sesiuneaCookieurilor);
					raspuns.accumulate("nume", user.getNume());
					raspuns.accumulate("email", user.getEmail());
    			return raspuns;
	    	}else {
					L2MSystem.el.onLoginEvent(new LoginEvent(args,LoginResult.FAIL_WRONG_PASSWORD,user));
					raspuns.accumulate("status",false);
					raspuns.accumulate("mesaj", "Parola incorecta");
    			return raspuns;
	    	}
    	}else {
				L2MSystem.el.onLoginEvent(new LoginEvent(args,LoginResult.FAIL_ACCOUNT_BANNED,user));
				raspuns.accumulate("status",false);
				raspuns.accumulate("mesaj", "Contul acesta nu mai poate fi folosit deoarece a incalcat conditiile site-ului");
    		return raspuns;
    	}
	}

	@Override
	public JSONObject execute(String[] args) throws JSONException{
		
    	String input = args[0];
    	String parola = args[1];
    	String ip = args[2];
    	JSONObject raspuns = new JSONObject();
    	if(!input.contains("@")) {
		    if(input.length()<3) {
					L2MSystem.el.onLoginEvent(new LoginEvent(args,LoginResult.FAIL_INVALID_USERNAME,null));
					raspuns.accumulate("status",false);
					raspuns.accumulate("mesaj", "Nume invalid(Trebuie sa aiba minim 3 caractere)");
    			return raspuns;
		    }
		    if(input.length()>16) {
		    	L2MSystem.el.onLoginEvent(new LoginEvent(args,LoginResult.FAIL_INVALID_USERNAME,null));
		    	raspuns.accumulate("status",false);
					raspuns.accumulate("mesaj", "Nume invalid(Trebuie sa aiba maxim 16 caractere)");
    			return raspuns;
		    }
		    
    		TUser tuser = UsersConfig.getTUserByNume(input);
    		if(tuser!=null) {
    			if(!LogRegUtil.expiredTUser(tuser)) {
	    			if(tuser.getUser().getParola().equals(parola)) {
	    				LoginEvent e = new LoginEvent(args,LoginResult.SUCCES_TUSER,tuser.getUser());
	    				L2MSystem.el.onLoginEvent(e);
	    				if(e.isCancelled()) {
	    					raspuns.accumulate("status",false);
								raspuns.accumulate("mesaj", e.getCancelMessage());
    						return raspuns;
							}
							raspuns.accumulate("status",true);
							raspuns.accumulate("uuid", tuser.getUser().getUUID());
							raspuns.accumulate("session", L2MSystem.emailActivationExpireInMinutes*60 - DateUtil.getElapsedTime(tuser.getDate(), TimeUnit.SECONDS));
							raspuns.accumulate("nume", tuser.getUser().getNume());
							raspuns.accumulate("email", tuser.getUser().getEmail());
    					return raspuns;
	    			}else {
	    				L2MSystem.el.onLoginEvent(new LoginEvent(args,LoginResult.FAIL_WRONG_PASSWORD,tuser.getUser()));
							raspuns.accumulate("status",false);
							raspuns.accumulate("mesaj", "Parola incorecta");
    					return raspuns;
	    			}
    			}
    		}
		    
		    User user = UsersConfig.getUserByNume(input,true);
		    if(user!=null) {
		    	return login(user,parola,ip,args);
	    	}else {
					L2MSystem.el.onLoginEvent(new LoginEvent(args,LoginResult.FAIL_USER_NOT_FOUND,null));
					raspuns.accumulate("status",false);
					raspuns.accumulate("mesaj", "Userul nu a fost gasit");
    			return raspuns;
	    	}
    	}else{
    		User user = UsersConfig.getUserByEmail(input,true);
	    	if(user!=null) {
	    		return login(user,parola,ip,args);
	    	}else {
	    		L2MSystem.el.onLoginEvent(new LoginEvent(args,LoginResult.FAIL_USER_NOT_FOUND,null));
	    		raspuns.accumulate("status",false);
					raspuns.accumulate("mesaj", "Userul nu a fost gasit");
    			return raspuns;
	    	}
    	}
	}

}
