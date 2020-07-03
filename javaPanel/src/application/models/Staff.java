package application.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Staff {
	public static final String APILINK="http://7f1742c0a9ce.ngrok.io/api";
	private String Name,email;
	private int level;
	private int id;
	private File image;
	private Token token;
	private String facebookLink , linkinLink, gPlusLink,twitterLink;
	public static List<Categery> categerys;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	
	public Staff(String name, String email,int id, int level, Token token) {
		
		Name = name;
		this.email = email;
		this.id = id;
		this.level = level;
		this.token = token;
		categerys = new ArrayList<>();
	}

	public Staff(String name, String email, int id, int level, File image, Token token, String facebookLink,
			String linkinLink, String gPlusLink, String twitterLink) {
	
		Name = name;
		this.email = email;
		this.id=id;
		this.level = level;
		this.image = image;
		this.token = token;
		this.facebookLink = facebookLink;
		this.linkinLink = linkinLink;
		this.gPlusLink = gPlusLink;
		this.twitterLink = twitterLink;
	
	}
	public Staff(String name, String email, int id, int level, File image, String facebookLink,
			String linkinLink, String gPlusLink, String twitterLink) {
	
		Name = name;
		this.email = email;
		this.id=id;
		this.level = level;
		this.image = image;
		
		this.facebookLink = facebookLink;
		this.linkinLink = linkinLink;
		this.gPlusLink = gPlusLink;
		this.twitterLink = twitterLink;
		
}

	
	  
	

	public void handleCookie()
	{
		
	}
	

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	public String getFacebookLink() {
		return facebookLink;
	}
	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}
	public String getLinkinLink() {
		return linkinLink;
	}
	public void setLinkinLink(String linkinLink) {
		this.linkinLink = linkinLink;
	}
	public String getgPlusLink() {
		return gPlusLink;
	}
	public void setgPlusLink(String gPlusLink) {
		this.gPlusLink = gPlusLink;
	}
	public String getTwitterLink() {
		return twitterLink;
	}
	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email.matches(""))
		this.email = email;
	}

	

	
	public String getJsonObject() {
		JSONObject object = new JSONObject();
		
		return object.toString();
	}

	public static Staff fromJsonObject(JSONObject jso) {
	apiReq api = new apiReq(jso.getString("image_profile"));
	
	String path = api.send("GET", true);
		File image = new File(path);
Staff stf = new Staff(jso.getString("name"),
		jso.getString("email"),
		jso.getInt("id"),
		jso.getInt("user_level"),
		image,
		jso.getString("facebook"),
		jso.getString("linkedin"),
		jso.getString("google"),
		jso.getString("twitter"));
		return stf;
	}

	@Override
	public String toString() {
		return "Staff [Name=" + Name + ", email=" + email + ", level=" + level + ", id=" + id + ", image=" + image
				+ ", token=" + token + ", facebookLink=" + facebookLink + ", linkinLink=" + linkinLink + ", gPlusLink="
				+ gPlusLink + ", twitterLink=" + twitterLink + "]";
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
	
}
