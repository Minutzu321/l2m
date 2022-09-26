package me.minutz.l2m.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import me.minutz.l2m.L2MSystem;

public class HtmlUtil {
	
	public static String getSiteContent(String a){
	    URLConnection connection = null;
	    StringBuilder sb = new StringBuilder();
		try {
			connection = new URL(a).openConnection();
		    connection
            .setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		    connection.connect();
		    BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(),
		            Charset.forName("UTF-8")));
		    String line;
		    while ((line = r.readLine()) != null) {
		        sb.append(line);
		    }
		} catch (IOException e) {
			e.printStackTrace();
			L2MSystem.logger.severe(e.toString());
		}
	    return sb.toString();
	}

}
