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
			 JSONObject holder = new JSONObject();
			 JSONArray array = new JSONArray();
			 for(Post ps :(List<Post>)posts ) {
			 
			 all.put("name", ps.getName() );
			 all.put("Content", ps.getContent());
			 all.put("owner", ps.getOwner().getJsonObject());
			 all.put("Categery", ps.getCategery());
			 all.put("id", ps.getId());
			 
			
			 holder.put("Post", all);
			 array.put(holder);
			 }
			 req = array.toString();
			 }
		 return req;
	 }
	 @Override
	 public List<T> parse(String res) {

		 JSONArray array = new JSONArray(res);
		 List<Post> Posts = new ArrayList<Post>();
	
		 array.forEach(ar->{
//			 JSONObject post = (JSONObject)((JSONObject)ar).get("Post");
//			Posts.add(new Post((String)post.get("name"),(String)post.get("Content"),
//					Staff.fromJsonObject((JSONObject)post.get("owner"))
//					,(String)post.get("id") , (String)post.get("Categery")));
			
		 });
		 
		 return  (List<T>) Posts;
	 }

}
