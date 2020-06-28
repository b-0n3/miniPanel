package application.models;

import java.util.List;

public class PostJsonHandler<T>  extends JsonHandler<T>{
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
