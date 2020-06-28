package application.models;

import java.util.List;

public  class CommentsJsonHandler<T> extends JsonHandler<T>{
	
	public CommentsJsonHandler() {
		super();
	}
	@Override 
	public String build() {
		 String req = "";
		 return req;
	 }
	 @Override
	 public List<T> parse(String res) {
		 return null;
	 }
}
