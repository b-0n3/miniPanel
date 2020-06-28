package application.models;
import java.util.List;

import org.json.*;


abstract class JsonHandler<T> {
	private JSONArray array;
	public JsonHandler()
	{
		array = new JSONArray();
	}
	
	 abstract String build();
	 abstract List<T> parse(String res);
	
}
