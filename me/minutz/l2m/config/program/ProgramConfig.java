package me.minutz.l2m.config.program;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.file.YamlConfiguration;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.config.PConfig;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.site.Program;
import me.minutz.l2m.util.DateUtil;

public class ProgramConfig {
  private static PConfig pconf;
	  
  public static void load(){
    try{
        File file = new File(L2MSystem.configPath+"/program.yml");
        YamlConfiguration yc = new YamlConfiguration();
			  pconf = new PConfig(file,yc);
      }catch (Exception localException){
        localException.printStackTrace();
      }
  }

  public static void checkProgram(){
    List<String> lista = pconf.getFC().getStringList("program"),lista2;
    lista2 = lista;
    if(lista==null){
      return;
    }
    for(String sj : lista){
      try{
        JSONObject o = new JSONObject(sj);
        if(o.getString("expira")!=null){
          if(DateUtil.stringToDate(o.getString("expira")).after(Calendar.getInstance().getTime())){
            lista2.remove(sj);
          }
        }
      } catch(JSONException e){
        e.printStackTrace();
      }
    }
    pconf.getFC().set("program", lista2);
    pconf.save();
  }

  public static void addProgram(Program p){
    List<String> lista = pconf.getFC().getStringList("program");
    if(lista ==null){
      lista = new ArrayList<String>();
    }
    lista.add(p.getJSON().toString());
    pconf.getFC().set("program", lista);
    pconf.save();
  }
  
  public static void remProgram(String id){
    List<String> lista = pconf.getFC().getStringList("program");
    if(lista==null){
      return;
    }
    for(String sj : lista){
      try{
        JSONObject o = new JSONObject(sj);
        if(o.getString("id").equals(id)){
          lista.remove(sj);
          break;
        }
      } catch(JSONException e){
        e.printStackTrace();
      }
    }
  }
}