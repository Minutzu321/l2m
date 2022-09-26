package me.minutz.l2m;

import me.minutz.l2m.procese.events.CheckCodEvent;
import me.minutz.l2m.procese.events.LoginEvent;
import me.minutz.l2m.procese.events.LogoutEvent;
import me.minutz.l2m.procese.events.PageLoadEvent;
import me.minutz.l2m.procese.events.RegisterEvent;
import me.minutz.l2m.procese.events.RequestDataEvent;
import me.minutz.l2m.procese.events.ha.EventListener;

public class EventHandler implements EventListener{

	@Override
	public void onRequestDataEvent(RequestDataEvent e) {

	}

	@Override
	public void onCheckCodEvent(CheckCodEvent e) {
		
	}

	@Override
	public void onPageLoadEvent(PageLoadEvent e) {

	}

	@Override
	public void onLoginEvent(LoginEvent e) {
	}

	@Override
	public void onLogoutEvent(LogoutEvent e) {
		
	}

	@Override
	public void onRegisterEvent(RegisterEvent e) {
		
	}

}
