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
    	userField.setText("admin@root.com");
    	passField.setText("123456");
    	login.setOnAction(event->{
    			
Platform.runLater(new Runnable() {
    @Override public void run() {
       
    	login.setDisable(true);
    	login.setVisible(false);
    	errorField.setText("");
    			try {
    				Token token;
    				Staff staff;
    			
    					apiReq api = new apiReq(Staff.APILINK+"/auth/login/");
    					api.addPostParam("email",userField.getText() , false);
    					api.addPostParam("password",passField.getText() , false);
    					String res = api.send("POST", false);
    					token = Token.fromJsonObject(new JSONObject(res));
    					api.reConnect(Staff.APILINK+"/auth/me?token="+
    					token.getToken());
    					
    					staff = Staff.fromJsonObject(new JSONObject(api.send("POST", false)));
    					staff.setToken(token);
    					getCategerys(staff);
    					ControllerClass db = new dashboardController();
    					SceneChanger sn = new SceneChanger();
    					sn.changeScenes(event, "/application/view/dashboard.fxml", "Dashboard", staff, db);
    					//System.out.println(staff);
    					//	accesToken = (String)
    			}catch(IllegalArgumentException e)
    			{
    					if (e.getMessage().equals(""))
    						errorField.setText("incorrect email or password");
    					else
    						errorField.setText(e.getMessage());
    				e.printStackTrace();
    			}
    			catch(Exception ex)
    			{
    				errorField.setText("Host is down or connection problem \n "
    						+ "cause : <" + ex.getMessage() + ">");
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
