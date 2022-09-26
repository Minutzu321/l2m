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
import me.minutz.l2m.procese.events.CheckCodEvent;
import me.minutz.l2m.procese.events.CheckCodEvent.CheckCodResult;
import me.minutz.l2m.procese.ha.Proces;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.IdDat;
import me.minutz.l2m.site.users.TUser;
import me.minutz.l2m.util.DateUtil;
import me.minutz.l2m.util.GoogleMail;

public class CheckCodProces extends Proces{

	public CheckCodProces() {
		super(ProcessType.CHECK_COD);
	}

	@Override
	public JSONObject execute(String[] args) throws JSONException{
    	String uuid = args[0];
    	String cod = args[1];
    	String ip = args[2];
			TUser tuser = UsersConfig.getTUserByUUID(uuid);
			JSONObject raspuns = new JSONObject();
    	if(tuser!=null) {
	    	if(tuser.getCod().equals(cod)) {
	    		UsersConfig.tusers.remove(tuser);
	    		UsersConfig.addUser(tuser.getUser());
					L2MSystem.el.onCheckCodEvent(new CheckCodEvent(args,tuser,CheckCodResult.SUCCES,null));
					raspuns.accumulate("status", true);
					raspuns.accumulate("session", L2MSystem.sesiuneaCookieurilor);
	    		return raspuns;
	    	}else if(tuser.getIncercare()<2){
					tuser.addIncercare();
	    		UsersConfig.tusers.remove(tuser);
	    		
				Random r = new Random();
				String cod1 = "";
				for(int i=0;i<8;i++){
					cod1=cod1+r.nextInt(10);
				}
				List<IdDat> idd = new ArrayList<IdDat>();
				idd.add(new IdDat(ip,Calendar.getInstance().getTime(),1));
		    	UsersConfig.tusers.add(new TUser(tuser.getUser(),cod1,tuser.getDate(),tuser.getIncercare()));
		    	GoogleMail.sendAuthMail(cod1, tuser.getUser().getEmail());
					L2MSystem.el.onCheckCodEvent(new CheckCodEvent(args,tuser,CheckCodResult.FAIL_WRONG_CODE,cod1));
					raspuns.accumulate("status", false);
					raspuns.accumulate("session", L2MSystem.emailActivationExpireInMinutes*60 - DateUtil.getElapsedTime(tuser.getDate(), TimeUnit.SECONDS));
					raspuns.accumulate("mesaj", "Codul pe care l-ai introdus este incorect. Un alt cod a fost trimis la "+tuser.getUser().getEmail());
		    	return raspuns;
	    	}else{
					UsersConfig.tusers.remove(tuser);
					L2MSystem.el.onCheckCodEvent(new CheckCodEvent(args,null,CheckCodResult.FAIL_OUT_OF_CODES,"0"));
					raspuns.accumulate("status", false);
					raspuns.accumulate("session", -3600);
					raspuns.accumulate("mesaj", "Ai ramas fara incercari. Contul a fost sters din sistem dar te poti inregistra din nou.");
					return raspuns;
				}
    	}else {
    		L2MSystem.el.onCheckCodEvent(new CheckCodEvent(args,null,CheckCodResult.FAIL_NOT_FOUND,null));
				raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", "home");
				return raspuns;
    	}
	}

}
