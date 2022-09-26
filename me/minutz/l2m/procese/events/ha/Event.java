package me.minutz.l2m.procese.events.ha;

import me.minutz.l2m.procese.ha.ProcessType;

public class Event {

	private ProcessType type;
	public String[] args;
	
	public Event(ProcessType type, String[] args) {
		this.type = type;
		this.args = args;
	}

	public ProcessType getType() {
		return type;
	}

}
