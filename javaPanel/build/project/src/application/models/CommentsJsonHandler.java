package application.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public  class CommentsJsonHandler<T> extends JsonHandler<T>{
	
	public CommentsJsonHandler() {
		super();
	}
	@Override 
	public String build(List<T> element) {
		 String req = "";
		 //Comment cmt = new Comment();
		 return req;
	 }
	 @SuppressWarnings("unchecked")
	@Override
	 public List<T> parse(String res) {
		 List<T> comments = new ArrayList();
		 JSONArray arr = new JSONArray(res);
		//[{"name":"JEO SMITH","email":"azdazhdui@dazdaz.fr",
		 //"website":"http:\/\/www.google.com\/",
		 //"content":"hello"}] 
		 arr.forEach(js->{
			 comments.add((T)new Comment(((JSONObject)js).getString("website")
					 ,((JSONObject)js).getString("name")
					 ,((JSONObject)js).getString("email"),
					 ((JSONObject)js).getString("content")					 
					 ));
			 
		 });
		 return comments;
	 }
}
