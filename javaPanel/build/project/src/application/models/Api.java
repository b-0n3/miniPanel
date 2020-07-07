package application.models;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Api {
	private HttpURLConnection 	con ;
	private StringBuilder postData ;
	public Api(String url)
	{
		postData = new StringBuilder();
		try {
			URL u = new URL(url);
			
			con = (HttpURLConnection) u.openConnection();
			con.setConnectTimeout(10000);
			con.setConnectTimeout(10000);
		
			} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void reConnect(String url)
	{
		try {
			URL u = new URL(url);
			
			con = (HttpURLConnection) u.openConnection();
			con.setConnectTimeout(10000);
			con.setConnectTimeout(10000);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public void addProperty(String key ,String value)
	{
		if (con != null)
		{
			con.setRequestProperty(key, value);
		}else
		{
			throw new IllegalArgumentException("Connection error !");
		}
	}
	public void addPostParam(String key ,String value,boolean isJson)
	{
		
		if (con != null)
		{
			if (isJson)
			postData.append(value);
				else {
			if(postData.length() != 0)
				postData.append("&");
			postData.append(key);
			postData.append("=");
			postData.append(value);
			}
		}else
		{
			throw new IllegalArgumentException("Connection error !");
		}
	}
	public String send(String method , boolean isImage)
	{
		BufferedReader bf;
		StringBuilder res = new StringBuilder();
		
		if (con != null)
		{
			try {
			String line ;
			con.setRequestMethod(method);
			if (method.equals("POST"))
			{
				con.setDoOutput( true );
				con.setInstanceFollowRedirects( false );
				con.setUseCaches( false );
				try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
					   wr.write( postData.toString().getBytes( StandardCharsets.UTF_8 ) );
					}
			}
			
			int status = con.getResponseCode();
			System.out.println(status);
			if (status > 299)
			{
				bf = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while ((line = bf.readLine()) != null)
				{
					res.append(line);
					res.append('\n');
				}
					bf.close();
					throw new IllegalArgumentException(res.toString());
			}else
			{
				
				if(isImage)
				{
					InputStream in = new BufferedInputStream(con.getInputStream());
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					int n = 0;
					while (-1!=(n=in.read(buf)))
					{
					   out.write(buf, 0, n);
					}
					out.close();
					in.close();
					byte[] response = out.toByteArray();
					String[] olfs = con.getURL().toString().split("/");
					String oldfilename = olfs[olfs.length -1];
					String path = getUniqueFileName("." + oldfilename);
					FileOutputStream fos = new FileOutputStream("./src/images/" + path);
					fos.write(response);
					fos.close();
					return "./src/images/" + path;
				}
				bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
					while((line = bf.readLine()) != null)
				{
						res.append(line);
						res.append('\n');
				}
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
	

	public String sendImage(File file,int postId) throws IOException
	{
		
		String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
	con.setDoOutput(true); // This sets request method to POST.
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		PrintWriter writer = null;
		try {
		    writer = new PrintWriter(new OutputStreamWriter(con.getOutputStream()));
		    if(postId != -404)
		    	writer.println("Post-id:" + postId);
		    writer.println("--" + boundary);
		    writer.println("Content-Disposition: form-data; name=\"picture\"; filename=\""+file.getName()+"\"");
		    writer.println("Content-Type: image/jpeg");
		   
		    writer.println();
		    BufferedReader reader = null;
		    try {
		        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		        for (String line; (line = reader.readLine()) != null;) {
		            writer.println(line);
		        }
		    } finally {
		        if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
		    }
		    writer.println("--" + boundary + "--");
		} finally {
		    if (writer != null) writer.close();
		}
		int status = con.getResponseCode();
		System.out.println(status);
		BufferedReader bf ;
		String line;
		StringBuilder res = new StringBuilder();
		if (status > 299)
		{
			bf = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			while ((line = bf.readLine()) != null)
			{
				res.append(line);
				res.append('\n');
			}
				bf.close();
				throw new IllegalArgumentException(res.toString());
		} else
		{
			bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while((line = bf.readLine()) != null)
			{
				res.append(line);
				res.append("\n");
			}
			bf.close();
		}
		con.disconnect();
		return res.toString();
	}
	
	 private String getUniqueFileName(String oldFileName)
	    {
	        String newName;
	        
	        //create a Random Number Generator
	        SecureRandom rng = new SecureRandom();
	        
	        //loop until we have a unique file name
	        do
	        {
	            newName = "";
	            
	            //generate 32 random characters
	            for (int count=1; count <=32; count++)
	            {
	                int nextChar;
	                
	                do
	                {
	                    nextChar = rng.nextInt(123);
	                } while(!validCharacterValue(nextChar));
	                
	                newName = String.format("%s%c", newName, nextChar);
	            }
	            newName += oldFileName;
	            
	        } while (!uniqueFileInDirectory(newName));
	        
	        return newName;
	    }
	    
	    
	    /**
	     * This method will search the images directory and ensure that the file name
	     * is unique
	     */
	    public boolean uniqueFileInDirectory(String fileName)
	    {
	        File directory = new File("./src/images/");
	        
	        File[] dir_contents = directory.listFiles();
	                
	        for (File file: dir_contents)
	        {
	            if (file.getName().equals(fileName))
	                return false;
	        }
	        return true;
	    }
	    
	    /**
	     * This method will validate if the integer given corresponds to a valid
	     * ASCII character that could be used in a file name
	     */
	    public boolean validCharacterValue(int asciiValue)
	    {
	        
	        //0-9 = ASCII range 48 to 57
	        if (asciiValue >= 48 && asciiValue <= 57)
	            return true;
	        
	        //A-Z = ASCII range 65 to 90
	        if (asciiValue >= 65 && asciiValue <= 90)
	            return true;
	        
	        //a-z = ASCII range 97 to 122
	        if (asciiValue >= 97 && asciiValue <= 122)
	            return true;
	        
	        return false;
	    }
}
