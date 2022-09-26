package me.minutz.l2m.util;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import me.minutz.l2m.L2MSystem;


public class GoogleMail {
	
	private static String username = "----------------", password = "-----------------";
	
public static void sendMail(String subiect,List<String> l,List<String> to) {
    	
    	new Thread("Google email") {
    		public void run() {
		        Properties props = new Properties();
		        props.put("mail.smtp.starttls.enable", "true");
		        props.put("mail.smtp.auth", "true");
		        props.put("mail.smtp.host", "smtp.gmail.com");
		        props.put("mail.smtp.port", "587");
		
		        Session session = Session.getInstance(props,
		          new javax.mail.Authenticator() {
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(username, password);
		            }
		          });
		
		        try {
		
		            Message message = new MimeMessage(session);
		            message.setFrom(new InternetAddress(username));
		            for(String c : to) {
			            message.addRecipients(Message.RecipientType.TO,
			                InternetAddress.parse(c));
		            }
		            message.setSubject(subiect);
		            StringBuilder g = new StringBuilder();
		            for(String qqw : l) {
		            	g.append(qqw);
		            }
		            message.setText(g.toString());
		            Transport.send(message);
		
		            System.out.println("A fost trimis un email catre "+to);
		
		        } catch (MessagingException e) {
		            L2MSystem.logger.severe(e.toString());
		        }
    		}
    }.start();;
    }

    public static void sendAuthMail(String cod,String to) {
    	
    	new Thread(to+" - mail verification") {
    		public void run() {
		        Properties props = new Properties();
		        props.put("mail.smtp.starttls.enable", "true");
		        props.put("mail.smtp.auth", "true");
		        props.put("mail.smtp.host", "smtp.gmail.com");
		        props.put("mail.smtp.port", "587");
		
		        Session session = Session.getInstance(props,
		          new javax.mail.Authenticator() {
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(username, password);
		            }
		          });
		
		        try {
		
		            Message message = new MimeMessage(session);
		            message.setFrom(new InternetAddress("serviciurw@gmail.com"));
		            message.setRecipients(Message.RecipientType.TO,
		                InternetAddress.parse(to));
		            message.setSubject("Verificare de email");
		            message.setText("A fost initiata o sesiune de inregistrare de pe acest email,"
		                + "\n\n Daca recunosti activitatea, introdu codul "+cod+"! \n\n\n\n-Minutz");
		
		            Transport.send(message);
		
		            System.out.println("Emailul de verificare a fost trimis catre "+to);
		
		        } catch (MessagingException e) {
		            throw new RuntimeException(e);
		        }
    		}
    }.start();;
    }
}