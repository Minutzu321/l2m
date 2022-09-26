package me.minutz.l2m.procese;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.procese.ha.Proces;
import me.minutz.l2m.procese.ha.ProcessType;

public class PingProces extends Proces{

	public PingProces() {
		super(ProcessType.PING);
	}

	@Override
	public JSONObject execute(String[] args) throws JSONException{
		JSONObject raspuns = new JSONObject();
		raspuns.accumulate("status", true);
		return raspuns;
	}
}
