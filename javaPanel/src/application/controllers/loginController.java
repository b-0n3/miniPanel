package application.controllers;

import application.models.ControllerClass;
import application.models.SceneChanger;
import application.models.Staff;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class loginController {
		
	@FXML
    private TextField userField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button login;


    @FXML
    public void initialize() {
    		login.setOnAction(event->{
    			String password = "";
    		//	ApiReq req = buildReq(userField , passField.getText())
    			if (userField.getText().equals("admin") && passField.getText().equals("admin"))
    			{
//    				Staff staff = req.getStaff();
    				Staff staff = null;
    				ControllerClass cc = new dashboardController();
    				SceneChanger cn = new SceneChanger();
    				try {
    				cn.changeScenes(event, "/application/view/Dashboard.fxml", "dashboard" , staff, cc);
    				}catch(Exception ex)
    				{
    					ex.printStackTrace();
    				}
    			
    			
    			}
    			
    		});

    }
}
