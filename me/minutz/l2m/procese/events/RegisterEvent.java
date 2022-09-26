package me.minutz.l2m.procese.events;

import me.minutz.l2m.procese.events.ha.Cancellable;
import me.minutz.l2m.procese.events.ha.Event;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.TUser;

public class RegisterEvent extends Event implements Cancellable{
	
	public enum RegisterResult {
		FAIL_INVALID_USERNAME,
		FAIL_INVALID_EMAIL,
		FAIL_EMAIL_ALREADY_REGISTERED,
		FAIL_INVALID_PASSWORD,
		FAIL_ALREADY_WAITING_FOR_ACTIVATION,
		FAIL_NAME_ALREADY_REGISTERED,
		SUCCES;
	}
	
	private boolean cancel = false;
	private String cancelMessage;
	
	private RegisterResult rr;
	private TUser tuser;
	
	public RegisterEvent(String[] args, RegisterResult rr, TUser tuser) {
		super(ProcessType.REGISTER, args);
		this.rr = rr;
		this.tuser = tuser;
	}

	public RegisterResult getRegisterResult() {
		return rr;
	}
	
	public String getName() {
		return args[0];
	}
	public String getEmail() {
		return args[1];
	}
	public String getPassword() {
		return args[2];
	}
	public String getIP() {
		return args[3];
	}
	
	public TUser getTUser() {
		return tuser;
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
