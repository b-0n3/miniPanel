package application.models;

public class apiReq extends Api {

	public apiReq(String url) {
		super(url);
	}
	
	public String addArticlereq(String filename)
	public String LoginReq(String username , String password) {
		String res = "";
		addProperty("username",  username);
			addProperty("password", password);
			res = send();
		return res;
	}
}
