package me.minutz.l2m.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.android.robot.Robot;

public class AConn {
  public Socket socket;
  public InputStream is;
  public InputStreamReader isr;
  public BufferedReader br;
  public OutputStream os;
  public PrintWriter pw;
  public boolean valid = false,closed=false;
  public ConnRun connRun;
  public boolean eRobot = false;

  public AConn(Socket socket){
    this.socket = socket;
    try{
      is = socket.getInputStream();
      isr = new InputStreamReader(is);
      br = new BufferedReader(isr);
      String cmd = br.readLine();
			if(cmd.startsWith(L2MSystem.parola)){
        valid=true;
        if(cmd.replace(L2MSystem.parola, "").equals("suntRobot")){
          eRobot = true;
          System.out.println("Robotul a fost conectat la server");
        }
        System.out.println("Acceptat "+socket.getPort());
      }else{
        System.out.println("Respins "+socket.getPort());
      }
      os = socket.getOutputStream();
			pw = new PrintWriter(os, true);
    }catch(IOException e){
      e.printStackTrace();
    }
    if(valid){
      connRun = new ConnRun(this);
    }
  }

  public void run(){
    try{
      String cmd = br.readLine();
      if(cmd==null){
        close();
        return;
      }
      AndroidListener.onMesaj(cmd,this);
    }catch(IOException e){
      close();
      e.printStackTrace();
    }
  }

  public void send(String s){
    pw.println(s);
    pw.flush();
  }

  public Socket getSocket(){
    return socket;
  }

  public void close(){
    AndroidListener.conns.remove(this);
    if(eRobot) {
	    System.out.println("Conexiunea cu robotul a fost inchisa");
	    Robot.config = null;
	    Robot.data = "DECONECTAT";
    }
    closed = true;
    connRun.stop = true;
    pw.close();
    try{
      br.close();
      isr.close();
      is.close();
      os.close();
      socket.close();
    }catch(Exception e){}
  }

}