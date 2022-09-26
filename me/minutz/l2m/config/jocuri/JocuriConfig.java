package me.minutz.l2m.config.jocuri;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.config.user.UsersConfig;

public class JocuriConfig {
	
	public static File folder = new File(L2MSystem.configPath+"/jocuri");

	public static JSONArray getJoc(String in) {
		int i = 0;
		List<String> sl = new ArrayList<>();
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
		Random r = new Random();
		for(int id=0;id<r.nextInt(10);id++)
			Collections.shuffle(files);
		for(File f:files) {
			String c = readFile(f.getPath());
			if(in.contains(" ")) {
				String[] cs = in.split(" ");
				for(String cuv : cs) {
					if(c.toLowerCase().contains(cuv.toLowerCase())) {
						if(!sl.contains(c)) {
							sl.add(c);
						}
					}
				}
			}else {
				if(in.toLowerCase().contains("random")) {
					if(i>7) break;
					if(!sl.contains(c)) {
						sl.add(c);
						i++;
					}
					
				}else if(c.toLowerCase().contains(in)) {
					if(!sl.contains(c)) {
						sl.add(c);
					}
				}
			}
		}
		List<JSONObject> a = new ArrayList<>();
		for(String jo : sl) {
			try {
				a.add(new JSONObject(jo));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return new JSONArray(sl);
	}
	
	static String readFile(String path)
			{
			  byte[] encoded = null;
			try {
				encoded = Files.readAllBytes(Paths.get(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			  return new String(encoded);
			}

}
