package me.minutz.l2m.procese;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.cmd.cmds.ProcessCommand;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.events.PageLoadEvent;
import me.minutz.l2m.procese.events.PageLoadEvent.PageLoadEventResult;
import me.minutz.l2m.procese.ha.Proces;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.Aplicatie;
import me.minutz.l2m.site.Aplicatie.A_STATUS;
import me.minutz.l2m.site.Chestie;
import me.minutz.l2m.site.users.TUser;
import me.minutz.l2m.site.users.User;

public class CheckUserProces extends Proces{

	public CheckUserProces() {
		super(ProcessType.CHECK_USER_DATA);
	}

	@Override
	public JSONObject execute(String[] args) throws JSONException{
    	String uuid = args[0];
    	String input = args[1];
    	String pass = args[2];
			// String ip = args[3];
		TUser tuser = UsersConfig.getTUserByUUID(uuid);
		JSONObject raspuns = new JSONObject();
    	if(tuser!=null) {
    		if(tuser.getUser().getNume().equals(input)) {
    			PageLoadEvent e = new PageLoadEvent(args,PageLoadEventResult.SUCCES_TUSER,tuser);
    			L2MSystem.el.onPageLoadEvent(e);
    			if(e.isCancelled()) {
						raspuns.accumulate("tuser", true);
						raspuns.accumulate("status", false);
						raspuns.accumulate("mesaj", e.getCancelMessage());
    				return raspuns;
					}
					raspuns.accumulate("tuser", true);
					raspuns.accumulate("status", true);
					raspuns.accumulate("mesaj", e.getResponse());
    			return raspuns;
    		}else {
    			L2MSystem.el.onPageLoadEvent(new PageLoadEvent(args,PageLoadEventResult.FAIL_TUSER_COOKIE_PROBLEM,tuser));
					raspuns.accumulate("tuser", true);
					raspuns.accumulate("status", false);
					raspuns.accumulate("mesaj", "A aparut o eroare neasteptata :O 3");
					return raspuns;
    		}
    	}
    	User user = UsersConfig.getUserByUUID(uuid);
    	if(user!=null) {
	    	if(user.getNume().equals(input)&&user.getParola().equals(pass)) {
	    		user.updateLastActivity(true);
	    		PageLoadEvent e = new PageLoadEvent(args,PageLoadEventResult.SUCCES_USER,user);
	    		L2MSystem.el.onPageLoadEvent(e);
    			if(e.isCancelled()) {
						raspuns.accumulate("tuser", false);
						raspuns.accumulate("status", false);
						raspuns.accumulate("mesaj", e.getCancelMessage());
    				return raspuns;
    			}
    			raspuns.accumulate("tuser", false);
				raspuns.accumulate("status", true);
				raspuns.accumulate("permlvl", user.getGrad().getImportanta());
				Aplicatie a = user.getAplicatie();
				if(a != null) {
					if(a.status == A_STATUS.PENDING_USER) {
						new Chestie("Am inregistrat o cerere care, din pacate a fost refuzata, in schimb poti deveni \""+ProcessCommand.getG(a.grad).getNume()+"\". Nu uita ca rolurile nu sunt fixe, poti participa oricand la actiuni in cadrul echipei care nu includ lucruri specifice rolului tau.","Accepta","Refuza",user.getUUID()) {
							@Override
							public void onAccept() {
								User u = UsersConfig.getUserByUUID(userUUID);
								Aplicatie ap = u.getAplicatie();
								if(u.getGrad().getImportanta()<ProcessCommand.getG(a.grad).getImportanta()) {
									u.setGrad(ProcessCommand.getG(a.grad), false);
								}
								ap.status = A_STATUS.ACCEPTAT;
								u.setAplicatie(ap.toJSON().toString(), false);
								u.save();
							}

							@Override
							public void onRefuz() {
								User u = UsersConfig.getUserByUUID(userUUID);
								Aplicatie ap = u.getAplicatie();
								ap.status = A_STATUS.RESPINS;
								u.setAplicatie(ap.toJSON().toString(), true);
							}
							
						};
						raspuns.accumulate("pending", true);
						raspuns.accumulate("chestie", Chestie.getChestieByUser(user.getUUID()).toJSON().toString());
					}else {
						raspuns.accumulate("pending", false);
					}
				}else {
					raspuns.accumulate("pending", false);
				}
				raspuns.accumulate("mesaj", e.getResponse());
	    		return raspuns;
	    	}else {
	    		L2MSystem.el.onPageLoadEvent(new PageLoadEvent(args,PageLoadEventResult.FAIL_USER_COOKIE_PROBLEM,user));
					raspuns.accumulate("tuser", false);
					raspuns.accumulate("status", false);
					raspuns.accumulate("mesaj", "A aparut o eroare neasteptata :O 2");
					return raspuns;
	    	}
    	}else {
    		L2MSystem.el.onPageLoadEvent(new PageLoadEvent(args,PageLoadEventResult.FAIL_USER_NOT_FOUND,user));
    		raspuns.accumulate("tuser", false);
				raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", "A aparut o eroare neasteptata :O 1");
				return raspuns;
    	}
	}

}
