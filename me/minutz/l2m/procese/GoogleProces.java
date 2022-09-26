package me.minutz.l2m.procese;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.ha.Proces;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.IdDat;
import me.minutz.l2m.site.users.User;

public class GoogleProces extends Proces{

	public GoogleProces() {
		super(ProcessType.GOOGLE);
	}
	
	public boolean valid(Payload payload) {
		payload.get("name");
		return true;
	}

	@Override
	public JSONObject execute(String[] args) throws JSONException{
		String token = args[0];
		String ip = args[1];
		JSONObject raspuns = new JSONObject();
		try {
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), new JacksonFactory())
				    .setAudience(Collections.singletonList("792682397537-fqlm93fougs01gjtmk9osbj8vis6cfp9.apps.googleusercontent.com"))
				    .build();
			GoogleIdToken idToken = verifier.verify(token);
			if (idToken != null) {
			  Payload payload = idToken.getPayload();
	
			  String userId = payload.getSubject();
			  String email = payload.getEmail();
			  String name = (String) payload.get("name");
			  System.out.println(payload.toPrettyString());
			  String parola = null;
			  User u = UsersConfig.getUserByUUID(userId);
			  if(u == null) {
				  List<IdDat> idd = new ArrayList<IdDat>();
				  idd.add(new IdDat(ip,Calendar.getInstance().getTime(),1));
				  User a = new User(name,email,"oratuscastapelacmacmacmacorezareee"+userId,userId,idd,false,Grad.USER,Calendar.getInstance().getTime());
				  UsersConfig.addUser(a);
				  parola = "oratuscastapelacmacmacmacorezareee"+userId;
			  }else{
				  if(u.isBanat()) {
					  raspuns.accumulate("mesaj", "Acest cont este interzis");
					  u.updateLastActivity(true);
					  raspuns.accumulate("status", false);
					  return raspuns;
				  }
				  u.addToIdlist(ip,true);
				  u.updateLastActivity(true);
				  parola = u.getParola();
			  }
			  raspuns.accumulate("user", name);
			  raspuns.accumulate("uuid", userId);
			  raspuns.accumulate("parola", parola);
			  raspuns.accumulate("email", email);
			  raspuns.accumulate("session", L2MSystem.sesiuneaCookieurilor);
			  raspuns.accumulate("status", true);
			} else {
				raspuns.accumulate("mesaj", "Eroare");
				raspuns.accumulate("status", false);
			}
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
			raspuns.accumulate("mesaj", "Eroare");
			raspuns.accumulate("status", false);
		}
		
		
		return raspuns;
	}
}
