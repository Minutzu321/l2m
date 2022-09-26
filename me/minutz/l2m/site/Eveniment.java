package me.minutz.l2m.site;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Eveniment {
	
	public enum EvenimentTip{
		SENDINTA,
		ZI_DE_LUCRU,
		EVENIMENT;
	}
	
	public enum Repetitie{
		ODATA,
		SAPTAMANAL,
		LUNAR,
		ANUAL;
	}
	
	public String titlu,descriere;
	public Date data;
	public EvenimentTip tip;
	public Repetitie repetitie;
	public List<String> uuids = new ArrayList<String>();
	
	public Eveniment(String titlu, String descriere, Date data, EvenimentTip tip, Repetitie repetitie, List<String> uuids) {
		this.titlu = titlu;
		this.descriere = descriere;
		this.data = data;
		this.tip = tip;
		this.repetitie = repetitie;
		this.uuids = uuids;
	}
	
	public Repetitie getRepetitieFromString(String str) {
		for(Repetitie r : Repetitie.values()) {
			if(r.toString().toLowerCase().equals(str.toLowerCase())) {
				return r;
			}
		}
		return null;
	}
	
	public EvenimentTip getEvenimentTipFromString(String str) {
		for(EvenimentTip r : EvenimentTip.values()) {
			if(r.toString().toLowerCase().equals(str.toLowerCase())) {
				return r;
			}
		}
		return null;
	}
	

}
