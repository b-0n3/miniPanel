package application.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Categery {
	private int id;
	private String content;
	public Categery(int id, String content) {
		
		this.id = id;
		this.content = content;
	}

	
	@Override
	public String toString() {
		return "Categery [id=" + id + ", content=" + content + "]";
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public static List<Categery> fromJsonArray(JSONArray jsa){
		List<Categery> list = new ArrayList<>();
		jsa.forEach(e->{
			boolean d = false;
			for(Categery c : list)
			{
				if (c.id == ((JSONObject)e).getInt("id"))
				{
					d = true;
					break;
				}
				
			}
			if (!d)
			list.add(new Categery(((JSONObject)e).getInt("id"),((JSONObject)e).getString("category_name")));
		});
		return list;
	}


	public static Categery fromId(int int1) {
		for(Categery c :Staff.categerys)
			if(c.id == int1)
					return c;
		
		return new Categery(0, "new posts");
	}


	public static Categery newCategery(String value, Token token) {
		apiReq api = new apiReq(Staff.APILINK + "categery/newcategery");
		token.addAuthorization(api);
		api.addPostParam("categeryname", value, false);
		int id = new JSONObject(api.send("GET",false)).getInt("id");
		return new Categery(id, value);
	}
}
