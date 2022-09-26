package me.minutz.l2m.cmd.cmds;

import java.util.List;

import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.IdDat;
import me.minutz.l2m.site.users.User;
import me.minutz.l2m.util.DateUtil;

public class InfoCommand extends Command{

	public InfoCommand() {
		super("info", Grad.MEDIA.getImportanta());
	}

	@Override
	public String execute(String[] args, int lvlexec, String nume) {
		User user = UsersConfig.getUserByNume(args[0],false);
		if(user != null) {
			if(user.getGrad().getImportanta()<=lvlexec) {
				StringBuilder sb = new StringBuilder();
				sb.append("\n UUID: "+user.getUUID()+"\n");
				sb.append(" Nume: "+user.getNume()+"\n");
				sb.append(" Email: "+user.getEmail()+"\n");
				sb.append(" Banat: "+user.isBanat()+"\n");
				sb.append(" Grad: "+user.getGrad().getNume()+"\n");
				sb.append(" Last activity: "+DateUtil.getDateFormat().format(user.getLastActivity())+"\n");
				List<IdDat> ipl =user.getIdlist();
				String fi = " "+ipl.size()+" Id: ";
				if(ipl.size()!=1) {
					fi = " "+ipl.size()+" IDs: ";
				}
				sb.append(fi+"\n");
				for(IdDat ipd:ipl) {
					sb.append("  - "+ipd.getId().replace("::1", "localhost")+" - Connected "+ipd.getI()+" time(s), last on "+DateUtil.getDateFormat().format(ipd.getData())+"\n");
				}
				return sb.toString();
			}else {
				return "Nu poti vedea informatiile acestui user";
			}
		}else {
			return "Userul nu a fost gasit";
		}
	}

	@Override
	public String execute(int lvlexec, String nume) {
		return "INVALID: info [User]";
	}

	@Override
	public String help() {
		return "Afla informatiile unui user";
	}

}
