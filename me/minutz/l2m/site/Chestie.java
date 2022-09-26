package me.minutz.l2m.site;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;

public abstract class Chestie {
	
	public static List<Chestie> chestii = new ArrayList<Chestie>();
	
	public String mesaj,acceptaMesaj,refuzaMesaj,userUUID,cuuid;
	
	public Chestie(String mesaj, String acceptaMesaj, String refuzaMesaj, String userUUID) {
		this.mesaj = mesaj;
		this.acceptaMesaj = acceptaMesaj;
		this.refuzaMesaj = refuzaMesaj;
		this.userUUID = userUUID;
		this.cuuid = UUID.randomUUID().toString();
		chestii.add(this);
		}
	
	public JSONObject toJSON() {
		JSONObject o = new JSONObject();
		try {
			o.accumulate("mesaj", mesaj);
			o.accumulate("acc", acceptaMesaj);
			o.accumulate("ref", refuzaMesaj);
			o.accumulate("user", userUUID);
			o.accumulate("chestie", cuuid);
		}catch(JSONException e) {
			e.printStackTrace();
			L2MSystem.logger.severe(e.toString());
		}
		return o;
	}
	
	public abstract void onAccept();
	public abstract void onRefuz();
	
	public static Chestie getChestieByCId(String id) {
		for(Chestie c:chestii) {
			if(c.cuuid.equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	public static Chestie getChestieByUser(String id) {
		for(Chestie c:chestii) {
			if(c.userUUID.equals(id)) {
				return c;
			}
		}
		return null;
	}

}
