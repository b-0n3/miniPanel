package application.controllers;

import application.models.ControllerClass;
import application.models.Post;
import application.models.Staff;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

public class addStaffController implements ControllerClass{

   
	@FXML
    private TextField firstName;

    @FXML
    private TextField LastName;

    @FXML
    private TextField email;

    @FXML
    private TextField confirmEmail;

    @FXML
    private TextField facebook;

    @FXML
    private TextField linkedIn;

    @FXML
    private TextField google;

    @FXML
    private TextField twitter;

    @FXML
    private Circle staffImage;

    @FXML
    private PasswordField PassField;

    @FXML
    private PasswordField confirmPass;

    @FXML
    private CheckBox isAdmin;

    @FXML
    private Button Cancel;

    @FXML
    private Button save;
    @Override
   	public void preloadData(Staff staff) {
   		
   	}

   	@Override
   	public void preloadData(Staff staff, Post post) {
   		
   	}
   	@Override
   	public void preloadData(Staff staff, Staff toEdit) {
   		if (staff.getId() == 0 || staff.getId() == toEdit.getId())
   		{
   			
   		}
   	}
}

