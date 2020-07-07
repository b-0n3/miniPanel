package application.models;

public class test {
public static void main(String [] args)
{
	apiReq api = new apiReq("http://9fece465bd26.ngrok.io/api/auth/login/");

	api.addPostParam("email", "admi@root.com",false);
	api.addPostParam("password", "123456",false);

	System.out.println(api.send("POST",false));
	
	
}
}
