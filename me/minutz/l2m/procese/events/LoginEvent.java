package me.minutz.l2m.procese.events;

import me.minutz.l2m.procese.events.ha.Cancellable;
import me.minutz.l2m.procese.events.ha.Event;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.User;

public class LoginEvent extends Event implements Cancellable{
	
	private boolean cancel = false;
	private String cancelMessage;
	
	public enum LoginResult{
		FAIL_USER_NOT_FOUND,
		FAIL_WRONG_PASSWORD,
		FAIL_INVALID_USERNAME,
		FAIL_ACCOUNT_BANNED,
		SUCCES_USER,
		SUCCES_TUSER;
	}
	
	private LoginResult lr;
	private User user;

	public LoginEvent(String[] args, LoginResult lr, User user) {
		super(ProcessType.LOGIN, args);
		this.lr = lr;
		this.user = user;
	}
	
	/*Input can be Username or Email*/
	public String getInput() {
		return args[0];
	}
	
	public String getPassword() {
		return args[1];
	}
	
	public String getIP() {
		return args[2];
	}
	
	public User getUser() {
		return user;
	}
	
	public LoginResult getLoginResult() {
		return lr;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public String getCancelMessage() {
		return cancelMessage;
	}

	@Override
	public void cancel(String message) {
		cancel = true;
		cancelMessage = message;
	}

	@Override
	public void setCancelled(boolean cancel, String cancelMessage) {
		this.cancel = cancel;
		this.cancelMessage = cancelMessage;
	}
}
