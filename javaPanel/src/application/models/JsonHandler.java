package application.models;
import java.io.IOException;
import java.util.List;

import org.json.*;


abstract class JsonHandler<T> {
	private JSONArray array;
	public JsonHandler()
	{
		array = new JSONArray();
	}
	
	 abstract String build(List<T> element) throws JSONException, IOException;
	 abstract List<T> parse(String res);
	public JSONArray getArray()
	{
		return this.array;
	}
	public void addElement(JSONObject element)
	{
		if (element != null)
		{
			this.array.put(element);
		}
		
	}
}
