package me.minutz.l2m.procese.events.ha;

import me.minutz.l2m.procese.events.CheckCodEvent;
import me.minutz.l2m.procese.events.LoginEvent;
import me.minutz.l2m.procese.events.LogoutEvent;
import me.minutz.l2m.procese.events.PageLoadEvent;
import me.minutz.l2m.procese.events.RegisterEvent;
import me.minutz.l2m.procese.events.RequestDataEvent;

public interface EventListener {
	
	void onCheckCodEvent(CheckCodEvent e);
	void onPageLoadEvent(PageLoadEvent e);
	void onLoginEvent(LoginEvent e);
	void onLogoutEvent(LogoutEvent e);
	void onRegisterEvent(RegisterEvent e);
	void onRequestDataEvent(RequestDataEvent e);
}
