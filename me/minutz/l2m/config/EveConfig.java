package me.minutz.l2m.config;

import me.minutz.l2m.L2MSystem;

public class EveConfig extends Configuratie{

	public EveConfig() {
		super(ConfTip.EVENIMENTE);
	}

	public static EveConfig getInstance() {
		return (EveConfig) L2MSystem.getConfig(ConfTip.EVENIMENTE);
	}
}
