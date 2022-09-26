package me.minutz.l2m.cmd.cmds;

import java.util.List;

import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.User;
import me.minutz.l2m.util.RUtil;

public class ListCommand extends Command{

	public ListCommand() {
		super("list", Grad.MEDIA.getImportanta());
	}
	
	private int iop = 10;
	
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
	public String execute(String[] args, int lvlexec, String nume) {
		String u = args[0];
		List<String> l = UsersConfig.getInstance().getYMLFileNameList();
		StringBuilder sb = new StringBuilder();
		
		if(isInteger(u)) {
			int pag = Integer.parseInt(u);
			if(pag<=0) pag =1;
			int iteme = l.size();
			int pagini = iteme/iop;
			if((iteme%iop)!=0) pagini++;
			if(pag>pagini) {
				pag=pagini;
			}
			sb.append("\n  Page "+pag+" from "+pagini+"\n");
			for(Object obj : RUtil.getPageFromList(l, pag, iop)) {
				String uuid = (String) obj;
				User user = UsersConfig.getUserByUUID(uuid);
				sb.append("   "+user.getNume()+"\n");
				user.getTPConf().removeIfNotUsed();
			}
		}else{
			if(u.equalsIgnoreCase("by") && args.length>=3) {
				
			}else {
				sb.append("INVALID: list by [role/ip/email/name] [itemToSearch] [page]");
			}
		}
		return sb.toString();
	}

	@Override
	public String execute(int lvlexec, String nume) {
		List<String> l = UsersConfig.getInstance().getYMLFileNameList();
		StringBuilder sb = new StringBuilder();
		if(l.size()==0) {
			sb.append("There are no registered users");
		}else if(l.size()==1) {
			sb.append("\n 1 user registered:\n");
			sb.append("   "+UsersConfig.getUserByUUID(l.get(0)).getNume());
		}else{
			sb.append("\n "+l.size()+" users registered:\n");
			for(Object obj : RUtil.getPageFromList(l, 1, 10)) {
					String uuid = (String) obj;
					User user = UsersConfig.getUserByUUID(uuid);
					sb.append("   "+user.getNume()+"\n");
					user.getTPConf().removeIfNotUsed();
				}
			if(l.size()>10) {
				sb.append("If you want to see next page, type 'list 2'");
			}
		}
		return sb.toString();
	}

	@Override
	public String help() {
		return "List users or search common things(list by ...)";
	}

}
