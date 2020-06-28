package application.models;

public class apiReq extends Api {

	public apiReq(String url) {
		super(url);
	}
	
	public String Articlereq(String filename ,Staff staff,String operation)
	{
		String res = "";
		addProperty("filename", filename);
		addProperty("cookie", staff.getCookie());
		addProperty("operation", operation);
		res = send();
		return res;
	}
	
	public String LoginReq(String username , String password) {
		String res = "";
		addProperty("username",  username);
			addProperty("password", password);
			res = send();
		return res;
	}
	
	public String testCookie(Staff staff)
	{
		String res = "";
		addProperty("cookie", staff.getCookie());
		return res;
	}
	
	
	public String simpleRequest()
	{
		String res = "";
		res = send();
		return res;
	}
}
