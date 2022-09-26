package me.minutz.l2m.procese;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.minutz.l2m.android.robot.Robot;
import me.minutz.l2m.config.jocuri.JocuriConfig;
import me.minutz.l2m.config.post.PostConfig;
import me.minutz.l2m.config.user.UsersConfig;
import me.minutz.l2m.procese.ha.Proces;
import me.minutz.l2m.procese.ha.ProcessType;
import me.minutz.l2m.site.Aplicatie.A_STATUS;
import me.minutz.l2m.site.posts.Post;
import me.minutz.l2m.site.users.Grad;
import me.minutz.l2m.site.users.Membru;
import me.minutz.l2m.site.users.User;
import me.minutz.l2m.util.RUtil;

public class RequestDataProces extends Proces{

	public RequestDataProces() {
		super(ProcessType.REQUEST_DATA);
	}

	public enum DataType{
		GRADE,
		GRAD,
		PROFIL,
		USER_POSTS,
		HOME_POSTS,
		MEMBRI,
		SPONSORI,
		PROGRAM,
		ROBOT_INFO,
		ROBOT_CONFIG,
		APLICANTI,
		JOC;
	}

	public DataType getDataType(String arg) {
		for(DataType dt : DataType.values()){
			if(dt.toString().equalsIgnoreCase(arg)){
				return dt;
			}
		}
		return null;
	}

	@Override
	public JSONObject execute(String[] args) throws JSONException{
		DataType dataType = getDataType(args[0]);
		String arg = args[1];
		String sviewer = args[2];
		User viewer = null;
		if(!sviewer.equals("-1")){
			viewer=UsersConfig.getUserByUUID(sviewer);
			if(viewer != null) {
				viewer.updateLastActivity(true);
			}
		}
		JSONObject raspuns = new JSONObject();
		switch(dataType){
			case GRADE:
				List<String> grade = new ArrayList<String>();
				raspuns.accumulate("status", true);
				grade.add(Grad.MEDIA.getJSON().toString());
				grade.add(Grad.DESIGNER.getJSON().toString());
				grade.add(Grad.MECANIC.getJSON().toString());
				grade.add(Grad.PROGRAMATOR.getJSON().toString());
				grade.add(Grad.VOLUNTAR.getJSON().toString());
				raspuns.accumulate("grade", new JSONArray(grade));
				if(viewer != null && viewer.getAplicatie() != null) {
					raspuns.accumulate("deja", true);
					raspuns.accumulate("arol", viewer.getAplicatie().grad);
				}else {
					raspuns.accumulate("deja", false);
				}
				break;
			case GRAD:
				JSONObject qqg = null;
				for(Grad rgrad : Grad.values()){
					if(rgrad.getNume().equalsIgnoreCase(arg)){
						qqg = rgrad.getJSON();
						break;
					}
				}
				if(qqg != null){
					raspuns.accumulate("status", true);
					raspuns.accumulate("grad", qqg.toString());
				}else{
					raspuns.accumulate("status", false);
				}
				break;
			case PROFIL:
				User u = UsersConfig.getUserByUUID(arg);
				if(u!=null && u.isMembru()){
					JSONObject q = u.getMembru().getMembruJSON();
					q.accumulate("status",true);
					return q;
				}else{
					raspuns.accumulate("status", false);
					raspuns.accumulate("mesaj", "Eroare. Profilul nu a fost gasit");
				}
				break;
			case USER_POSTS:
				List<String> postari = new ArrayList<String>();
				User us = UsersConfig.getUserByUUID(arg);
				if(us!=null && us.isMembru()){
					for(String puid : us.getMembru().getPosts()){
						Post post = PostConfig.getPostByID(puid);
						if(post!=null&&post.getImages().size()>0){
							JSONObject o = new JSONObject();
							o.append("imagine", post.getImages().get(0));
							o.append("titlu", post.getTitle());
							String content = post.getContent();
							if(content.length()>133){
								content = content.substring(0, 133)+"...";
							}
							o.append("content", content);
							postari.add(o.toString());
						}
					}
					raspuns.accumulate("postari", postari);
				}else{
					raspuns.accumulate("status", false);
					raspuns.accumulate("mesaj", "Eroare. Profilul nu a fost gasit");
				}
				break;
			case HOME_POSTS:
				int pag = Integer.parseInt(arg);
				if(PostConfig.getInstance().getYMLFileNameList().size()>0){
				List<String> ist = (List<String>)RUtil.getPageFromList(PostConfig.getInstance().getYMLFileNameList(), pag, 6);
					List<String> qi = new ArrayList<String>();
					for(String s:ist){
						Post pi = PostConfig.getPostByID(s);
						if(pi!=null){
							qi.add(pi.getJSON().toString());
						}
					}
					raspuns.accumulate("status", true);
					raspuns.accumulate("postari", qi);
				}else{
					raspuns.accumulate("status", false);
					raspuns.accumulate("mesaj", "Nu sunt postari");
				}
				break;
			case MEMBRI:
				List<String> usrs = new ArrayList<String>();
				for(Grad grad : Grad.values()){
					if(grad != Grad.USER && grad != Grad.VOLUNTAR && grad != Grad.SPONSOR){
						for(User guser:UsersConfig.getUsersByGrad(grad,false)){
							JSONObject jo = new JSONObject();
							jo.append("nume",guser.getNume());
							jo.append("grad",guser.getGrad().getNume());
							jo.append("descriere",guser.getMembru().getDescriere());
							jo.append("imagine",guser.getMembru().getImage());
							usrs.add(jo.toString());
						}
					}
				}
				if(usrs.size()>0){
					raspuns.accumulate("status", true);
					raspuns.accumulate("membri", usrs);
				}else{
					raspuns.accumulate("status", false);
					raspuns.accumulate("mesaj", "Nu sunt membri");
				}
				break;
			case SPONSORI:
				break;
			case PROGRAM:
				break;
			case JOC:
				raspuns.accumulate("status", true);
				raspuns.accumulate("mesaj", JocuriConfig.getJoc(arg).toString());
				break;
			case ROBOT_INFO:
				JSONObject w = new JSONObject();
				try{
					w.accumulate("tip", 2);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if(Robot.updateRobotData(w.toString())){
					raspuns.accumulate("status", true);
					raspuns.accumulate("mesaj", Robot.data);
					break;
				}
				raspuns.accumulate("status", false);
				raspuns.accumulate("mesaj", Robot.data);
				break;
			case ROBOT_CONFIG:
				if(Robot.config != null){
					raspuns.accumulate("status", true);
					raspuns.accumulate("mesaj", Robot.config);
				}else{
					raspuns.accumulate("status", false);
				}
				break;
			case APLICANTI:
				List<String> apl = new ArrayList<String>();
				for(User aplicant : UsersConfig.getAplicanti()) {
					if(aplicant.getAplicatie().status == A_STATUS.PENDING) {
						JSONObject q = new JSONObject();
						q.accumulate("id", aplicant.getUUID());
						q.accumulate("email", aplicant.getAplicatie().email);
						q.accumulate("gemail", aplicant.getAplicatie().gemail);
						q.accumulate("nume", aplicant.getAplicatie().nume);
						q.accumulate("gnume", aplicant.getAplicatie().gnume);
						q.accumulate("grad", aplicant.getAplicatie().grad);
						q.accumulate("clasa", aplicant.getAplicatie().clasa);
						apl.add(q.toString());
					}

				}
				if(apl.size()>0) {
					raspuns.accumulate("status", true);
					raspuns.accumulate("ap", apl);
				}else {
					raspuns.accumulate("status", false);
					raspuns.accumulate("mesaj", "Nimeni nu a aplicat");
				}
				int v = 0,mec=0,d=0,prog=0,med=0,ment=0;
				List<Membru> membri = UsersConfig.getMembri();
				for(User m : membri) {
					if(m.getGrad() == Grad.VOLUNTAR) {
						v++;
					}
					if(m.getGrad() == Grad.DESIGNER || m.getGrad() == Grad.DESIGNER_SEF) {
						d++;
					}
					if(m.getGrad() == Grad.MANAGER || m.getGrad() == Grad.MEDIA || m.getGrad() == Grad.MEDIA_SEF) {
						med++;
					}
					if(m.getGrad() == Grad.MECANIC || m.getGrad() == Grad.MECANIC_SEF) {
						mec++;
					}
					if(m.getGrad() == Grad.OWNER || m.getGrad() == Grad.PROGRAMATOR || m.getGrad() == Grad.PROGRAMATOR_SEF) {
						prog++;
					}
					if(m.getGrad() == Grad.MENTOR) {
						ment++;
					}
				}
				raspuns.accumulate("membri", membri.size()-v);
				raspuns.accumulate("voluntari", v);
				raspuns.accumulate("mecanici", mec);
				raspuns.accumulate("designeri", d);
				raspuns.accumulate("programatori", prog);
				raspuns.accumulate("media", med);
				raspuns.accumulate("mentori", ment);
				break;
		}
		return raspuns;
	}
}
