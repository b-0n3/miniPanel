package application.models;

import java.sql.Date;
import com.sun.jmx.snmp.Timestamp;

import org.json.JSONObject;

public class Token {
	private String token;
	private long deadline;
	private String type;
	public Token(String token, long deadline, String type) {
		Timestamp tm = new Timestamp();
		this.token = token;
		this.deadline =tm.getDateTime() + deadline *1000;
		System.out.println(this.deadline);
		this.type = type;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
		
	}
	public long getDeadline() {
		return deadline;
	}
	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static Token fromJsonObject(JSONObject jso)
	{
		Token token = null;
		if (jso  != null)
		{
			String tok = (String )jso.get("access_token");
			String type = (String )jso.get("token_type");
			int exp_time = (int) jso.get("expires_in");
			token = new Token(tok,exp_time, type);
			return token;
		}
		return token;
	}
}
