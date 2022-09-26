package me.minutz.l2m.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.yaml.snakeyaml.file.YamlConfiguration;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.util.DateUtil;

public abstract class Configuratie {
	
	public enum ConfTip{
		USERS,
		POSTS,
		EVENIMENTE,
		JOCURI;
	}
	
	public ConfTip tip;
	public File folder;
	public List<TPConfig> tempConfigs = new ArrayList<TPConfig>();
	
	public Configuratie(ConfTip ct) {
		this.tip = ct;
		
		folder = new File(L2MSystem.configPath+"/"+ct.toString().toLowerCase());
		if(!folder.exists()){
			folder.mkdir();
		}
	}
	
	public List<String> getYMLFileNameList() {
		List<String> lista = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			String data = fileEntry.getName();
			if(data.contains(".yml")) {
				lista.add(data.replace(".yml", ""));
			}
		}
		return lista;
	}
	
	public TPConfig getYMLFile(String nume){
		TPConfig tpc = getTPCByName(nume);
		if(tpc==null) {
			File f = new File(L2MSystem.configPath+"/"+tip.toString().toLowerCase(), nume+".yml");
			YamlConfiguration yc = new YamlConfiguration();
			PConfig pco = new PConfig(f,yc);
			if(!pco.getFC().getBoolean("deleted")) {
				TPConfig tpco = new TPConfig(nume,pco,1);
				tempConfigs.add(tpco);
				return tpco;
			}
			return null;
		}else{
			PConfig pco = tpc.getPConf();
			if(!pco.getFC().getBoolean("deleted")) {
				return tpc;
			}
			if(tempConfigs.contains(tpc)) {
				tempConfigs.remove(tpc);
			}
			return null;
		}
	}
	  
	private TPConfig getTPCByName(String name) {
		List<TPConfig> del = new ArrayList<TPConfig>();
		for(TPConfig tpc : tempConfigs) {
			if(tpc.getName().equals(name)) {
				tpc.resetDate();
				return tpc;
			}
			long v = DateUtil.getElapsedTime(tpc.getData(), TimeUnit.MINUTES);
			if(v>=tpc.getMin()) {
				del.add(tpc);
			}	
		}
		for(TPConfig tpc : del) {
			if(tempConfigs.contains(tpc)) {
				tempConfigs.remove(tpc);
			}
		}
		return null;
	}
	  
	public void clearTemp() {
		tempConfigs.clear();
		tempConfigs = new ArrayList<TPConfig>();
	}

}
