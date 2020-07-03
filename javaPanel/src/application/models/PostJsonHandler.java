package application.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostJsonHandler<T>  extends JsonHandler<T>{
	 
	@Override 
	public String build(List<T> posts) throws JSONException, IOException {
		 String req = "";
		 if (posts != null)
		 {
			 JSONObject all = new JSONObject();
			 
			 JSONArray array = new JSONArray();
			 Post ps =(Post) posts.get(0);
			 System.out.println("before" +all);
			 all.put("title", ps.getTitle());
			 all.put("content", ps.getContent());
			 String tags = "";
			 
			 for(String s : ps.getTags())
				 tags = tags +"," + s ;
			 
			 all.put("tags", tags);
			 all.put("category_id", ps.getCategery().getId());
			 
			 all.put("post_id", ps.getId());
			 System.out.println("after addin " + all);
			 JSONObject img = Post.getImageJson(ps.getImage());
				all.put("img", img);
			
			// all.put("image", ps.getImage().getName());
			 
			
			
			 
			 
			 req = all.toString();
			 System.out.println("thsi is the req " + req);
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
