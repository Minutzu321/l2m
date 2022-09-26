package me.minutz.l2m.site;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.L2MSystem;
import me.minutz.l2m.util.DateUtil;

public class Program{
  private String titlu,content,id;
  private int importanta;
  private Date expdate;

  public Program(JSONObject o){
    try{
      this.titlu = o.getString("titlu");
      this.content = o.getString("content");
      this.importanta = o.getInt("importanta");
      this.id = o.getString("id");
      if(o.getString("expira") != null){
        this.expdate = DateUtil.stringToDate(o.getString("expira"));
      }else{
        this.expdate = null;
      }
    }catch(JSONException e){
      e.printStackTrace();
      L2MSystem.logger.severe(e.toString());
    }
  }

  public Program(String titlu,String content,int importanta, Date expdate){
    this.titlu = titlu;
    this.content = content;
    this.importanta = importanta;
    this.id = titlu.length()+"-"+content.length()+"-"+Calendar.getInstance().getTime().toString();
    this.expdate = expdate;
  }

  public Program(String titlu,String content,int importanta){
    this.titlu = titlu;
    this.content = content;
    this.importanta = importanta;
    this.id = titlu.length()+"-"+content.length()+"-"+Calendar.getInstance().getTime().toString();
  }
  public String getTitlu() {
    return titlu;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public void setTitlu(String titlu) {
    this.titlu = titlu;
  }
  public void setImportanta(int importanta) {
    this.importanta = importanta;
  }
  public int getImportanta() {
    return importanta;
  }
  public String getId() {
    return id;
  }
  public void setExpDate(Date expdate) {
    this.expdate = expdate;
  }
  public Date getExpDate() {
    return expdate;
  }
  public JSONObject getJSON(){
    JSONObject j = new JSONObject();
    try{
      if(expdate != null){
        j.append("expira",DateUtil.dateToString(expdate));
      }else{
        j.append("expira",null);
      }
      j.append("importanta", importanta);
      j.append("titlu", titlu);
      j.append("content", content);
      j.append("id", id);
    }catch(JSONException e){
      e.printStackTrace();
      L2MSystem.logger.severe(e.toString());
    }
    return j;
  }
}