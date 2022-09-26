package me.minutz.l2m.cmd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import me.minutz.l2m.cmd.cmds.AcceptCommand;
import me.minutz.l2m.cmd.cmds.Command;
import me.minutz.l2m.cmd.cmds.DeleteCommand;
import me.minutz.l2m.cmd.cmds.HelpCommand;
import me.minutz.l2m.cmd.cmds.InfoCommand;
import me.minutz.l2m.cmd.cmds.ListCommand;
import me.minutz.l2m.cmd.cmds.ParolaCommand;
import me.minutz.l2m.cmd.cmds.ProcessCommand;
import me.minutz.l2m.cmd.cmds.SetGradCommand;

public class Consola implements Runnable{
	Scanner input = null;
	public static List<Command> cmds = new ArrayList<Command>();
	private static SimpleDateFormat sdf;
	public static void display(String mesaj){
			String time = "["+sdf.format(new Date())+"] " + mesaj;
			System.out.println(time);
	}
	public static String getDisplay(String mesaj){
		String time = "["+sdf.format(new Date()) + "] " + mesaj;
		return time;
	}
	public Consola(){
		sdf = new SimpleDateFormat("HH:mm:ss");
		input = new Scanner(System.in);
		
		cmds.add(new HelpCommand());
		cmds.add(new DeleteCommand());
		cmds.add(new InfoCommand());
		cmds.add(new SetGradCommand());
		cmds.add(new AcceptCommand());
		cmds.add(new ListCommand());
		cmds.add(new ProcessCommand());
		cmds.add(new ParolaCommand());
	}
	public static Command getCommand(String cmd){
		for(Command c:cmds){
			if(c.getCmd().equalsIgnoreCase(cmd)){
				return c;
			}
		}
		return null;
	}
	@Override
	public void run() {
	    while (true) {
	    	String s = input.nextLine();
	    	display(cmd(s,999999999,"Consola")+"\n");
	    }
	}
	
	public static String cmd(String s, int le, String nume){
    	if(!s.replace(" ", "").isEmpty()){
    	String comanda = "";
    	String[] args = null;
    	if(s.contains(" ")){
    		String[] c = s.split(" ");
    		comanda = c[0].toLowerCase();
    		args = new String[c.length-1];
    	for(int i=1;i<c.length;i++){
    		args[i-1]=c[i];
    	}
    	}else{
    		comanda = s.toLowerCase();
    	}
    	Command c = getCommand(comanda);
    	if(c != null){
    	if(args != null){
    		try{
    			if(le>=c.getLvlacces()){
    		return c.execute(args,le,nume);
    			}else{
    				return "Nu ai permisiunea";
    			}
    		}catch(Exception e){
    			e.printStackTrace();
    			return e.getStackTrace().toString();
    		}
      }else{
    	  try{
    		  if(le>=c.getLvlacces()){
    	  return c.execute(le,nume);
    		  }else{
    			  return "Nu ai permisiunea";
    		  }
    	  }catch(Exception e){
    			e.printStackTrace();
    			return e.getStackTrace().toString();
    		}
      }
    	}else{
    		return "Comanda nu a fost gasita. Scrie "+'"'+"help"+'"'+" pentru ajutor";
    	}
      }else{
    	  return "Comanda nu poate fi goala!";
      }
	}

}
