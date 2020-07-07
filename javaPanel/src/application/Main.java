package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application{
	@Override
	public void start(Stage primaryStage) {
	
			try {
				Parent root= FXMLLoader.load(getClass().getResource("/application/view/login.fxml"));
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
		File[] cache = (new File("./src/images/")).listFiles();
		
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
