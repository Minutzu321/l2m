package me.minutz.l2m.procese.ha;

public enum ProcessType {
	
	PING("Ping","ping"),
	CHECK_COD("CheckCode","checkcod"),
	CHECK_USER_DATA("CheckUser","checkuser"),
	LOGIN("Login","log"),
	REGISTER("Register","reg"),
	REQUEST_DATA("RequestData","rqdata"),
	REQUEST_COMMAND("RequestCommand","rqcmd"),
	GOOGLE("Google","gugal"),
	APLICA("Aplica","aplica"),
	UPLOAD("Upload","upl"),
	LOGOUT("Logout","lout");
	
	private String nume,cmd;
	private ProcessType(String nume, String cmd){
		this.nume = nume;
		this.cmd = cmd;
	}
	
	public String getNume() {
		return nume;
	}
	
	public String getCommand() {
		return cmd;
	}

}
