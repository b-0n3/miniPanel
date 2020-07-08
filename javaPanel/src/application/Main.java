package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.List;

import org.json.JSONObject;

import application.models.Staff;
import application.models.osFinder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application{
	@Override
	public void start(Stage primaryStage) {
	
			try {
				new osFinder();
				Parent root;
				String configPath;
				
				
				 
				System.out.println(osFinder.currentPath);
				if(!osFinder.isWindwos)
				{
					String path =  "/application/view/login.fxml";
				System.out.println(path);
					root= FXMLLoader.load(getClass().getResource(path));
					configPath = osFinder.currentPath + "/src/config/config.json";
				}
				else
				{
					String path = "login.fxml";
					System.out.println(path);
					configPath = osFinder.currentPath + "\\src\\config\\config.json";
					root= FXMLLoader.load(getClass().getResource(path));
				}
				List<String> jso = Files.readAllLines(new File (configPath).toPath());
				StringBuilder js = new StringBuilder();
				for(String str : jso)
				{
					js.append(str);
				}
				JSONObject config = new  JSONObject(js.toString());
				Staff.APILINK = config.getString("api");
				
				
				System.out.println(Staff.APILINK);
				
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setTitle("login");
				primaryStage.setResizable(false);
				primaryStage.setScene(scene);
				
				primaryStage.show();
				
			} catch(Exception e) {
				e.printStackTrace();

			}
			
		
	}
	
	
	@Override
	public void stop() throws Exception {
		String path = "";
		if(osFinder.isWindwos)
				path = osFinder.currentPath+"\\src\\images\\";
		else
			path = osFinder.currentPath + "/src/images/";
		File[] cache = (new File(path)).listFiles();
		
		for(File s:cache)
		{
			s.delete();
		}
		super.stop();
		
	}
	


	public static void main(String[] args) {
		launch(args);
	}
}
