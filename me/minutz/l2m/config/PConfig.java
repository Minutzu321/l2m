package me.minutz.l2m.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.yaml.snakeyaml.file.FileConfiguration;

public class PConfig {
	
	private File file;
	private FileConfiguration fconf;
	  
	public PConfig(File file, FileConfiguration fconf) {
		this.file = file;
		this.fconf = fconf;
	    if (!file.exists())
	    {
	      file.getParentFile().mkdirs();
	      copy(null, file);
	    }
	    try {
		      fconf.load(file);
		    }catch (Exception e){}
	}
	
	  private static void copy(InputStream in, File file)
	  {
	    try
	    {
	      OutputStream out = new FileOutputStream(file);
	      byte[] buffer = new byte[63];
	      int len;
	      while ((len = in.read(buffer)) > 0)
	      {
	        out.write(buffer, 0, len);
	      }
	      out.close();
	      in.close();
	    }
	    catch (Exception localException)
	    {
	    }
	  }

	public File getFile() {
		return file;
	}

	public void setFile(File playerFile) {
		this.file = playerFile;
	}

	public FileConfiguration getFC() {
		return fconf;
	}

	public void setUser(FileConfiguration player) {
		this.fconf = player;
	}
	  
	public void save(){
		try {
			fconf.save(file);
		} catch (IOException e) {
		}
	}
	
	public void delete() {
		if(!file.delete()) {
			file.deleteOnExit();
		}
	}
	

}
