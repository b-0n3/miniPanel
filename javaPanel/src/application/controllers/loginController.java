package application.controllers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import application.models.Categery;
import application.models.ControllerClass;
import application.models.SceneChanger;
import application.models.Staff;
import application.models.Token;
import application.models.apiReq;
import application.models.osFinder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class loginController {
		
	   @FXML
	    private VBox Background;

	    @FXML
	    private TextField userField;

	    @FXML
	    private PasswordField passField;

	    @FXML
	    private Button login;

	    @FXML
	    private Text errorField;

    @FXML
    public void initialize() {
    	
    	userField.setOnMouseClicked(event->{
    		errorField.setText("");
    	});
    	passField.setOnMouseClicked(event->{
    		errorField.setText("");
    	});
    	login.setOnAction(event->{
    		errorField.setText("");
Platform.runLater(new Runnable() {
    @Override public void run() {
       
    	login.setDisable(true);
    	login.setVisible(false);
    	
    	
    			try {
    				Token token =null;
    				Staff staff;
    				apiReq api = null;
    				String res = null;
    			try {
    					 api = new apiReq(Staff.APILINK+"/auth/login/");
    					api.addPostParam("email",userField.getText() , false);
    					api.addPostParam("password",passField.getText() , false);
    					res = api.send("POST", false);
    					if(res.contains("error"))
    					{passField.setText("");
    						
    						errorField.setText("invalid email or Password");
        					login.setDisable(false);	
                			login.setVisible(true);
            				return;
    					}
    					
    			}catch(Exception e)
    			{
    				
    				System.out.println("eeee");
    					errorField.setText("invalid email or Password");
    					login.setDisable(false);	
            			login.setVisible(true);
            			passField.setText("");
        				return;
    				
    				
    			
    			}
    			token = Token.fromJsonObject(new JSONObject(res));
    					api.reConnect(Staff.APILINK+"/auth/me?token="+
    					token.getToken());
    					
    					staff = Staff.fromJsonObject(new JSONObject(api.send("POST", false)));
    					staff.setToken(token);
    					getCategerys(staff);
    					ControllerClass db = new dashboardController();
    					SceneChanger sn = new SceneChanger();
    					String path;
    					if (osFinder.isWindwos)
    					 {
    						 path =  "dashboard.fxml";
    						 
    					 }else
    						 path =  "/application/view/dashboard.fxml";
    					System.out.println(path);
    					sn.changeScenes(event, path, "Dashboard", staff, db);
    					//System.out.println(staff);
    					//	accesToken = (String)
    			}catch(Exception ex)
    			{
    				
    				passField.setText("");
    				errorField.setText("Host is down or connection problem \n ");
    				ex.printStackTrace();
    			}
    			login.setDisable(false);	
    			login.setVisible(true);
    }
	
});

    		});

    }
    
    public void getCategerys(Staff staff)
    {
    	apiReq api = new apiReq(Staff.APILINK+"/categories/");
    	api.addProperty("Authorization", "Bearer " + staff.getToken().getToken());
		String responce = api.send("GET", false);
		System.out.println(responce);
		if (Staff.categerys != null)
			Staff.categerys.clear();
		Staff.categerys =Categery.fromJsonArray(new JSONArray(responce));
		
    }
}
