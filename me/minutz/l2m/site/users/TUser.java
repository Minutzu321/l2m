package me.minutz.l2m.site.users;

import java.util.Date;

public class TUser {
	private User user;
	private String cod;
	private Date date;
	private int incercare;

	public TUser(User u,String cod,Date date,int incercare) {
		this.incercare = incercare;
		this.user = u;
		this.cod = cod;
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public Date getDate() {
		return this.date;
	}
	public void setDate(Date date) {
		this.date = date;
	}	
	public int getIncercare(){
		return incercare;
	}
	public void addIncercare(){
		incercare++;
	}
}