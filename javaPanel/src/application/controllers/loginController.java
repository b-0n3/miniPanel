package application.controllers;

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
    			if (userField.equals("admin") && passField.equals("admin"))
    			{
    				
    			}
    			
    		});

    }
}
