package application.models;

public class osFinder {
	public static  String currentPath;
	public static boolean isWindwos;
	
	public osFinder() {
		String osname =System.getProperty("os.name");
		isWindwos = osname.startsWith("Windows");
		currentPath = System.getProperty("user.dir");
	}
}
