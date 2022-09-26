package me.minutz.l2m.site.users;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.util.DateUtil;

public class IdDat {
	
	private String id;
	private Date data;
	private int i;
	
	public IdDat(String id, String d, int i){
		this.i = i;
		this.id = id;
		try {
			this.data = DateUtil.getDateFormat().parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public IdDat(String id, Date d, int i){
		this.i = i;
		this.id = id;
		this.data = d;
	}
	
	public boolean isIp(){
		if(id.contains("::1")||id.contains(".")){
			return true;
		}
		return false;
	}

	public boolean isLocalhost(){
		if(id.contains("::1")){
			return true;
		}
		return false;
	}

	public String getId() {
		return id;
	}
	public Date getData() {
		return data;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public JSONObject getJSON(){
		JSONObject o = new JSONObject();
		try{
			o.accumulate("isIp", isIp());
			o.accumulate("isLocalhost", isLocalhost());
			o.accumulate("id", id);
			o.accumulate("data", DateUtil.dateToString(data));
			o.accumulate("i", i);
			return o;
		}catch(JSONException e){
			e.printStackTrace();
			L2MSystem.logger.severe(e.toString());
		}
		return null;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	
	public static IdDat fromString(String s){
		try{
			JSONObject o = new JSONObject(s);
			return new IdDat(o.getString("id"),o.getString("data"),o.getInt("i"));
		}catch(JSONException e){
			e.printStackTrace();
			L2MSystem.logger.severe(e.toString());
		}
		return new IdDat(s,(Date)null,0);
	}
}
