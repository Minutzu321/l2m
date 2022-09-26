package me.minutz.l2m.cmd.cmds;

import java.util.ArrayList;
import java.util.List;

import me.minutz.l2m.cmd.Consola;

public class HelpCommand extends Command{
	private int nrppag=4;
	public HelpCommand() {
		super("help",2);
	}
	
	public boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}

	@Override
	public String execute(String[] args,int lvlexec, String nume) {
		if(!isInteger(args[0])){
		Command c = Consola.getCommand(args[0]);
		if(c != null){
			if(c.getLvlacces()<=lvlexec){
			StringBuilder sb = new StringBuilder();
			sb.append("\n------------------\n");
			sb.append(c.getCmd()+" - "+c.help());
			sb.append("\n------------------");
		return sb.toString();
			}else{
				return "Nu ai permisiunea.";
			}
		}
		return "Comanda "+args[0]+" nu a fost gasita";
		}else{
			List<Command> cmds = new ArrayList<Command>();
			for(Command cmdl:Consola.cmds){
				if(cmdl.getLvlacces()<=lvlexec){
					cmds.add(cmdl);
				}
			}
			int pag = Integer.parseInt(args[0]);
			if(pag<=0) pag =1;
			int com = cmds.size();
			int pagini = com/nrppag;
			if((com%nrppag)!=0){
				pagini++;
			}
			if(pag<=pagini){
			StringBuilder sb = new StringBuilder();
			sb.append("\n---------["+pag+"/"+pagini+"]---------\n");
			for(int l=pag*nrppag-nrppag;l<pag*nrppag;l++){
				if(l<com){
				Command c = cmds.get(l);
					if(c!=null){
						if(c.getLvlacces()<=lvlexec){
							sb.append(c.getCmd()+" - "+c.help()+"\n");
						}
					}
				}
			}
			sb.append("---------["+pag+"/"+pagini+"]---------");
			return sb.toString();
			}else{
				String[] s = new String[1];
				s[0]=Integer.toString(pagini);
				return execute(s,lvlexec,nume);
			}
		}
	}

	@Override
	public String execute(int lvlexec, String nume) {
		String[] s = new String[1];
		s[0]="1";
		return execute(s,lvlexec,nume);
	}

	@Override
	public String help() {
		return "Cauta ajutor.";
	}

}
