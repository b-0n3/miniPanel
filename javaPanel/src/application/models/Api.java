package application.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Api {
	private HttpURLConnection 	con ;
	public Api(String url)
	{
		try {
			URL u = new URL(url);
			
			con = (HttpURLConnection) u.openConnection();
			con.setConnectTimeout(5000);
			con.setConnectTimeout(5000);
		
			} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addProperty(String key ,String value)
	{
		if (con != null)
		{
			con.addRequestProperty(key, value);
		}else
		{
			throw new IllegalArgumentException("Connection error !");
		}
	}
	public String send()
	{
		BufferedReader bf;
		StringBuilder res = new StringBuilder();
		
		if (con != null)
		{
			try {
			String line ;
			int status = con.getResponseCode();
			if (status > 299)
			{
				bf = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while ((line = bf.readLine()) != null)
					res.append(line);
				bf.close();
			}else
			{
				bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while((line = bf.readLine()) != null)
						res.append(line);
					bf.close();
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally {
				con.disconnect();
			}
		}
		else
			throw new IllegalArgumentException("Connection Error!");
			return res.toString();
	}
}
