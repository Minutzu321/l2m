package me.minutz.l2m.util;

import java.util.ArrayList;
import java.util.List;

public class RUtil {
  public static List<?> getPageFromList(List<?> l, int pag, int obiectePePagina){
		if(pag<=0) pag =1;
		int obiecte = l.size();
		int pagini = obiecte/obiectePePagina;
		if(obiecte % obiectePePagina !=0) pagini++;
		if(pag>pagini) {
			pag=pagini;
		}
		List<Object> nl = new ArrayList<Object>();
		for(int i=pag*obiectePePagina-obiectePePagina;i<pag*obiectePePagina;i++){
			if(i<obiecte){
				nl.add(l.get(i));
			}
		}
		return nl;
	}
}