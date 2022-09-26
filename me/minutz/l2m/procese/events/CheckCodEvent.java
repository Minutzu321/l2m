package me.minutz.l2m.procese.events;

import me.minutz.l2m.procese.events.ha.Event;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.TUser;

public class CheckCodEvent extends Event{
	
	private TUser tuser;
	private CheckCodResult cc;
	private String newcode;
	
	public enum CheckCodResult {
		SUCCES,
		FAIL_NOT_FOUND,
		FAIL_WRONG_CODE,
		FAIL_OUT_OF_CODES;
	}

	public CheckCodEvent(String[] args, TUser tuser, CheckCodResult cc, String newcode) {
		super(ProcessType.CHECK_COD, args);
		this.cc = cc;
		this.newcode = newcode;
	}

	public TUser getTUser() {
		return tuser;
	}

	public String getUUID() {
		return args[0];
	}

	public String getCod() {
		return args[1];
	}

	public String getIp() {
		return args[2];
	}
	
	public String getNewcode() {
		return newcode;
	}
	
	public CheckCodResult getCheckCodResult() {
		return cc;
	}
}
