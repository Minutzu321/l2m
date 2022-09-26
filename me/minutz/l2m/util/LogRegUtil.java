package me.minutz.l2m.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.site.users.TUser;

public class LogRegUtil {
	
	public static boolean expiredTUser(TUser tuser) {
		Date da = tuser.getDate();
		long expiren = L2MSystem.emailActivationExpireInMinutes*60 - DateUtil.getElapsedTime(da, TimeUnit.SECONDS);
		if(expiren<=0) {
			return true;
		}
		return false;
	}
	
	

}
