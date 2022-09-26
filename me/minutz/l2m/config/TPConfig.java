package me.minutz.l2m.config;

import java.util.Calendar;
import java.util.Date;

public class TPConfig {
	
	private PConfig pconf;
	private Date data;
	private String name;
	private int min;
	
	public TPConfig(String name,PConfig pconf,int min) {
		this.min = min;
		this.name = name;
		this.pconf = pconf;
		this.data = Calendar.getInstance().getTime();
	}
	
	public void resetDate() {
		this.data = Calendar.getInstance().getTime();
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMin() {
		return min;
	}

	public PConfig getPConf() {
		return pconf;
	}

	public Date getData() {
		return data;
	}

	public String getName() {
		return name;
	}
	
	public void removeIfNotUsed() {
		if(min==1) {
			min=0;
		}
	}
}
