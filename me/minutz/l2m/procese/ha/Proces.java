package me.minutz.l2m.procese.ha;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Proces {
	
	private String nume,cmd;
	
	public Proces(ProcessType type) {
		nume = type.getNume();
		cmd = type.getCommand();
	}
	
	public Proces(String name, String cmd) {
		this.nume = name;
		this.cmd = cmd;
	}
	
	public abstract JSONObject execute(String[] args) throws JSONException;

	public String getNume() {
		return nume;
	}
	
	public String getComanda() {
		return cmd;
	}

}
