package me.minutz.l2m.cmd.cmds;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.events.CheckCodEvent;
import me.minutz.l2m.procese.events.CheckCodEvent.CheckCodResult;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.TUser;

public class AcceptCommand extends Command{

	public AcceptCommand() {
		super("accept", Grad.MEDIA.getImportanta());
	}

	@Override
	public String execute(String[] args, int lvlexec, String nume) {
		TUser tuser = UsersConfig.getTUserByNume(args[0]);
		if(tuser == null) {
			return "Userul temporal nu a fost gasit";
		}
		UsersConfig.tusers.remove(tuser);
		UsersConfig.addUser(tuser.getUser());
		L2MSystem.el.onCheckCodEvent(new CheckCodEvent(args,tuser,CheckCodResult.SUCCES,null));
		return "User acceptat";
	}

	@Override
	public String execute(int lvlexec, String nume) {
		return "INVALID: accept [User]";
	}

	@Override
	public String help() {
		return "Accepta o inregistrare noua direct";
	}
	
	

}
