package me.minutz.l2m.cmd.cmds;

import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.site.Aplicatie;
import me.minutz.l2m.site.Aplicatie.A_STATUS;
import me.minutz.l2m.site.Chestie;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.User;

public class ProcessCommand extends Command{

	public ProcessCommand() {
		super("process", Grad.USER.getImportanta());
	}

	@Override
	public String execute(String[] args, int lvlexec, String nume) {
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("uuid")) {
				return c(UsersConfig.getUserByUUID(args[1]),args[2],lvlexec,nume);
			}else if(args[0].equalsIgnoreCase("nume")) {
				return c(UsersConfig.getUserByNume(args[1], false),args[2],lvlexec,nume);
			}else if(args[0].equalsIgnoreCase("chestie")) {
				User u = UsersConfig.getUserByUUID(args[1]);
				if(u != null) {
					Chestie c = Chestie.getChestieByUser(args[1]);
					if(c != null) {
						String s = args[2];
						if(s.equalsIgnoreCase("ACCEPTA")) {
							c.onAccept();
							Chestie.chestii.remove(c);
							return "Acceptat";
						}else if(s.equalsIgnoreCase("REFUZA")){
							c.onRefuz();
							Chestie.chestii.remove(c);
							return "Refuzat";
						}else {
							return "Nu ai scris bine";
						}
					}else {
						return "Chestia nu a fost gasita";
					}
				}else {
					return "Userul nu a fost gasit";
				}
			}else{
				return "Primul argument trebuie sa fie uuid/nume";
			}
		}
		return "Trebuie sa ai 3 argumente dupa comanda";
	}
	
	private String c(User us, String r, int l, String nume) {
		Aplicatie a = us.getAplicatie();
		if(a != null) {
			if(a.status != A_STATUS.PENDING) {
				return "Cererea a fost deja procesata de "+a.exec;
			}
			a.exec = nume;
			if(r.equalsIgnoreCase("accepta")) {
				Grad g = getG(a.grad);
				us.setGrad(g, false);
				us.setNume(a.nume, false);
				us.setEmail(a.email, false);
				a.status = A_STATUS.ACCEPTAT;
				us.setAplicatie(a.toJSON().toString(), false);
				us.save();
				return "Userul a fost acceptat";
			}else if(r.equalsIgnoreCase("refuza")) {
				a.status = A_STATUS.RESPINS;
				us.setAplicatie(a.toJSON().toString(), true);
				return "Userul a fost respins";
			}else if(r.toLowerCase().contains("refuza-")) {
				String sg = r.toLowerCase().split("-")[1];
				us.setNume(a.nume, false);
				us.setEmail(a.email, false);
				Grad g = getG(sg);
				a.grad=g.getNume();
				a.status = A_STATUS.PENDING_USER;
				us.setAplicatie(a.toJSON().toString(), true);
				return "Userului o sa i se zica sa treaca pe rolul "+g.getNume();
			}else if(r.equalsIgnoreCase("ban")) {
				us.setBanat(true, true);
				return "Userul a fost banat";
			}
			
		}
		return "Userul nu a aplicat";
	}
	
	public static Grad getG(String q) {
		for(Grad g : Grad.values()) {
			if(g.toString().equalsIgnoreCase(q)) {
				return g;
			}
		}
		return null;
	}

	@Override
	public String execute(int lvlexec, String nume) {
		return "INVALID: "+help();
	}

	@Override
	public String help() {
		return "process chestie/uuid/nume <VALOARE> ACCEPTA/REFUZA";
	}

}
