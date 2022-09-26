package me.minutz.l2m.servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.config.program.ProgramConfig;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.ha.ProcesHandler;

public class WebServer extends MServer{

	public WebServer(int port) {
		super("web", port, L2MSystem.DEBUG);
	}

	@Override
	public void run() {
		try {
			if(started){
				Socket socket = serverSocket.accept();
				UsersConfig.checkTUsers();
				ProgramConfig.checkProgram();
				runRequest(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
			L2MSystem.logger.severe(e.toString());
		}
	}
	
	private void runRequest(Socket socket){
		new Thread(socket.getInetAddress().getHostAddress()+":"+socket.getPort()+" - request Thread") {
			public void run() {
				try {
			        InputStream is = socket.getInputStream();
			        InputStreamReader isr = new InputStreamReader(is);
			        BufferedReader br = new BufferedReader(isr);
			        String cmd = br.readLine();
			        
			        String returnMessage = "SecProtocol:1";
			        if(cmd.startsWith(L2MSystem.parola)){
			        	returnMessage = getResp(cmd.substring(L2MSystem.parola.length()));
			        }
			        
			        OutputStream os = socket.getOutputStream();
			        PrintWriter pw = new PrintWriter(os, true);
			        pw.println(returnMessage);
			        pw.flush();
			        
					is.close();
					isr.close();
					br.close();
					os.close();
					pw.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
					L2MSystem.logger.severe(e.toString());
				}
			}
		}.start();
	}

	public String getResp(String s){
			try{
				JSONObject data = new JSONObject(s);
				String cmd = data.getString("cmd");
				JSONArray argz = data.getJSONArray("args");
				String[] args = new String[argz.length()];
				for(int i=0;i<argz.length();i++){
					args[i]=String.valueOf(argz.get(i));
				}
				if(debug) {
					System.out.println("\n"+Calendar.getInstance().getTime().toString());
					System.out.println("CLIENT: "+data.toString());
				}
				String raspuns = ProcesHandler.executeProces(cmd, args);
				if(debug) {
					System.out.println("SERVER: "+raspuns+"\n");
				}
				if(raspuns != null) {
					return raspuns;
				}
			}catch(JSONException e){
				e.printStackTrace();
				L2MSystem.logger.severe(e.toString());
			}
	    
		return "erroare:O eroare neasteptata a fost detectata la executia comenzii "+s;
	}
	
}
