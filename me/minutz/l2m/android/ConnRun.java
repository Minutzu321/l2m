package me.minutz.l2m.android;

public class ConnRun {

	private Thread thread;
	public boolean stop=false;
	public ConnRun(AConn c) {
		thread = new Thread("ConnRun"+c.getSocket().getPort()+" - thread"){
			public void run(){
				while(!stop){
				  c.run();
				}
			}
		};
		thread.start();
	}
	public Thread getThread() {
		return thread;
	}
}
