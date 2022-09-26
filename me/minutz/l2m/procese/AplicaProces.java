package me.minutz.l2m.procese;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.cipher.CCipher;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.ha.Proces;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.Aplicatie;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.User;

public class AplicaProces extends Proces{

	public AplicaProces() {
		super(ProcessType.APLICA);
	}
	
	public boolean clasaValida(String c) {
		if(c.equals("VI")||c.equals("VII")||c.equals("VIII")||c.equals("IX")||c.equals("X")||c.equals("XI")) {
			return true;
		}
		return false;
	}


	@Override
	public JSONObject execute(String[] args) throws JSONException{
		String id = args[0];
		String nume = args[1];
		String email = args[2];
		String clasa = args[3];
		int grad = Integer.parseInt(CCipher.decrypt(L2MSystem.key, args[4]));
		boolean elevmoisil = Boolean.getBoolean(args[5]);
		boolean termeni = Boolean.getBoolean(args[6]);
		String ip = args[7];
		JSONObject raspuns = new JSONObject();
		User u = UsersConfig.getUserByUUID(id);
		if(u==null) {
			raspuns.accumulate("mesaj", "Userul nu a fost gasit");
			raspuns.accumulate("status", false);
			raspuns.accumulate("continua", false);
			raspuns.accumulate("out", true);
			return raspuns;
		}
		if(Grad.fromId(grad) == null) {
			raspuns.accumulate("mesaj", "Rolul la care ai aplicat nu exista");
			raspuns.accumulate("status", false);
			raspuns.accumulate("continua", false);
			raspuns.accumulate("out", false);
			return raspuns;
		}
		
		if(u.getAplicatie() != null) {
			raspuns.accumulate("mesaj", "Deja ai aplicat pentru rolul de "+u.getAplicatie().grad);
			raspuns.accumulate("status", false);
			raspuns.accumulate("continua", false);
			raspuns.accumulate("out", false);
			return raspuns;
		}
		
		if(!clasaValida(clasa)) {
			raspuns.accumulate("mesaj", "Selecteaza una din clasele date");
			raspuns.accumulate("status", false);
			raspuns.accumulate("continua", true);
			raspuns.accumulate("out", false);
			return raspuns;
		}
		
		u.setAplicatie(new Aplicatie(nume,u.getNume(),email,u.getEmail(),clasa,Grad.fromId(grad).getNume(),elevmoisil,termeni).toJSON().toString(), true);
		raspuns.accumulate("mesaj", "Ai aplicat pentru rolul \""+Grad.fromId(grad).getNume()+"\". Vei primi un email la adresa "+email+" in care vei fi anuntat de data, ora si locul unde se va desfasura sedinta.");
		raspuns.accumulate("status", true);
		raspuns.accumulate("continua", false);
		raspuns.accumulate("out", false);
		return raspuns;
	}
}
