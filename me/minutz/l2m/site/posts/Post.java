package me.minutz.l2m.site.posts;

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
import me.minutz.l2m.config.PConfig;
import me.minutz.l2m.config.TPConfig;
import me.minutz.l2m.config.post.PostConfig;
import me.minutz.l2m.site.users.User;
import me.minutz.l2m.util.DateUtil;

public class Post {
	
	private String postID,owner,title,content;
	private Date date;
	private List<String> likes = new ArrayList<String>(), images = new ArrayList<String>();
	private TPConfig tpconfig;
	
	public Post(TPConfig conf) throws JSONException {
		this.tpconfig = conf;
		if(tpconfig != null) {
			FileConfiguration fc = tpconfig.getPConf().getFC();
			
			JSONObject json = new JSONObject(fc.getString("data"));

			this.owner = json.getString("owner");
			this.title = json.getString("title");
			this.content = json.getString("content");
			List<String> laics = new ArrayList<String>();
			JSONArray jar = json.getJSONArray("likes");
			for(int i=0;i<jar.length();i++) {
				laics.add((String) jar.get(i));
			}
			this.likes = laics;
			List<String> imgs = new ArrayList<String>();
			JSONArray jer = json.getJSONArray("images");
			for(int i=0;i<jer.length();i++) {
				imgs.add((String) jer.get(i));
			}
			this.images = imgs;
			try {
				this.date = DateUtil.getDateFormat().parse(json.getString("date"));
			} catch (ParseException e) {
				e.printStackTrace();
				L2MSystem.logger.severe(e.toString());
			}
			this.postID = Long.toString(date.getTime());
		}
	}
	
	public Post(String owner, String title, String content) {
		this.postID = Long.toString(date.getTime());
		this.tpconfig = PostConfig.getInstance().getYMLFile(postID);
		
		this.owner = owner;
		this.title = title;
		this.content = content;
		this.date = Calendar.getInstance().getTime();
		
		save();
	}

	public String getID() {
		return postID;
	}

	public String getOwner() {
		return owner;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Date getDate() {
		return date;
	}

	public List<String> getLikes() {
		return likes;
	}
	
	public void addLike(String like) {
		likes.add(like);
	}

	public List<String> getImages(){
		return images;
	}

	public void addImage(String image){
		images.add(image);
	}

	public TPConfig getTPConfig() {
		return tpconfig;
	}
	
	public JSONObject getJSON() {
		JSONObject value = new JSONObject();
		try {
			value.append("id", postID);
			value.append("owner", owner);
			value.append("title", title);
			value.append("content", content);
			value.append("date", DateUtil.getDateFormat().format(date));
			value.append("likes", likes);
			value.append("images", images);
		} catch (JSONException e) {
			e.printStackTrace();
			L2MSystem.logger.severe(e.toString());
		}
		return value;
	}
	
	public void save() {
		FileConfiguration fc = tpconfig.getPConf().getFC();
		fc.set("data", getJSON().toString());
		tpconfig.getPConf().save();
	}
	
	public void delete() {
		PConfig pconf = tpconfig.getPConf();
		pconf.getFC().set("deleted", true);
		pconf.save();
		pconf.delete();
	}
	

}
