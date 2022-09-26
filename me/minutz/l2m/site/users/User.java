package me.minutz.l2m.site.users;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.file.FileConfiguration;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.cipher.CCipher;
import me.minutz.l2m.config.PConfig;
import me.minutz.l2m.config.TPConfig;
import me.minutz.l2m.site.Aplicatie;
import me.minutz.l2m.util.DateUtil;

public class User {
	private String nume,parola,uuid,email;
	private List<IdDat> idlist = new ArrayList<IdDat>();
	private boolean banat;
	private Grad grad;
	private TPConfig tpconf;
	private PConfig pconf;
	private Date lastActivity;

	public User(TPConfig tpconf){
		this.tpconf = tpconf;
		this.pconf = tpconf.getPConf();
	}
	
	public User(String nume, String email, String parola, String uuid, List<IdDat> idlist, boolean banat, Grad grad, Date lastActivity) {
		this.nume = nume.replace("_", " ");
		this.parola = parola;
		this.uuid = uuid;
		this.idlist = idlist;
		this.banat = banat;
		this.grad = grad;
		this.lastActivity = lastActivity;
		this.email = email;
	}

	public String getNume() {
		if(tpconf != null){
			nume = pconf.getFC().getString("nume");
		}
		return nume.trim();
	}

	public void setNume(String nume, boolean s) {
		this.nume = nume.trim();
		FileConfiguration user = pconf.getFC();
		user.set("nume", nume.trim());
		if(s){
			save();
		}
	}

	public String getParola() {
		if(tpconf != null){
			parola = CCipher.decrypt(L2MSystem.key, pconf.getFC().getString("par"));
		}
		return parola;
	}

	public void setParola(String parola, boolean s) {
		this.parola = parola;
		FileConfiguration user = pconf.getFC();
		user.set("par", CCipher.encrypt(L2MSystem.key, L2MSystem.iv, parola));
		if(s){
			save();
		}
	}

	public String getUUID() {
		if(tpconf != null){
			uuid = pconf.getFile().getName().replace(".yml", "");
		}
		return uuid;
	}

	public List<IdDat> getIdlist() {
		if(tpconf==null){
			return idlist;
		}
		idlist = new ArrayList<IdDat>();
		FileConfiguration user = pconf.getFC();
		List<String> l = user.getStringList("idlist");
		if(l!=null){
			for(String idds : l){
				IdDat idd = IdDat.fromString(idds);
				idlist.add(idd);
			}
		}
		return idlist;
	}

	public void addToIdlist(IdDat iddatq, boolean s) {
		String id = iddatq.getId();
		idlist = getIdlist();
		IdDat i = null;
		for(IdDat idt:idlist){
			if(idt.getId().equals(id)){
				i=idt;
			}
		}
		IdDat iddat = null;
		if(i!=null){
			iddat = new IdDat(id,Calendar.getInstance().getTime(),(i.getI()+1));
		}else{
			iddat = new IdDat(id,Calendar.getInstance().getTime(),1);	
		}
		idlist.add(iddat);
		
		FileConfiguration user = pconf.getFC();
		List<String> l = user.getStringList("idlist");
		if(l!=null){
			for(String idds : l){
				IdDat idd = IdDat.fromString(idds);
				if(idd.getId().equals(iddat.getId())){
					l.remove(idds);
					break;
				}
			}
			l.add(iddat.getJSON().toString());
			user.set("idlist", l);
		}else{
			l = new ArrayList<String>();
			l.add(iddat.getJSON().toString());
			user.set("idlist", l);
		}
		if(s){
			save();
		}
	}
	
	public void addToIdlist(String id, boolean s) {
		idlist = getIdlist();

		IdDat i = null;
		for(IdDat idt:idlist){
			if(idt.getId().equals(id)){
				i=idt;
			}
		}
		IdDat iddat = null;
		if(i!=null){
			iddat = new IdDat(id,Calendar.getInstance().getTime(),(i.getI()+1));
		}else{
			iddat = new IdDat(id,Calendar.getInstance().getTime(),1);	
		}
		
		idlist.add(iddat);
		
		FileConfiguration user = pconf.getFC();
		List<String> l = user.getStringList("idlist");
		if(l!=null){
			for(String idds : l){
				IdDat idd = IdDat.fromString(idds);
				if(idd.getId().equals(iddat.getId())){
					l.remove(idds);
					break;
				}
			}
			l.add(iddat.getJSON().toString());
			user.set("idlist", l);
		}else{
			l = new ArrayList<String>();
			l.add(iddat.getJSON().toString());
			user.set("idlist", l);
		}
		if(s){
			save();
		}
	}

	public boolean isBanat() {
		if(tpconf != null){
			banat = pconf.getFC().getBoolean("ban");
		}
		return banat;
	}

	public void setBanat(boolean banat, boolean s) {
		this.banat = banat;
		FileConfiguration user = pconf.getFC();
		user.set("ban", banat);
		if(s){
			save();
		}
	}

	public Grad getGrad() {
		if(tpconf != null){
			grad = Grad.fromId(pconf.getFC().getInt("grad"));
		}
		return grad;
	}

	public void setGrad(Grad grad, boolean s) {
		this.grad = grad;
		FileConfiguration user = pconf.getFC();
		user.set("grad", grad.getId());
		if(s){
			save();
		}
	}
	
	public String getEmail() {
		if(tpconf != null){
			email = pconf.getFC().getString("email");
		}
		return email;
	}

	public void setEmail(String email, boolean s) {
		this.email = email;
		FileConfiguration user = pconf.getFC();
		user.set("email", email);
		if(s){
			save();
		}
	}
	
	public void updateLastActivity(boolean s) {
		this.lastActivity = Calendar.getInstance().getTime();
		
		FileConfiguration user = pconf.getFC();
		user.set("lastActivity", DateUtil.getDateFormat().format(lastActivity));
		if(s){
			save();
		}
	}
	
	public Date getLastActivity() {
		if(tpconf != null){
			try{
				lastActivity = DateUtil.getDateFormat().parse(pconf.getFC().getString("lastActivity"));
			}catch(ParseException e){
				lastActivity = Calendar.getInstance().getTime();
				L2MSystem.logger.severe(e.toString());
			}
		}
		return this.lastActivity;
	}
	
	public void setAplicatie(String a, boolean s) {
		FileConfiguration user = pconf.getFC();
		user.set("aplicatie", a);
		if(s){
			save();
		}
	}
	
	public Aplicatie getAplicatie() {
		String j = pconf.getFC().getString("aplicatie");
		if(j != null && !j.isEmpty()) {
			return new Aplicatie(j);
		}
		return null;
	}
	
	public JSONObject getUserJSON() {
		JSONObject o = new JSONObject();
		try {
			o.append("nume", nume);
			o.append("email", email);
			JSONArray jar = new JSONArray();
			for(IdDat id : idlist) {
				jar.put(id.getJSON());
			}
			o.append("idlist", jar);
			o.append("grad", grad.getJSON());
			o.append("lastActivity", DateUtil.dateToString(lastActivity));
			o.append("aplicatie", getAplicatie().toJSON());
		}catch(JSONException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public boolean isMembru() {
		if(grad != Grad.USER && grad != Grad.SPONSOR){
			return true;
		}
		return false;
	}
	
	public Membru getMembru() {
		if(isMembru()) {
			return new Membru(this);
		}
		return null;
	}
	
	public PConfig getPConf() {
		return pconf;
	}
	
	public TPConfig getTPConf() {
		return tpconf;
	}

	public void save(){
		pconf.save();
	}
	
	public void delete() {
		pconf.getFC().set("deleted", true);
		pconf.save();
		pconf.delete();
	}
}
