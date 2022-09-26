package me.minutz.l2m.config.post;

import org.json.JSONException;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.config.Configuratie;
import me.minutz.l2m.site.posts.Post;

public class PostConfig extends Configuratie{

	public PostConfig() {
		super(ConfTip.POSTS);
	}
	
	public static PostConfig getInstance() {
		return (PostConfig) L2MSystem.getConfig(ConfTip.POSTS);
	}

	public static Post getPostByID(String id){
		try{
			return new Post(getInstance().getYMLFile(id));
		}catch(JSONException e){
			e.printStackTrace();
		}
		return null;
	}
}
