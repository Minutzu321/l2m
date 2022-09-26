package me.minutz.l2m.procese;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.android.robot.Robot;
import me.minutz.l2m.cmd.Consola;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.ha.Proces;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.User;

public class RequestCommandProces extends Proces{

	public RequestCommandProces() {
		super(ProcessType.REQUEST_COMMAND);
	}

	public enum CommandType{
	    SET_ROBOT_DATA,
	    CONSOLE,
	}

	public CommandType getCmdType(String arg) {
		for(CommandType dt : CommandType.values()){
			if(dt.toString().equalsIgnoreCase(arg)){
				return dt;
			}
		}
		return null;
	}

	@Override
	public JSONObject execute(String[] args) throws JSONException{
		CommandType cmdType = getCmdType(args[0]);
		JSONObject raspuns = new JSONObject();
		switch(cmdType){
			case SET_ROBOT_DATA:
		        if(Robot.updateRobotData(args[1])){
		          raspuns.accumulate("status", true);
		          raspuns.accumulate("mesaj", "primit");
		          break;
		        }
		        raspuns.accumulate("status", false);
		        raspuns.accumulate("mesaj", "Nu e conectat robotul");
				break;
			case CONSOLE:
				User u = UsersConfig.getUserByUUID(args[2]);
				if(u != null && u.getParola().equals(args[3])) {
					raspuns.accumulate("status", true);
					raspuns.accumulate("mesaj", Consola.cmd(args[1], u.getGrad().getImportanta(), u.getNume()));
				}else {
					raspuns.accumulate("status", false);
					raspuns.accumulate("mesaj", "Eroare");
				}
				break;
		}
		return raspuns;
	}
}
