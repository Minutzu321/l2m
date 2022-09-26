package me.minutz.l2m.procese.events;

import me.minutz.l2m.procese.RequestDataProces.DataType;
import me.minutz.l2m.procese.events.ha.Event;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.users.User;

public class RequestDataEvent extends Event{

	private User user;
	private DataType dataType;

	public RequestDataEvent(String[] args,DataType dataType, User viewer) {
		super(ProcessType.REQUEST_DATA, args);
		this.dataType = dataType;
		this.user = viewer;
	}

	public User getUser() {
		return user;
	}

	public DataType getDataType(){
		return dataType;
	}

}
