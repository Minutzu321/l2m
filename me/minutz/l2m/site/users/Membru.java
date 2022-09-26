package me.minutz.l2m.site.users;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.file.FileConfiguration;

import me.minutz.l2m.config.PConfig;
import me.minutz.l2m.config.TPConfig;

public class Membru extends User{

	private String descriere,imageURL,gen="nedefinit";
	private boolean lego = false;
	private List<String> posts;
	private TPConfig tpconf;
	private PConfig pconf;
	
	public Membru(User user) {
		super(user.getTPConf());
		this.tpconf = user.getTPConf();
		this.pconf = user.getPConf();
	}
	
	public String getDescriere(){
		if(tpconf != null){
			descriere = pconf.getFC().getString("desc");
		}
		return descriere.trim();
	}

	public void setDescriere(String descriere, boolean s) {
		this.descriere = descriere.trim();
		FileConfiguration user = pconf.getFC();
		user.set("desc", descriere.trim());
		if(s){
			save();
		}
	}
	
	public void setImage(String imageURL, boolean s) {
		this.imageURL = imageURL;
		
		FileConfiguration user = pconf.getFC();
		user.set("imageURL", imageURL);
		if(s){
			save();
		}
	}
	
	public String getImage() {
		if(tpconf != null){
			imageURL = pconf.getFC().getString("imageURL");
		}
		return imageURL;
	}
	
	public void setLego(boolean lego, boolean s) {
		this.lego = lego;
		
		FileConfiguration user = pconf.getFC();
		user.set("lego", lego);
		if(s){
			save();
		}
	}
	
	public boolean isLego() {
		if(tpconf != null){
			lego = pconf.getFC().getBoolean("lego");
		}
		return lego;
	}
	
	public List<String> getPosts(){
		if(tpconf != null){
			posts = pconf.getFC().getStringList("posts");
		}
		return posts;
	}

	public void addPost(String post, boolean s) {
		List<String> po = getPosts();
		po.add(post);
		setPosts(po,s);
	}

	public void setPosts(List<String> posts, boolean s) {
		FileConfiguration user = pconf.getFC();
		user.set("posts", posts);
		if(s){
			save();
		}
	}
	
	public JSONObject getMembruJSON() {
		JSONObject o = getUserJSON();
		try {
			o.accumulate("descriere", descriere);
			o.accumulate("imageURL", imageURL);
			o.accumulate("gen", gen);
			o.accumulate("posts", posts);
		}catch(JSONException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
