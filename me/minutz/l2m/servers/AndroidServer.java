package me.minutz.l2m.servers;

import java.io.IOException;
import java.net.Socket;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.android.AConn;
import me.minutz.l2m.android.AndroidListener;

public class AndroidServer extends MServer {

	public AndroidServer(int port) {
		super("android", port, L2MSystem.DEBUG);
	}

	@Override
	public void run() {
		try {
			if(started){
				Socket socket = serverSocket.accept();
				AConn c = new AConn(socket);
				if(!c.valid){
					c.close();
					return;
				}
        AndroidListener.addAConn(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
			L2MSystem.logger.severe(e.toString());
		}
  }
}