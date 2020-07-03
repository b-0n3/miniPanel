package application.models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.media.sound.JavaSoundAudioClip;

public class StaffJsonHandler<T> extends JsonHandler<T> {
	
	
	
	
	
	
	
	
	
	
	@Override 
	public String build(List<T> elemt) {
	
		 String req = "";
		 
		 JSONArray jsa = new JSONArray();
		 elemt.forEach(s->{
			JSONObject jso = new JSONObject();
			Staff staff = (Staff)s;
			
			jso.put("name",staff.getName());
			jso.put("email", staff.getEmail());
			jso.put("id", staff.getId());
			jso.put("user_level",staff.getLevel());
			jso.put("password", staff.getPassword());
			try {
				jso.put("img", Post.getImageJson(staff.getImage())).toString();
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jso.put("facebook",staff.getFacebookLink());
			jso.put("twitter", staff.getTwitterLink());
			jso.put("linkedin",staff.getLinkinLink());
			jso.put("google", staff.getgPlusLink());
			jsa.put(jso);
		 });
		 return jsa.get(0).toString();
	 }
	 @Override
	 public List<T> parse(String res) {
		 ArrayList<Staff>  stf = new ArrayList<>();
		 JSONArray jso = new JSONArray(res);
		 jso.forEach(s->{
			 stf.add(Staff.fromJsonObject((JSONObject)s));
		 });
		 return (List<T>) stf;
	 }
}
