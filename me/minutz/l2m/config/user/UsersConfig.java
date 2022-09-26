package me.minutz.l2m.config.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.config.Configuratie;
import me.minutz.l2m.config.TPConfig;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.IdDat;
import me.minutz.l2m.site.users.Membru;
import me.minutz.l2m.site.users.TUser;
import me.minutz.l2m.site.users.User;
import me.minutz.l2m.util.DateUtil;

public class UsersConfig extends Configuratie{

	public UsersConfig() {
		super(ConfTip.USERS);
	}
	
	public static UsersConfig getInstance() {
		return (UsersConfig) L2MSystem.getConfig(ConfTip.USERS);
	}

	public static List<TUser> tusers = new ArrayList<TUser>();
	public static TUser getTUserByUUID(String uuid) {
		for(TUser tuser : tusers) {
			if(tuser.getUser().getUUID().equals(uuid)) {
				return tuser;
			}
		}
		return null;
	}
	public static TUser getTUserByNume(String nume) {
		for(TUser tuser : tusers) {
			if(tuser.getUser().getNume().toLowerCase().equals(nume.toLowerCase())) {
				return tuser;
			}
		}
		return null;
	}
	public static TUser getTUserByEmail(String email) {
		for(TUser tuser : tusers) {
			if(tuser.getUser().getEmail().equals(email)) {
				return tuser;
			}
		}
		return null;
	}
	public static TUser getTUserByPass(String pass) {
		for(TUser tuser : tusers) {
			if(tuser.getUser().getParola().equals(pass)) {
				return tuser;
			}
		}
		return null;
	}
	public static void checkTUsers() {
		if(tusers.size()>0) {
			List<TUser> rtusers = new ArrayList<TUser>();
			for(TUser tuser:tusers) {
				if(DateUtil.getElapsedTime(tuser.getDate(), TimeUnit.SECONDS)>(60*L2MSystem.emailActivationExpireInMinutes)) {
					rtusers.add(tuser);
				}
			}
			if(rtusers.size()>0) {
				for(TUser tuser:rtusers) {
					tusers.remove(tuser);
					System.out.println(tuser.getUser().getNume()+" - expired");
				}
			}
		}
	}
	
	public static String getRUUID(){
		String uuid = UUID.randomUUID().toString();
		if(exists(uuid)){
			return getRUUID();
		}
		return uuid;
	}
	public static boolean exists(String uuid){
		List<String> l = getInstance().getYMLFileNameList();
		if(l!=null){
			if(l.contains(uuid)){
				return true;
			}
		}
		return false;
		
	}
	
	public static void addUser(User u){
		addUser(u.getUUID(),u.getEmail(),u.getNume(),u.getParola(),u.getIdlist().get(0));
	}
	
	public static User addUser(String uuid,String email,String nume,String par,IdDat ip){
		TPConfig tpconf = getInstance().getYMLFile(uuid);
		User u=new User(tpconf);
		u.setNume(nume,false);
		u.setEmail(email,false);
		u.setParola(par,false);
		u.setBanat(false,false);
		u.setGrad(Grad.USER,false);
		u.setAplicatie(null, false);
		u.updateLastActivity(false);
		u.addToIdlist(ip,false);
		u.save();
		return u;
	}
	
	public static User getUserByUUID(String uuid) {
		if(exists(uuid)) {
			TPConfig tpconf = getInstance().getYMLFile(uuid);
			return new User(tpconf);
		}
		return null;
	}
	
//	private static boolean 
	
	public static User getUserByNume(String nume,boolean retine){
		List<String> l = getInstance().getYMLFileNameList();
		if(l==null){
			return null;
		}
		for(String uuid:l){
			User usr = getUserByUUID(uuid);
			String unume = usr.getNume().toLowerCase();
			nume = nume.toLowerCase().replace("_", " ");
			if(usr.getNume().toLowerCase().equals(nume.toLowerCase())||usr.getNume().toLowerCase().equals(nume.toLowerCase().replace("_", " "))){
				if(retine){
					usr.getTPConf().resetDate();
				}else{
					usr.getTPConf().removeIfNotUsed();
				}
				return usr;
			}else{
				usr.getTPConf().removeIfNotUsed();
			}
		}
		return null;
	}
	
	public static User getUserByEmail(String email,boolean retine){
		List<String> l = getInstance().getYMLFileNameList();
		if(l==null){
			return null;
		}
		for(String uuid:l){
			User usr = getUserByUUID(uuid);
			if(usr.getEmail().equals(email)){
				if(retine){
					usr.getTPConf().resetDate();
				}else{
					usr.getTPConf().removeIfNotUsed();
				}
				return usr;
			}else{
				usr.getTPConf().removeIfNotUsed();
			}
		}
		return null;
	}

	public static List<User> getUsersByGrad(Grad g,boolean retine){
		List<String> l = getInstance().getYMLFileNameList();
		if(l==null){
			return null;
		}
		List<User> uli = new ArrayList<User>();
		for(String uuid:l){
			User usr = getUserByUUID(uuid);
			if(usr.getGrad() == g){
				if(retine){
					usr.getTPConf().resetDate();
				}else{
					usr.getTPConf().removeIfNotUsed();
				}
				uli.add(usr);
			}else{
				usr.getTPConf().removeIfNotUsed();
			}
		}
		return uli;
	}
	
	public static List<User> getUsersByIP(String ip){
		List<String> l = getInstance().getYMLFileNameList();
		if(l==null){
			return null;
		}
		List<User> uli = new ArrayList<User>();
		for(String uuid:l){
			User usr = getUserByUUID(uuid);
			for(IdDat idd : usr.getIdlist()) {
				if(idd.getId().equals(ip)) {
					uli.add(usr);
					break;
				}
			}
		}
		return uli;
	}
	
	public static List<User> getAplicanti(){
		List<String> l = getInstance().getYMLFileNameList();
		if(l==null){
			return null;
		}
		List<User> uli = new ArrayList<User>();
		for(String uuid:l){
			User usr = getUserByUUID(uuid);
			if(usr.getAplicatie() != null){
				uli.add(usr);
			}
			usr.getTPConf().removeIfNotUsed();
		}
		return uli;
	}
	
	public static List<Membru> getMembri(){
		List<String> l = getInstance().getYMLFileNameList();
		if(l==null){
			return null;
		}
		List<Membru> uli = new ArrayList<Membru>();
		for(String uuid:l){
			User usr = getUserByUUID(uuid);
			if(usr.isMembru()){
				uli.add(usr.getMembru());
			}
				usr.getTPConf().removeIfNotUsed();
		}
		return uli;
	}
}