package me.minutz.l2m.servers;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import me.minutz.l2m.L2MSystem;

public class FileServer extends MServer{

	public FileServer(int port) {
		super("fileserver", port, L2MSystem.DEBUG);
	}

	@Override
	public void run() {
	       Socket socket = null;
	        InputStream in = null;
	        OutputStream out = null;

	        try {
		        socket = serverSocket.accept();
		            
		        in = socket.getInputStream();
		        DataInputStream dis = new DataInputStream(in);
		        String nume = dis.readUTF();
		        System.out.println("!"+nume);
		        out = new FileOutputStream("E:\\SVL\\Develop\\mlamdatabase\\"+nume);
		        	
		        byte[] bytes = new byte[16 * 1024];//16 KB/milisecunda
	
		        int count;
		        while ((count = in.read(bytes)) > 0) {
		            out.write(bytes, 0, count);
		        }
	
		        out.close();
		        in.close();
		        socket.close();
		        System.out.println("!IMPORTAT - "+nume);
	        }catch(Exception e) {
	        	
	        	e.printStackTrace();
	        	try {
			        out.close();
			        in.close();
			        socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	        }
	}

}
