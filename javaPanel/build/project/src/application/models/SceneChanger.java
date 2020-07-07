package  application.models;

import java.io.IOException;	
import java.util.ArrayList;
import java.util.Optional;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class SceneChanger {

		public void changeScenes(Event event, String viewName, String title) throws IOException
	    {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource(viewName));
	        Parent parent = loader.load();
	        
	        Scene scene = new Scene(parent);
	       // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        //get the stage from the event that was passed in
	        scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
	        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        stage.setTitle(title);
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.show();
	    
	}
		
		public void changeScenes(Event event, String viewName, String title, Staff staff, ControllerClass controllerClass) throws IOException
	    {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource(viewName));
	        Parent parent = loader.load();
	        
	        Scene scene = new Scene(parent);
	        scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
	        //access the controller class and preloaded the volunteer data
	        controllerClass = loader.getController();
	        controllerClass.preloadData(staff);
	        
	      //  scene.getStylesheets().add("./src/application/application.css");
	        //get the stage from the event that was passed in
	        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        stage.setTitle(title);
	        
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.show();
	    }

		public void changeScenes(ActionEvent event, String string, String string2, Staff staff, ControllerClass db,
				Post pp) throws IOException {
			 FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource(string));
		        Parent parent = loader.load();
		        
		        Scene scene = new Scene(parent);
		        
		        //access the controller class and preloaded the volunteer data
		        db = loader.getController();
		        db.preloadData(staff,pp);
		        scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		        //get the stage from the event that was passed in
		        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		        
		        stage.setTitle(string2);
		        stage.setScene(scene);
		        stage.setResizable(false);
		        stage.show();
			
		}
		public void changeScenes(ActionEvent event, String string, String string2, Staff staff,Staff toEdit, ControllerClass db) throws IOException {
			 FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource(string));
		        Parent parent = loader.load();
		        
		        Scene scene = new Scene(parent);
		        scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		   //     scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		        //access the controller class and preloaded the volunteer data
		        db = loader.getController();
		        if (toEdit != null)
		        db.preloadData(staff,toEdit);
		        else
		        	db.preloadData(staff);	
		        //get the stage from the event that was passed in
		        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		        
		        stage.setTitle(string2);
		        stage.setScene(scene);
		        stage.setResizable(false);
		        stage.show();
			
		}
		public void changeScenes(Stage stage, String viewName, String title, Staff staff, ControllerClass controllerClass) throws IOException
	    {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource(viewName));
	        Parent parent = loader.load();
	        
	        Scene scene = new Scene(parent);
	        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        //access the controller class and preloaded the volunteer data
	        scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
	        controllerClass = loader.getController();
	        controllerClass.preloadData(staff);
	        
	
	        
	        stage.setTitle(title);
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.show();
	    }
		public static void showPopUP(String str, String title)
		{

			 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		        alert.setTitle(title);
		        alert.setHeaderText(str);
		        alert.showAndWait();
		  
		}

}