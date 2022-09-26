package me.minutz.l2m.cmd.cmds;

import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.User;

public class DeleteCommand extends Command{

	public DeleteCommand() {
		super("delete", Grad.MEDIA.getImportanta());
	}

	@Override
	public String execute(String[] args, int lvlexec, String nume) {
		if(args.length==1) {
			String user = args[0];
			User u = UsersConfig.getUserByNume(user,false);
			if(u!=null) {
				u.delete();
				return "User "+user+" sters!";
			}else {
				return "Userul nu exista";
			}
		}
		return execute(lvlexec,nume);
	}

	@Override
	public String execute(int lvlexec, String nume) {
		return "INCORECT: scrie \"delete [USER]\"";
	}

	@Override
	public String help() {
		return "Sterge un user";
	}

}
