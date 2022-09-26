package me.minutz.l2m.procese.events;

import me.minutz.l2m.procese.events.ha.Event;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.User;

public class LogoutEvent extends Event{

	private User user;
	public LogoutEvent(String[] args, User user) {
		super(ProcessType.LOGOUT, args);
		this.user = user;
	}
	
	public int getTip() {
		return Integer.parseInt(args[0]);
	}
	
	public String getInput() {
		return args[1];
	}
	
	public User getUser() {
		return user;
	}

}
