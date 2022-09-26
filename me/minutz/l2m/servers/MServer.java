package me.minutz.l2m.servers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.dosse.upnp.UPnP;

import me.minutz.l2m.L2MSystem;

public abstract class MServer {
	public int port;
	public String nume;
	public ServerSocket serverSocket;
	public boolean started,debug;
	
	public MServer(String nume,int port,boolean debug){
		this.port = port;
		this.nume = nume;
		this.debug = debug;
	}
	
	public void start(){
        try {
        	UPnP.openPortTCP(port);
			serverSocket = new ServerSocket(port);
			started=true;
			if(debug){
				System.out.println("!Serverul "+nume+" a fost deschis pe portul "+port);
			}
		} catch (IOException e) {
			started=false;
			L2MSystem.logger.severe(e.toString());
			if(debug){
				System.out.println("!Serverul "+nume+" nu a putut porni pe portul "+port);
				e.printStackTrace(System.err);
			}
		}
	}
	
	public void stop(){
		started=false;
		UPnP.closePortTCP(port);
	    if (serverSocket != null && !serverSocket.isClosed()) {
	        try {
	            serverSocket.close();
	            System.out.println("!Serverul "+nume+" a fost inchis");
	        } catch (IOException e)
	        {
	        	L2MSystem.logger.severe(e.toString());
	            e.printStackTrace(System.err);
	        }
	    }
	}
	
	public abstract void run();
	
}