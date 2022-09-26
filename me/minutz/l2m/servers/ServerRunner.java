package me.minutz.l2m.servers;

public class ServerRunner {
	private MServer server;
	private Thread thread;
	public ServerRunner(MServer server) {
		this.server = server;
		thread = new Thread(server.nume+" - thread"){
			public void run(){
				while(true){
				server.run();
				}
			}
		};
		thread.start();
	}
	public MServer getServer() {
		return server;
	}
	public Thread getThread() {
		return thread;
	}
}
