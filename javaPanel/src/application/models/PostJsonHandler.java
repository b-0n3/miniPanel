package application.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PostJsonHandler<T>  extends JsonHandler<T>{
	 
	@Override 
	public String build(List<T> posts) {
		 String req = "";
		 if (posts != null)
		 {
			 JSONObject all = new JSONObject();
			 
			 JSONArray array = new JSONArray();
			 for(Post ps :(List<Post>)posts ) {
			 
			 all.put("name", ps.getTitle());
			 all.put("content", ps.getContent());
			 all.put("user_id", ps.getOwnerid());
			 all.put("Categery", ps.getCategery());
			 all.put("id", ps.getId());
			// all.put("image", ps.getImage().getName());
			 
			
			
			 array.put(all);
			 }
			 req = array.toString();
			 }
		 return req;
	 }
	 @Override
	 public List<T> parse(String res) {
		 JSONObject js= new JSONObject(res);
		 JSONArray array = js.getJSONArray("data");
		 List<T> Posts = new ArrayList<T>();
	
		 array.forEach(ar->{
			 Posts.add((T)Post.fromJsonObject((JSONObject) ar));
		 });
		 
		 return Posts;
	 }

}
