package me.minutz.l2m.site.users;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;

public enum Grad {
	USER(1,1344,"User","Un user simplu"),
	VOLUNTAR(2,1117,"Voluntar","Rolul de voluntar se da persoanelor care vor sa ajute echipa cu lucruri minore.<br>Nota: Acestia nu vor aparea ca membri oficiali."),
	DESIGNER(4,6514,"Designer","Rolul de designer se da persoanelor care au abilitati tehnice sau care vin cu idei realizabile(pe care le-ar putea pune in aplicare, fiind ajutati de echipa) privind design-ul robotului / al logo-ului / al site-ului / etc."),
	DESIGNER_SEF(5,9164,"Designer",""),
	MECANIC(4,4019,"Mecanic","Rolul de mecanic se da persoanelor care vor sa participe la construirea propriu-zisa a robotului. Acest lucru cuprinde asamblarea mecanica dar si conectarea cablurilor / senzorilor / motoarelor la module. De asemenea va puteti dezvolta abilitatile practice."),
	MECANIC_SEF(5,6719,"Mecanic",""),
	PROGRAMATOR(4,9311,"Programator","Rolul de programator se da persoanelor interesate de partea robotului care tine de software. Limbajul de programare pe care il folosim este Java care la randul lui este aplicat pe platforma Android. Acesta este unul dintre cele mai logice limbaje asa ca se invata rapid."),
	PROGRAMATOR_SEF(5,1943,"Programator",""),
	MEDIA(5,2940,"Media","Rolul \"Media\" se da persoanelor energice si dornice de comunicare. Acestia pot administra postarile de pe site-ul echipei, sa se ocupe de publicitate, postere, sponsorizari, etc."),
	MEDIA_SEF(999999991,2754,"Media",""),
	SPONSOR(3,7313,"Sponsor",""),
	MENTOR(999999990,5898,"Mentor","Rolul de mentor este atribuit profesorilor care ne ajuta si ne sustin cu acest proiect."),
	MANAGER(999999997,1003,"",""),
	OWNER(999999998,1011,"Programator","");
	
	private int id,i;
	private String nume,descriere;
	
	private Grad(int i,int id, String nume, String descriere) {
		this.i = i;
		this.id = id;
		this.nume = nume;
		this.descriere = descriere;
	}
	public int getId() {
		return id;
	}
	public String getNume() {
		return nume;
	}
	public int getImportanta(){
		return i;
	}
	public String getDescriere(){
		return descriere;
	}
	public JSONObject getJSON(){
		try{
		JSONObject o = new JSONObject();
			o.accumulate("id", id);
			o.accumulate("nume", nume);
			o.accumulate("descriere", descriere);
			return o;
		}catch(JSONException e){
			e.printStackTrace();
			L2MSystem.logger.severe(e.toString());
		}
		return null;
	}

	public static Grad fromId(int i){
		for(Grad g:Grad.values()){
			if(g.getId()==i){
				return g;
			}
		}
		return null;
	}
	public static Grad fromString(String i){
		for(Grad g:Grad.values()){
			if(g.getNume().equalsIgnoreCase(i)||i.toLowerCase().contains(g.getNume().toLowerCase())){
				return g;
			}
		}
		return null;
	}
}
