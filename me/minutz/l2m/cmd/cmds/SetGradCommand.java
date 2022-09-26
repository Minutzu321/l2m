package me.minutz.l2m.cmd.cmds;

import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.User;

public class SetGradCommand extends Command{

	public SetGradCommand() {
		super("setgrad", Grad.MEDIA.getImportanta());
	}

	@Override
	public String execute(String[] args, int lvlexec, String nume) {
		if(args.length!=2) {
			return execute(lvlexec,nume);
		}
		Grad grad = null;
		for(Grad g : Grad.values()) {
			if(g.toString().equalsIgnoreCase(args[0])) {
				grad=g;
			}
		}
		if(grad==null) {
			return "Gradul nu exista";
		}
		if(grad.getImportanta()>lvlexec) {
			return "Nu poti pune un grad mai mare decat tine";
		}
		
		User user = UsersConfig.getUserByNume(args[1],true);
		if(user==null) {
			return "Userul nu exista";
		}
		
		if(user.getGrad().getImportanta()>=lvlexec) {
			return "Nu poti schimba un grad mai mare ca tine";
		}
		
		user.setGrad(grad,true);
		return "Gradul "+grad.getNume()+" a fost atribuit userului "+user.getNume();
	}

	@Override
	public String execute(int lvlexec, String nume) {
		return "INVALID: setgrad [Grad] [User]";
	}

	@Override
	public String help() {
		return "Seteaza un grad pentru un user ";
	}

}
