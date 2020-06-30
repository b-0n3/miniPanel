package application.models;

import java.util.List;

public class StaffJsonHandler<T> extends JsonHandler<T> {
	@Override 
	public String build(List<T> elemt) {
		 String req = "";
		 return req;
	 }
	 @Override
	 public List<T> parse(String res) {
		 return null;
	 }
}
