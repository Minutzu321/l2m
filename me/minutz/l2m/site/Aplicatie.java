package me.minutz.l2m.site;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.util.DateUtil;

public class Aplicatie {
	
	public String nume,gnume,email,gemail,clasa,grad,exec = null;
	public A_STATUS status = A_STATUS.PENDING;
	public Date data;
	public boolean elevmoisil,termeni;
	
	public enum A_STATUS{
		PENDING,
		PENDING_USER,
		ACCEPTAT,
		RESPINS
	}
	
	public Aplicatie(String j) {
		try {
			JSONObject o = new JSONObject(j);
			this.nume = o.getString("nume");
			this.gnume = o.getString("gnume");
			this.email = o.getString("email");
			this.gemail = o.getString("gemail");
			this.clasa = o.getString("clasa");
			this.grad = o.getString("grad");
			if(!o.isNull("elevmoisil")) {
				this.elevmoisil = o.getBoolean("elevmoisil");
			}
			if(!o.isNull("termeni")) {
				this.termeni = o.getBoolean("termeni");
			}
			this.status = getStatusFromString(o.getString("status"));
			if(!o.isNull("exec")) {
				this.exec = o.getString("exec");
			}
			if(!o.isNull("data")) {
				this.data = DateUtil.stringToDate(o.getString("data"));
			}else {
				this.data = Calendar.getInstance().getTime();
			}
		}catch(JSONException e) {
			e.printStackTrace();
			L2MSystem.logger.severe(e.toString());
		}
	}
	
	public Aplicatie(String nume, String gnume, String email, String gemail, String clasa, String grad, boolean elevmoisil, boolean termeni) {
		this.nume = nume;
		this.gnume = gnume;
		this.email = email;
		this.gemail = gemail;
		this.clasa = clasa;
		this.grad = grad;
		this.elevmoisil = elevmoisil;
		this.termeni = termeni;
		this.data = Calendar.getInstance().getTime();
	}
	
	public A_STATUS getStatusFromString(String s) {
		for(A_STATUS st : A_STATUS.values()) {
			if(st.toString().equals(s)) {
				return st;
			}
		}
		return A_STATUS.PENDING;
	}
	
	public JSONObject toJSON() {
		JSONObject o = new JSONObject();
		try {
			o.accumulate("nume", nume);
			o.accumulate("gnume", gnume);
			o.accumulate("email", email);
			o.accumulate("gemail", gemail);
			o.accumulate("clasa", clasa);
			o.accumulate("grad", grad);
			o.accumulate("status", status.toString());
			o.accumulate("exec", exec);
			o.accumulate("elevmoisil", elevmoisil);
			o.accumulate("termeni", termeni);
			o.accumulate("data", DateUtil.dateToString(data));
		}catch(JSONException e) {
			e.printStackTrace();
			L2MSystem.logger.severe(e.toString());
		}
		
		return o;
	}

}
