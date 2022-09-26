package me.minutz.l2m.procese.events;

import me.minutz.l2m.procese.events.ha.Cancellable;
import me.minutz.l2m.procese.events.ha.Event;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.TUser;
import me.minutz.l2m.site.users.User;

public class PageLoadEvent extends Event implements Cancellable{
	
	public enum PageLoadEventResult{
		FAIL_USER_NOT_FOUND,
		FAIL_USER_COOKIE_PROBLEM,
		FAIL_TUSER_COOKIE_PROBLEM,
		SUCCES_USER,
		SUCCES_TUSER;
	}
	
	private boolean cancel = false;
	private String cancelMessage,response="";
	
	private PageLoadEventResult cr;
	private TUser tuser;
	private User user;

	public PageLoadEvent(String[] args, PageLoadEventResult cr, TUser tuser) {
		super(ProcessType.CHECK_USER_DATA, args);
		this.cr = cr;
		this.tuser = tuser;
	}
	
	public PageLoadEvent(String[] args, PageLoadEventResult cr, User user) {
		super(ProcessType.CHECK_USER_DATA, args);
		this.cr = cr;
		this.user = user;
	}
	
	public String getUUID() {
		return args[0];
	}
	
	public String getInput() {
		return args[1];
	}
	
	public String getPassword() {
		return args[2];
	}
	
	public String getIP() {
		return args[3];
	}

	public String getPage(){
		return args[4];
	}

	public PageLoadEventResult getPageLoadResult() {
		return cr;
	}

	public TUser getTUser() {
		return tuser;
	}

	public User getUser() {
		return user;
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
	
	public void setResponse(String resp) {
		response = resp;
	}

	public String getResponse() {
		return response;
	}
	
	

}
