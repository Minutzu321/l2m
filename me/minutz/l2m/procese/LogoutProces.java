package me.minutz.l2m.procese;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.events.LogoutEvent;
import me.minutz.l2m.procese.ha.Proces;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.User;

public class LogoutProces extends Proces{

	public LogoutProces() {
		super(ProcessType.LOGOUT);
	}

	@Override
	public JSONObject execute(String[] args) throws JSONException{
		int tip = Integer.parseInt(args[0]);
		String input = args[1];
		User user = null;
		JSONObject raspuns = new JSONObject();
		if(tip == 0) {
			user = UsersConfig.getUserByUUID(input);
		}
		if(tip == 1) {
			user = UsersConfig.getUserByNume(input,false);
		}
		if(tip == 2) {
			user = UsersConfig.getUserByEmail(input,false);
		}
//		if(tip == 3) {
//			//parola
//		}
		L2MSystem.el.onLogoutEvent(new LogoutEvent(args,user));
		raspuns.accumulate("status", true);
		return raspuns;
	}
}
