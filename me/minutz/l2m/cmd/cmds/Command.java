package me.minutz.l2m.cmd.cmds;

public abstract class Command {
	
	private String cmd;
	private int lvlacces;
	
	public Command(String cmd,int lvlacces){
		this.cmd = cmd;
		this.lvlacces=lvlacces;
	}
	
	
	public String getCmd() {
		return cmd;
	}
	public int getLvlacces(){
		return lvlacces;
	}
	
	public abstract String execute(String[] args,int lvlexec,String nume);
	public abstract String execute(int lvlexec,String nume);
	public abstract String help();

}
