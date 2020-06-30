package application.models;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Base64;

public class Crypto {
	public static String Hash(String toHash ) {
		StringBuilder ret = new StringBuilder();
		try {
		byte[] bytesOfMessage = toHash.getBytes("UTF-8");

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		for(byte b : thedigest)
			ret.append(b);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return ret.toString();
	}
	 public  static Object fromString( String s ) throws IOException,
     ClassNotFoundException {
 byte [] data = Base64.getDecoder().decode( s );
 ObjectInputStream ois = new ObjectInputStream(
         new ByteArrayInputStream(  data ) );
 Object o  = ois.readObject();
 ois.close();
 return o;
}

/** Write the object to a Base64 string. */
public  static String toString( Serializable o ) throws IOException {
 ByteArrayOutputStream baos = new ByteArrayOutputStream();
 ObjectOutputStream oos = new ObjectOutputStream( baos );
 oos.writeObject( o );
 oos.close();
 return Base64.getEncoder().encodeToString(baos.toByteArray());
}
}
