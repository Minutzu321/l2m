package me.minutz.l2m.procese;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.events.RegisterEvent;
import me.minutz.l2m.procese.events.RegisterEvent.RegisterResult;
import me.minutz.l2m.procese.ha.Proces;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.IdDat;
import me.minutz.l2m.site.users.TUser;
import me.minutz.l2m.site.users.User;
import me.minutz.l2m.util.DateUtil;
import me.minutz.l2m.util.GoogleMail;
import me.minutz.l2m.util.LogRegUtil;

public class RegisterProces extends Proces{

	public RegisterProces() {
		super(ProcessType.REGISTER);
	}
	
	public JSONObject checkTUser(String[] args, TUser tuser) throws JSONException{
		JSONObject raspuns = new JSONObject();
		String unit = " minute";
		long expiren = L2MSystem.emailActivationExpireInMinutes*60 - DateUtil.getElapsedTime(tuser.getUser().getIdlist().get(0).getData(), TimeUnit.SECONDS);
		if(expiren>=60&&expiren<120) {
			unit=" minut";
		}
		if(expiren<60) {
			unit = " secunde";
    		if(expiren==1) {
    			unit=" secunda";
    		}
    		L2MSystem.el.onRegisterEvent(new RegisterEvent(args,RegisterResult.FAIL_ALREADY_WAITING_FOR_ACTIVATION,tuser));
				raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", "Acest cont deja asteapta sa fie activat (Va expira in "+expiren+unit+")");
    		return raspuns;
		}
		L2MSystem.el.onRegisterEvent(new RegisterEvent(args,RegisterResult.FAIL_ALREADY_WAITING_FOR_ACTIVATION,tuser));
		raspuns.accumulate("status", false);
		raspuns.accumulate("mesaj", "Acest cont deja asteapta sa fie activat (Va expira in "+TimeUnit.MINUTES.convert(expiren, TimeUnit.SECONDS)+unit+")");
    return raspuns;
	}

	@Override
	public JSONObject execute(String[] args) throws JSONException{
			String nume = args[0];
			JSONObject raspuns = new JSONObject();
    	if(nume.contains("@")) {
				L2MSystem.el.onRegisterEvent(new RegisterEvent(args,RegisterResult.FAIL_INVALID_USERNAME,null));
				raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", "Nume invalid");
    		return raspuns;
    	}
    	if(nume.length()<3) {
				L2MSystem.el.onRegisterEvent(new RegisterEvent(args,RegisterResult.FAIL_INVALID_USERNAME,null));
				raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", "Nume prea scurt (Minim 3 caractere)");
    		return raspuns;
    	}
    	if(nume.length()>16) {
				L2MSystem.el.onRegisterEvent(new RegisterEvent(args,RegisterResult.FAIL_INVALID_USERNAME,null));
				raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", "Nume prea lung (Maxim 16 caractere)");
    		return raspuns;
    	}
    	String email = args[1];
    	if(!email.contains("@")) {
    		L2MSystem.el.onRegisterEvent(new RegisterEvent(args,RegisterResult.FAIL_INVALID_EMAIL,null));
    		raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", "Email invalid");
    		return raspuns;
    	}
    	if(UsersConfig.getUserByEmail(email,false)!=null) {
    		L2MSystem.el.onRegisterEvent(new RegisterEvent(args,RegisterResult.FAIL_EMAIL_ALREADY_REGISTERED,null));
    		raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", "Emailul este deja folosit");
    		return raspuns;
    	}
    	
    	
    	String parola = args[2];
    	if(parola.length()<8) {
				L2MSystem.el.onRegisterEvent(new RegisterEvent(args,RegisterResult.FAIL_INVALID_PASSWORD,null));
				raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", "Parola prea scurta (Minim 8 caractere)");
    		return raspuns;
    	}
    	if(parola.length()>30) {
				L2MSystem.el.onRegisterEvent(new RegisterEvent(args,RegisterResult.FAIL_INVALID_PASSWORD,null));
				raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", "Parola prea lunga (Maxim 30 caractere)");
    		return raspuns;
    	}
    	
    	TUser tuser = null;
    	tuser = UsersConfig.getTUserByNume(nume);
    	if(tuser!=null) {
    		if(!LogRegUtil.expiredTUser(tuser)) {
    			return checkTUser(args,tuser);
    		}else{
    			UsersConfig.tusers.remove(tuser);
    		}
    	}else {
	    	tuser = UsersConfig.getTUserByEmail(email);
	    	if(tuser!=null) {
	    		if(!LogRegUtil.expiredTUser(tuser)) {
	    			return checkTUser(args,tuser);
	    		}else{
	    			UsersConfig.tusers.remove(tuser);
	    		}
	    	}
    	}
    			
    	String ip = args[3];
    	if(UsersConfig.getUserByNume(nume,true)!=null)
    	{
				L2MSystem.el.onRegisterEvent(new RegisterEvent(args,RegisterResult.FAIL_NAME_ALREADY_REGISTERED,null));
				raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", "Numele este deja folosit");
    		return raspuns;
    	}
    	
		Random r = new Random();
		String cod = "";
		for(int i=0;i<8;i++){
			cod=cod+r.nextInt(10);
		}
		List<IdDat> idd = new ArrayList<IdDat>();
		idd.add(new IdDat(ip,Calendar.getInstance().getTime(),1));
    	User u = new User(nume,email,parola,UsersConfig.getRUUID(),idd,false,Grad.USER,Calendar.getInstance().getTime());
    	TUser tu = new TUser(u,cod,Calendar.getInstance().getTime(),0);
    	UsersConfig.tusers.add(tu);
    	GoogleMail.sendAuthMail(cod, email);
    	RegisterEvent e = new RegisterEvent(args,RegisterResult.SUCCES,tu);
    	L2MSystem.el.onRegisterEvent(e);
    	if(e.isCancelled()) {
				raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", e.getCancelMessage());
    		return raspuns;
			}
			raspuns.accumulate("status", true);
			raspuns.accumulate("uuid", u.getUUID());
			raspuns.accumulate("session", L2MSystem.emailActivationExpireInMinutes*60);
    	return raspuns;
    }

}
